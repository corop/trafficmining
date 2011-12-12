package de.lmu.ifi.dbs.paros;

import de.lmu.ifi.dbs.paros.graph.OSMGraph;
import de.lmu.ifi.dbs.paros.graph.OSMLink;
import de.lmu.ifi.dbs.paros.graph.OSMNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;

public class SeekPositionFrame extends javax.swing.JFrame {

    private final OSMGraph<OSMNode<OSMLink>, OSMLink<OSMNode>> graph;
    private final GeoPosition centeredGeoPos;
    private OSMNode node = null;
    private GeoPosition coord = null;
    private JXMapViewer map;

    /** Creates new form SeekPositionFrame */
    public SeekPositionFrame() {
        initComponents();
        this.graph = null;
        this.centeredGeoPos = null;
        this.coord = null;
    }

    /** Creates new form SeekPositionFrame */
    public SeekPositionFrame(OSMGraph<OSMNode<OSMLink>, OSMLink<OSMNode>> g, GeoPosition center, TileServer ts) {
        initComponents();
        this.graph = g;
        this.setLocationRelativeTo(null);
        this.centeredGeoPos = center;
        map = jXMapKit.getMainMap();
        map.setTileFactory(ts.getTileFactory());
        ((DefaultTileFactory) map.getTileFactory()).setThreadPoolSize(8);
        map.setRestrictOutsidePanning(true);
        map.setHorizontalWrapped(false);
        jXMapKit.setCenterPosition(centeredGeoPos);
        jXMapKit.setZoom(2);
        
        // getRootPane().setDefaultButton(searchButton);
        queryField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                doSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                doSearch();
            }
        });
    }

    public OSMNode getNode() {
        return node;
    }
    
    public GeoPosition getGeoPos() {
        return coord;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        defaultListModel = new javax.swing.DefaultListModel();
        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        jXMapKit = new org.jdesktop.swingx.JXMapKit();
        queryField = new javax.swing.JTextField();
        cancelButon = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jList.setModel(defaultListModel);
        jList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListValueChanged(evt);
            }
        });
        scrollPane.setViewportView(jList);

        splitPane.setTopComponent(scrollPane);

        jXMapKit.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        jXMapKit.setZoom(2);
        splitPane.setBottomComponent(jXMapKit);

        cancelButon.setText("Cancel");
        cancelButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButonActionPerformed(evt);
            }
        });

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButon))
                    .addComponent(queryField, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(queryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButon)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListValueChanged
        setPosition();
    }//GEN-LAST:event_jListValueChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButonActionPerformed
        node = null;
        dispose();
    }//GEN-LAST:event_cancelButonActionPerformed

    private void doSearch() {
        final String query = queryField.getText().trim().toLowerCase();
        if (graph == null || query.length() <= 0) {
            return;
        }

        final HashSet<LinkWrapper> hits = new HashSet<LinkWrapper>();
        final HashSet<OSMLink> visitedLinks = new HashSet<OSMLink>();

        for (OSMNode<OSMLink> node : graph.getNodes()) {
            for (OSMLink<OSMNode> link : node.getLinks()) {
                if (visitedLinks.add(link)) { // true : did not yet contain the link
                    // now check the properties of the link
                    String name = link.getAttr("name");
                    if (name != null && name.toLowerCase().contains(query)) {
                        hits.add(new LinkWrapper(link.getSource(), name));
                        hits.add(new LinkWrapper(link.getTarget(), name));
                    }
                }
            }
        }

        List<LinkWrapper> list = new ArrayList<LinkWrapper>(hits);
        Collections.sort(list, new Comparator<LinkWrapper>() {

            @Override
            public int compare(LinkWrapper o1, LinkWrapper o2) {
                int a = o1.name.compareTo(o2.name);
                if (a != 0) {
                    return a;
                }
                return o1.node.getName() - o2.node.getName();
            }
        });

        defaultListModel.clear();
        for (LinkWrapper node : list) {
            defaultListModel.addElement(node);
        }
    }

    private void setPosition() {
        Object o = jList.getSelectedValue();
        if (o != null) {
            LinkWrapper lw = (LinkWrapper) o;
            coord = lw.node.getGeoPosition();
            node = lw.node;
            jXMapKit.setAddressLocation(coord);
            jXMapKit.setCenterPosition(coord);
            jXMapKit.repaint();
        }
    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new SeekPositionFrame().setVisible(true);
//            }
//        });
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButon;
    private javax.swing.DefaultListModel defaultListModel;
    private javax.swing.JList jList;
    private org.jdesktop.swingx.JXMapKit jXMapKit;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField queryField;
    // End of variables declaration//GEN-END:variables

    // End of variables declaration
    class LinkWrapper {

        private final OSMNode node;
        private final String name;

        private LinkWrapper(OSMNode node, String name) {
            this.node = node;
            this.name = name;
        }

        @Override
        public String toString() {
            return node.toString() + ", " + name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final LinkWrapper other = (LinkWrapper) obj;
            if (this.node != other.node && (this.node == null || !this.node.equals(other.node))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + (this.node != null ? this.node.hashCode() : 0);
            return hash;
        }
    }
}

