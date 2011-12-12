package de.lmu.ifi.dbs.paros.graph;

import de.lmu.ifi.dbs.paros.utils.GraphFactory;
import de.lmu.ifi.dbs.paros.utils.OSMUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p/>
 * <p/>
 * @author graf
 * @param <N>
 * @param <L>
 */
public class OSMGraph<N extends OSMNode, L extends OSMLink> extends Graph<N, L> {

    private static final Logger log = Logger.getLogger(OSMGraph.class.getName());
    protected List<OSMLink<OSMNode>> linkList = new ArrayList<>();
    protected HashMap<String, Integer> speed = new HashMap<>();
    private final String[] blacklist = new String[]{"height", "name", "note"};
    private final String speedsConfig = "speeds.properties";

    public OSMGraph() {
        try {
            File f = new File(speedsConfig);
            Properties prop = new Properties();
            prop.load(new BufferedReader(new FileReader(f)));
            log.log(Level.INFO, "Using speeds config: {0}", f.getAbsolutePath());
            log.log(Level.FINE, "Speeds found: {0}", prop.keySet().size());
            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                Integer speed_int = Integer.parseInt((String)entry.getValue());
                speed.put((String)entry.getKey(), speed_int);
                log.log(Level.FINE, "{0} -> {1}", new Object[]{entry.getKey(), speed_int});
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("A error occured due parsing speeds config, using hardcoded defaults");
            speed.clear();
            // http://wiki.openstreetmap.org/wiki/DE:Map_Features
            speed.put("default", 50); // myown definition
            //
            speed.put("DE:zone30", 30);
            speed.put("motorway", 130);
            speed.put("motorway_link", 60);
            speed.put("trunk", 100);
            speed.put("trunk_link", 60);
            speed.put("primary", 100);
            speed.put("primary_link", 60);
            speed.put("secondary", 100);
            speed.put("secondary_link", 60);
            speed.put("tertiary", 100);
            speed.put("tertiary_link", 100);
            speed.put("unclassified", 50); // meist kleiner als tertiary
            speed.put("road", 50);
            speed.put("residential", 50);
            speed.put("living_street", 6); // spielstraße 4-7 km/h
            speed.put("service", 30);
            speed.put("track", 30);
            speed.put("pedestrian", 10);
            speed.put("raceway", 250);
            speed.put("services", 60);
            speed.put("bus_guideway", 60);
            speed.put("construction", 3);
            // Paths
            speed.put("path", 4);
            speed.put("cycleway", 15);
            speed.put("footway", 5);
            speed.put("bridleway", 5);
            speed.put("byway", 15);
            speed.put("steps", 3);
        }
    }

    /**
     * @deprecated use {@link GraphFactory#readGraph(java.io.File, java.io.File)}
     * @param nodeFile
     * @param linkFile
     * @throws IOException
     */
    public OSMGraph(File nodeFile, File linkFile) throws IOException {
        throw new UnsupportedOperationException("use GraphFactory.readGraph(File, File) instead");
    }

    public int getLinkCount() {
        return linkList.size();
    }

    @Deprecated
    public int getNodesWithoutLinksCount() {
        return 0;
    }

    @Deprecated
    public List<N> getNodesWithoutLinks() {
        return new ArrayList<N>(0);
    }

    public void beautifyGraph() {
        splitNodeWithAttribsToNewLinks();
        connect();
        //cleanIntermediateNodes();
        cleanNodeList();
        
    }
    
    public void cleanIntermediateNodes() {
        List<OSMLink<OSMNode>> linkList_cleaned = new ArrayList<>();
        for (int o = 0; o < linkList.size(); o++) {
            //specified link
            OSMLink<OSMNode> link = linkList.get(o);
//            System.out.println("list_nodes: "+list_nodes.toString());
//            for (int i = 0; i < list_nodes.size(); i++) {
//                System.out.println("OSMNode ["+i+"]: "+list_nodes.get(i).getName());              
//            }
//            System.out.print("Link ["+link.getId()+"] before: "+link.getNodes().size());
            if (link.getNodes().size() == 2) {
//                System.out.println(" UNMODIFIED!");
                linkList_cleaned.add(link);
                continue;
            }
            
            OSMNode first = link.getNodes().get(0);
            OSMNode last = link.getNodes().get(link.getNodes().size()-1);
            link.clearNodes();
            link.addNodes(first);
            link.addNodes(last);
            
            linkList_cleaned.add(link);
//            System.out.println(" REDUCED: "+link.getNodes().size());
        }
//        System.out.println("LINKS BEFORE: "+linkList.size());
        linkList.clear();
        linkList.addAll(linkList_cleaned);
//        System.out.println("LINKS AFTER: "+linkList.size());
    }

