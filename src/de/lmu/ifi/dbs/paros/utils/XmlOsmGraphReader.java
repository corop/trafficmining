package de.lmu.ifi.dbs.paros.utils;

import de.lmu.ifi.dbs.paros.graph.OSMGraph;
import de.lmu.ifi.dbs.paros.graph.OSMLink;
import de.lmu.ifi.dbs.paros.graph.OSMNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Class for reading OSM XML files
 * <p/>
 * @author graf
 * @modified greil
 */
public class XmlOsmGraphReader<N extends OSMNode<L>, L extends OSMLink<N>> {

    private static final Logger log = Logger.getLogger(XmlOsmGraphReader.class.getName());
    private OSMGraph<N, L> graph;
    private XmlOsmHandler xoh;

    public XmlOsmGraphReader() {
    }

    public void parseOSMxml(File osmXML) {
        try {
            long filesize = (osmXML.length() / 1024 / 1024);
            String out = filesize + " mb";
            if (filesize <= 0) {
                out = (osmXML.length() / 1024) + " kb";
            }
            log.info("parsing XML: " + osmXML.getAbsolutePath() + "; filesize: " + out);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            parser.parse(osmXML, xoh);
        } catch (IOException ex) {
            Logger.getLogger(XmlOsmGraphReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlOsmGraphReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmlOsmGraphReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doGraph() {
        List<N> nodes = xoh.getListNodes();
        List<OSMLink<OSMNode>> links = xoh.getListLinks();
        graph.addNodeList(nodes);
        graph.setLinkList(links);

    }

    OSMGraph<N, L> getGraph(File osmXML) {
        try {
            graph = new OSMGraph<N, L>();
            xoh = new XmlOsmHandler();
            parseOSMxml(osmXML);
            Thread.sleep(1);
            doGraph();
            Thread.sleep(1);
            graph.beautifyGraph();
        } catch (InterruptedException ex) {
        }
        return graph;
    }
}
