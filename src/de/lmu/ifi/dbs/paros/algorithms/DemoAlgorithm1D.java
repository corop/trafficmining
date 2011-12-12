package de.lmu.ifi.dbs.paros.algorithms;

import de.lmu.ifi.dbs.paros.graph.Graph;
import de.lmu.ifi.dbs.paros.graph.Path;
import de.lmu.ifi.dbs.paros.graph.OSMNode;
import de.lmu.ifi.dbs.paros.result.Simplex1Result;
import de.lmu.ifi.dbs.paros.Statistics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DemoAlgorithm1D extends Algorithm<OSMNode, Graph, Path> {

    private List<String> attribs = new ArrayList();
    final static List<String> attribsOptions = Collections.unmodifiableList(
            new ArrayList() {

                {
                    add("straight");
                    add("detour");
                }
            });

    public List<String> getAttribs() {
        return attribs;
    }

    public void setAttribs(List<String> attribs) {
//        String s = "";
//        for (String ss : myListOptions) {
//            s += ss+"|";
//        }
//        System.out.println("set: " + s);
        this.attribs = attribs;
    }

    @Override
    public Statistics getStatistics() {
        Statistics stats = new Statistics();
        stats.put("Runtime", "60");
        stats.put("Nodes visited", "15000");
        stats.put("Paths in Queue", "100");
        return stats;
    }

    @Override
    public Simplex1Result getResult() {
        Simplex1Result s1result = new Simplex1Result();
        List<OSMNode> nodes = getNodes();
        Path p1 = new Path(nodes.get(0), nodes.get(nodes.size() - 1));
        s1result.addResult(p1, 150d);

        OSMNode start = nodes.get(0);
        OSMNode end = nodes.get(nodes.size() - 1);
        OSMNode intermediate = new OSMNode(-1);
        double lat = (start.getLat() + end.getLat()) / 2;
        double lon = (start.getLon() + end.getLon()) / 2;
        lat += (start.getLat() - end.getLat()) * 0.3;
        intermediate.setLat(lat);
        intermediate.setLon(lon);

        Path p2 = new Path(start, intermediate);
        p2 = new Path(p2, end);
        s1result.addResult(p2, 170d);


        s1result.setUnits("test");
        s1result.setAttributes("test");
        return s1result;
    }

    @Override
    public void run() {
        System.out.println("I'm just a demo");
    }

    @Override
    public String getName() {
        return "Demo algorithm 1D";
    }
}