    public Node getNode(Double lat, Double lon) {
        Collection<N> nodes = this.getNodes();
        for (N aktN : nodes) {
            if (aktN.getLat() == lat && aktN.getLon() == lon) {
                return aktN;
            }
        }
        return null;
    }

    /**
     * if a node has an additional attribute and is an intermediate node
     * split the link to two new links and set the node as start or end
     * for a new link
     */
    private void splitNodeWithAttribsToNewLinks() {
        int s = linkList.size();
        log.log(Level.INFO, "creating new links for nodes with attribs. Links: {0}", s);
        List<OSMLink<OSMNode>> newLinkList = new ArrayList<>(s);
        int id_counter = -1;

        // list with all active links
        for (int o = 0; o < linkList.size(); o++) {

            //specified link
            OSMLink<OSMNode> link_org = linkList.get(o);

            //list with all nodes per specified link
            List<OSMNode> list_nodes = link_org.getNodes();

            //list with new nodes for new link
            List<OSMNode> act_nodes = new ArrayList<>();
            boolean link_splitted = false;

            for (int l = 0; l < list_nodes.size(); l++) {
                OSMNode n = list_nodes.get(l);

                if (l == 0) { // first node
                    act_nodes.add(n);
                } else if (l == list_nodes.size() - 1) {  // last node
                    if (link_splitted) {  // at least 1 node had attribs
                        act_nodes.add(n);
                        OSMLink<OSMNode> lw = newLinker(act_nodes, id_counter, link_org);
                        newLinkList.add(lw);
                        id_counter--;
                    } else { // no node had attribs, maintain the original unmodified link
                        act_nodes.clear();
                        link_org.setDistance(OSMUtils.dist(link_org));
                        newLinkList.add(link_org);
                    }
                } else {  // intermediate nodes
                    act_nodes.add(n);

                    boolean usefulAttribs = false;
                    Map<String, String> map_node_attr = n.getAttr();
                    if (map_node_attr.size() >= 1) {
                        for (String attrib : map_node_attr.keySet()) {
                            for (String bl : blacklist) {
                                if (!attrib.equalsIgnoreCase(bl)) {
                                    usefulAttribs = true; // node has informational attribs except the blacklisted ones e.g. height (no need to split here)
                                }
                            }
                            if (usefulAttribs) {
                                break;
                            }
                        }
                    }
                    if (usefulAttribs) {  // split link
                        link_splitted = true;
                        OSMLink<OSMNode> lw = newLinker(act_nodes, id_counter, link_org);
                        newLinkList.add(lw);

                        id_counter--;
                        act_nodes.clear();
                        act_nodes.add(n);
                    }
                }
            }
        }
        linkList.clear();
        for (OSMLink<OSMNode> oSMLink : newLinkList) {
            linkList.add(oSMLink);
            OSMUtils.setSpeed(this, oSMLink);
        }
//        linkList = newLinkList;
//        for (OSMLink<OSMNode> oSMLink : newLinkList) {
//            System.out.println("oSMLink: "+oSMLink+" speed: "+oSMLink.getSpeed()+" | distance: "+oSMLink.getDistance()+" | oneway: "+oSMLink.isOneway());
//            for (OSMNode oSMNode : oSMLink.getNodes()) {
//                System.out.println(oSMNode.getName()+" : "+oSMNode);
//            }
//        }
        log.log(Level.INFO, "Done. Added {0} links. Links now: {1}", new Object[]{linkList.size() - s, linkList.size()});
    }

    private OSMLink<OSMNode> newLinker(List<OSMNode> nodes, int id, OSMLink<OSMNode> link_org) {
        if (nodes.size() < 2) {
            // must never happen
            log.log(Level.SEVERE, "{0}.newLinker: nodes.size() < 2: {1}", new Object[]{this.getClass().getName(), nodes.size()});
            System.exit(1);
        }
        OSMLink<OSMNode> ret = new OSMLink(nodes.get(0), nodes.get(nodes.size() - 1), link_org.isOneway());
        for (OSMNode oSMNode : nodes) {
            oSMNode.removeLink(link_org);  // reset the node<>link memory
            ret.addNodes(oSMNode);  // and readd the nodes to the link
        }

        ret.setId(id);
        ret.setAscend(link_org.getAscend());
        ret.setDescend(link_org.getDescend());
//        ret.setSpeed(link_org.getSpeed());
        for (Map.Entry<String, String> entry : link_org.getAttr().entrySet()) {
            ret.setAttr(entry.getKey(), entry.getValue());
        }
        ret.setDistance(OSMUtils.dist(ret));


        return ret;
    }

    private void cleanNodeList() {
        int counter = 0;
        log.log(Level.INFO, "removing nodes without links. Nodes: {0}", getNodes().size());
        List<N> list = new ArrayList<>(getNodes());
        for (N n : list) {
            if (Thread.interrupted()) {
                return;
            }
            if (n.getLinks().isEmpty()) {
                removeNode(n);
                counter++;
            }
        }
        log.log(Level.INFO, "remaining nodes: {0}, links: {1}. Removed {2} nodes.", new Object[]{getNodes().size(), getLinkList().size(), counter});
    }

    /**
     * @fixme this should be done MUCH more intelligent
     *
     * AC and BD are links, but not AB, BC.
     * So split AC into AB,BC.
     *
     * A--B--C
     *    |
     *    D
     */
    private void connect() {
        log.log(Level.INFO, "connecting ways for routing");
        int linkCountA = linkList.size();
        List<OSMNode> nodes = new ArrayList<>(1000);
        for (OSMNode n : getNodes()) {
            if (n.getLinks().size() > 0) {
                nodes.add(n);
            }
        }

        for (int link_inner = 0; link_inner < linkList.size() && !Thread.interrupted(); link_inner++) {
            if (log.isLoggable(Level.FINE) && link_inner != 0 && link_inner % 10000 == 0) {
                log.log(Level.FINE, "processed {0} / {1} links", new Object[]{link_inner, linkList.size()});
            }

            int removes = 0;
            int adds = 0;
            OSMLink<OSMNode> l = linkList.get(link_inner);
            List<OSMNode> linkNodes = l.getNodes();
            for (int iN = 0; l != null && iN < linkNodes.size() && !Thread.interrupted(); iN++) {
                OSMNode innerNode = linkNodes.get(iN);
                List<OSMLink<OSMNode>> links = innerNode.getLinks();
                if (links.size() > 0 && !links.contains(l)) {
                    linkList.remove(link_inner);
                    removes++;
                    List<OSMLink<OSMNode>> newLinks = OSMUtils.split(l, innerNode);
                    for (OSMLink<OSMNode> aLink : newLinks) {
                        linkList.add(link_inner, aLink);
                        adds++;
                    }
                    l = null;
                    linkNodes = null;
                    link_inner--;
                }
            }
        }
        log.log(Level.INFO, "added {0} links.", (linkList.size() - linkCountA));
    }

    /**
     * returns an instance of the link list. Keep in mind that changing the list
     * DOES NOT AFFECT the graph directly if you do not remove/add the link from 
     * the according nodes!
     *
     * @return list of links
     */
    public List<OSMLink<OSMNode>> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<OSMLink<OSMNode>> linkList) {
        this.linkList = linkList;
    }

    public HashMap<String, Integer> getSpeedMap() {
        return speed;
    }

	public OSMNode getRandomNOde(OSMGraph<OSMNode, OSMLink> g) {
		ArrayList<OSMNode> sL = new ArrayList(g.getNodes());
		Collections.shuffle(sL);		
		return sL.get(0);
	}


}
