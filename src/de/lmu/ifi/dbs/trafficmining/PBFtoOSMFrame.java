
package de.lmu.ifi.dbs.trafficmining;

import de.lmu.ifi.dbs.trafficmining.graphpainter.PolyPainter;
import de.lmu.ifi.dbs.trafficmining.utils.PBFOSMWorker;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.CompoundPainter;

/**
 *
 * @author rob
 */
public class PBFtoOSMFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private static PolyPainter painter_intersection, painter_bounds, painter_sele;
    private JFileChooser jfc = null;
    private static JXMapViewer map = null;
    private String recentlyUsedDirectory = "";
    private File[] files = new File[3];
    private PBFOSMWorker pbf_w = null;
    private CompoundPainter cp = new CompoundPainter();
    private static double[] bounds = null;
    private boolean pbf = false;
    private boolean osm = false;
    private boolean srtm = false;
    private static double left_lon_bounds = -1;
    private static double right_lon_bounds = -1;
    private static double top_lat_bounds = -1;
    private static double bottom_lat_bounds = -1;
    private static double left_lon_sele = -1;
    private static double right_lon_sele = -1;
    private static double top_lat_sele = -1;
    private static double bottom_lat_sele = -1;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel_leftSide = new javax.swing.JPanel();
        jPanel_loader = new javax.swing.JPanel();
        jLabel_pbf = new javax.swing.JLabel();
        jFormattedTextField_in_pbf = new javax.swing.JFormattedTextField();
        jButton_in_pbf = new javax.swing.JButton();
        jLabel_osm = new javax.swing.JLabel();
        jFormattedTextField_out_osm = new javax.swing.JFormattedTextField();
        jButton_out_osm = new javax.swing.JButton();
        jLabel_srtm = new javax.swing.JLabel();
        jCheckBox_srtm = new javax.swing.JCheckBox();
        jFormattedTextField_dir_srtm = new javax.swing.JFormattedTextField();
        jButton_dir_srtm = new javax.swing.JButton();
        jButton_load = new javax.swing.JButton();
        jPanel_selection = new javax.swing.JPanel();
        filler_pre = new javax.swing.Box.Filler(new java.awt.Dimension(26, 0), new java.awt.Dimension(26, 0), new java.awt.Dimension(21, 32767));
        jLabel_top_lat = new javax.swing.JLabel();
        jFormattedTextField_top_lat = new javax.swing.JFormattedTextField();
        jLabel_left_lon = new javax.swing.JLabel();
        jFormattedTextField_left_lon = new javax.swing.JFormattedTextField();
        jLabel_right_lon = new javax.swing.JLabel();
        jFormattedTextField_right_lon = new javax.swing.JFormattedTextField();
        jLabel_bottom_lat = new javax.swing.JLabel();
        jFormattedTextField_bottom_lat = new javax.swing.JFormattedTextField();
        filler_hor_strut3 = new javax.swing.Box.Filler(new java.awt.Dimension(65, 0), new java.awt.Dimension(65, 0), new java.awt.Dimension(45, 32767));
        jPanel_progress = new javax.swing.JPanel();
        busyLabel = new org.jdesktop.swingx.JXBusyLabel();
        jButton_work = new javax.swing.JButton();
        jFormattedTextField_progess = new javax.swing.JFormattedTextField();
        filler_horStut_05 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jPanel_map = new javax.swing.JPanel();
        jXMapKit = new org.jdesktop.swingx.JXMapKit();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Import PBF");
        setAlwaysOnTop(true);

        jPanel_leftSide.setLayout(new java.awt.GridBagLayout());

        jPanel_loader.setBorder(javax.swing.BorderFactory.createTitledBorder("Loader"));
        jPanel_loader.setLayout(new java.awt.GridBagLayout());

        jLabel_pbf.setText("PBF input");
        jLabel_pbf.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_pbf.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_pbf.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_loader.add(jLabel_pbf, gridBagConstraints);

        jFormattedTextField_in_pbf.setEditable(false);
        jFormattedTextField_in_pbf.setText("input.osm.pbf");
        jFormattedTextField_in_pbf.setMinimumSize(new java.awt.Dimension(150, 20));
        jFormattedTextField_in_pbf.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_loader.add(jFormattedTextField_in_pbf, gridBagConstraints);

        jButton_in_pbf.setText("...");
        jButton_in_pbf.setMaximumSize(new java.awt.Dimension(65, 20));
        jButton_in_pbf.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton_in_pbf.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton_in_pbf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_in_pbfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel_loader.add(jButton_in_pbf, gridBagConstraints);

        jLabel_osm.setText("OSM output");
        jLabel_osm.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_osm.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_osm.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_loader.add(jLabel_osm, gridBagConstraints);

        jFormattedTextField_out_osm.setEditable(false);
        jFormattedTextField_out_osm.setText("output.osm");
        jFormattedTextField_out_osm.setMinimumSize(new java.awt.Dimension(150, 20));
        jFormattedTextField_out_osm.setOpaque(false);
        jFormattedTextField_out_osm.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_loader.add(jFormattedTextField_out_osm, gridBagConstraints);

        jButton_out_osm.setText("...");
        jButton_out_osm.setMaximumSize(new java.awt.Dimension(65, 20));
        jButton_out_osm.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton_out_osm.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton_out_osm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_out_osmActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel_loader.add(jButton_out_osm, gridBagConstraints);

        jLabel_srtm.setText("SRTM directory");
        jLabel_srtm.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_srtm.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_srtm.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_loader.add(jLabel_srtm, gridBagConstraints);

        jCheckBox_srtm.setSelected(true);
        jCheckBox_srtm.setToolTipText("<html>\nUse SRTM?<br>\nyes or no\n</html>");
        jCheckBox_srtm.setMaximumSize(new java.awt.Dimension(26, 21));
        jCheckBox_srtm.setMinimumSize(new java.awt.Dimension(26, 21));
        jCheckBox_srtm.setPreferredSize(new java.awt.Dimension(26, 21));
        jCheckBox_srtm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_srtmActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel_loader.add(jCheckBox_srtm, gridBagConstraints);

        jFormattedTextField_dir_srtm.setEditable(false);
        jFormattedTextField_dir_srtm.setText("./srtm");
        jFormattedTextField_dir_srtm.setMinimumSize(new java.awt.Dimension(150, 20));
        jFormattedTextField_dir_srtm.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_loader.add(jFormattedTextField_dir_srtm, gridBagConstraints);

        jButton_dir_srtm.setText("...");
        jButton_dir_srtm.setMaximumSize(new java.awt.Dimension(65, 20));
        jButton_dir_srtm.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton_dir_srtm.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton_dir_srtm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_dir_srtmActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel_loader.add(jButton_dir_srtm, gridBagConstraints);

        jButton_load.setText("load");
        jButton_load.setEnabled(false);
        jButton_load.setMaximumSize(new java.awt.Dimension(65, 20));
        jButton_load.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton_load.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        jPanel_loader.add(jButton_load, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_leftSide.add(jPanel_loader, gridBagConstraints);

        jPanel_selection.setBorder(javax.swing.BorderFactory.createTitledBorder("Selection"));
        jPanel_selection.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        jPanel_selection.add(filler_pre, gridBagConstraints);

        jLabel_top_lat.setText("Top (Latitude)");
        jLabel_top_lat.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_top_lat.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_top_lat.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_selection.add(jLabel_top_lat, gridBagConstraints);

        jFormattedTextField_top_lat.setEditable(false);
        jFormattedTextField_top_lat.setMaximumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_top_lat.setMinimumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_top_lat.setPreferredSize(new java.awt.Dimension(138, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_selection.add(jFormattedTextField_top_lat, gridBagConstraints);

        jLabel_left_lon.setText("Left (Longitude)");
        jLabel_left_lon.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_left_lon.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_left_lon.setPreferredSize(new java.awt.Dimension(90, 15));
        jLabel_left_lon.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_selection.add(jLabel_left_lon, gridBagConstraints);

        jFormattedTextField_left_lon.setEditable(false);
        jFormattedTextField_left_lon.setMaximumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_left_lon.setMinimumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_left_lon.setPreferredSize(new java.awt.Dimension(138, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_selection.add(jFormattedTextField_left_lon, gridBagConstraints);

        jLabel_right_lon.setText("Right (Longitude)");
        jLabel_right_lon.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_right_lon.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_right_lon.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_selection.add(jLabel_right_lon, gridBagConstraints);

        jFormattedTextField_right_lon.setEditable(false);
        jFormattedTextField_right_lon.setMaximumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_right_lon.setMinimumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_right_lon.setPreferredSize(new java.awt.Dimension(138, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_selection.add(jFormattedTextField_right_lon, gridBagConstraints);

        jLabel_bottom_lat.setText("Bottom (Latitude)");
        jLabel_bottom_lat.setMaximumSize(new java.awt.Dimension(90, 15));
        jLabel_bottom_lat.setMinimumSize(new java.awt.Dimension(90, 15));
        jLabel_bottom_lat.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_selection.add(jLabel_bottom_lat, gridBagConstraints);

        jFormattedTextField_bottom_lat.setEditable(false);
        jFormattedTextField_bottom_lat.setMaximumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_bottom_lat.setMinimumSize(new java.awt.Dimension(138, 20));
        jFormattedTextField_bottom_lat.setPreferredSize(new java.awt.Dimension(138, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_selection.add(jFormattedTextField_bottom_lat, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        jPanel_selection.add(filler_hor_strut3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_leftSide.add(jPanel_selection, gridBagConstraints);

        jPanel_progress.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));
        jPanel_progress.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel_progress.add(busyLabel, gridBagConstraints);

        jButton_work.setText("run");
        jButton_work.setEnabled(false);
        jButton_work.setMaximumSize(new java.awt.Dimension(65, 20));
        jButton_work.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton_work.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton_work.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_workActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel_progress.add(jButton_work, gridBagConstraints);

        jFormattedTextField_progess.setEditable(false);
        jFormattedTextField_progess.setMinimumSize(new java.awt.Dimension(235, 20));
        jFormattedTextField_progess.setPreferredSize(new java.awt.Dimension(235, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel_progress.add(jFormattedTextField_progess, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel_progress.add(filler_horStut_05, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_leftSide.add(jPanel_progress, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        jPanel_leftSide.add(filler1, gridBagConstraints);

        jSplitPane1.setLeftComponent(jPanel_leftSide);

        jPanel_map.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel_map.setLayout(new java.awt.GridBagLayout());

        jXMapKit.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        jXMapKit.setAddressLocationShown(false);
        jXMapKit.setPreferredSize(new java.awt.Dimension(800, 600));
        jXMapKit.setZoom(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel_map.add(jXMapKit, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel_map);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Creates new form NewJFrame */
    public PBFtoOSMFrame() {
        initComponents();
    }

    @Override
    public void dispose() {
        if (pbf_w != null) {
            pbf_w.kill();
        }
        this.setVisible(false);
    }

    public void setMapTileServer(TileServer ts) {
        map = jXMapKit.getMainMap();
        map.setTileFactory(ts.getTileFactory());
        ((DefaultTileFactory) map.getTileFactory()).setThreadPoolSize(8);
        map.setRestrictOutsidePanning(true);
        map.setHorizontalWrapped(false);
    }

    private boolean chooser(final String end, final String desc, final boolean file, javax.swing.JFormattedTextField t, int i) {
        jfc = new JFileChooser(recentlyUsedDirectory);
        jfc.setMultiSelectionEnabled(false);

        if (!file) {
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            jfc.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(end);
                }

                @Override
                public String getDescription() {
                    return end + " - " + desc;
                }
            });
        }

        // If the user pressed "okay", try to load the files
        if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(getContentPane())) {
            if (recentlyUsedDirectory.isEmpty()) {
                recentlyUsedDirectory = jfc.getCurrentDirectory().getAbsolutePath();
            }
            t.setText(jfc.getSelectedFile().getAbsolutePath());
            files[i] = new File(jfc.getSelectedFile().getAbsolutePath());
            return true;
        }
        return false;
    }

    public static void setLongLat(double[] sele_box) {
        //Rectangle2D used for intersection between selected box and bounding
        //of loaded pbf
        Point2D bounds_tl = map.convertGeoPositionToPoint(new GeoPosition(top_lat_bounds, left_lon_bounds));
        Point2D bounds_br = map.convertGeoPositionToPoint(new GeoPosition(bottom_lat_bounds, right_lon_bounds));
        Rectangle2D r_bounds_tl = new Rectangle2D.Double(bounds_tl.getX(), bounds_tl.getY(), 1, 1);
        Rectangle2D r_bounds_br = new Rectangle2D.Double(bounds_br.getX(), bounds_br.getY(), 1, 1);
        Rectangle2D r_bounds = r_bounds_tl.createUnion(r_bounds_br);

        Point2D sele_tl = map.convertGeoPositionToPoint(new GeoPosition(sele_box[2], sele_box[0]));
        Point2D sele_br = map.convertGeoPositionToPoint(new GeoPosition(sele_box[3], sele_box[1]));
        Rectangle2D r_sele_tl = new Rectangle2D.Double(sele_tl.getX(), sele_tl.getY(), 1, 1);
        Rectangle2D r_sele_br = new Rectangle2D.Double(sele_br.getX(), sele_br.getY(), 1, 1);
        Rectangle2D r_sele = r_sele_tl.createUnion(r_sele_br);

        if (r_sele.intersects(r_bounds)) {
            jButton_work.setEnabled(true);
            //left - lon
            if (sele_box[0] < left_lon_bounds || sele_box[0] > right_lon_bounds) {
                sele_box[0] = left_lon_bounds;
            }
            //right - lon
            if (sele_box[1] > right_lon_bounds || sele_box[1] < left_lon_bounds) {
                sele_box[1] = right_lon_bounds;
            }
            //top - lat
            if (sele_box[2] > top_lat_bounds || sele_box[2] < bottom_lat_bounds) {
                sele_box[2] = top_lat_bounds;
            }
            //bottom - lat
            if (sele_box[3] < bottom_lat_bounds || sele_box[3] > top_lat_bounds) {
                sele_box[3] = bottom_lat_bounds;
            }
            //Intersection Painting
            Rectangle2D rect = r_sele.createIntersection(r_bounds);
            painter_intersection.setGeoPosition(new GeoPosition[]{
                        map.convertPointToGeoPosition(new Point2D.Double(rect.getX(), rect.getY())),
                        map.convertPointToGeoPosition(new Point2D.Double(rect.getX() + rect.getWidth() - 1, rect.getY())),
                        map.convertPointToGeoPosition(new Point2D.Double(rect.getX() + rect.getWidth() - 1, rect.getY() + rect.getHeight() - 1)),
                        map.convertPointToGeoPosition(new Point2D.Double(rect.getX(), rect.getY() + rect.getHeight() - 1))
                    });
            painter_intersection.setColorRegion(new Color(200, 0, 0, 100));
            painter_intersection.setPaintChoosable(true);
            painter_intersection.setFill(true);
        } else {
            jButton_work.setEnabled(false);
            sele_box[0] = left_lon_bounds;
            sele_box[1] = right_lon_bounds;
            sele_box[2] = top_lat_bounds;
            sele_box[3] = bottom_lat_bounds;

            painter_intersection.setGeoPosition(null);
        }

        left_lon_sele = sele_box[0];
        right_lon_sele = sele_box[1];
        top_lat_sele = sele_box[2];
        bottom_lat_sele = sele_box[3];

        jFormattedTextField_left_lon.setText("" + left_lon_sele);
        jFormattedTextField_right_lon.setText("" + right_lon_sele);
        jFormattedTextField_top_lat.setText("" + top_lat_sele);
        jFormattedTextField_bottom_lat.setText("" + bottom_lat_sele);
    }

    private void loadButtonChecker() {
        if (pbf && osm && srtm) {
            resetAllStats();
            jButton_load.setEnabled(true);
        } else {
            jButton_load.setEnabled(false);
        }
    }

    private void resetAllStats() {
        painter_sele = new PolyPainter();
        painter_sele.setColorBorder(new Color(200, 200, 0));
        painter_sele.setColorRegion(new Color(200, 200, 0, 100));
        painter_bounds = new PolyPainter();
        painter_intersection = new PolyPainter();
        bounds = null;
        left_lon_bounds = -1;
        right_lon_bounds = -1;
        top_lat_bounds = -1;
        bottom_lat_bounds = -1;
        left_lon_sele = -1;
        right_lon_sele = -1;
        top_lat_sele = -1;
        bottom_lat_sele = -1;
        jFormattedTextField_left_lon.setText("");
        jFormattedTextField_right_lon.setText("");
        jFormattedTextField_top_lat.setText("");
        jFormattedTextField_bottom_lat.setText("");
        jButton_work.setEnabled(false);

        map.removeMouseListener(painter_sele);
        map.removeMouseMotionListener(painter_sele);
        map.setOverlayPainter(null);
        map.repaint();
    }

private void jButton_in_pbfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_in_pbfActionPerformed
    if (chooser(".pbf", "compressed osm file (pbf)", true, jFormattedTextField_in_pbf, 0)) {
        pbf = true;
        loadButtonChecker();
    }
}//GEN-LAST:event_jButton_in_pbfActionPerformed

private void jButton_out_osmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_out_osmActionPerformed
    if (chooser(".osm", "output osm file", true, jFormattedTextField_out_osm, 1)) {
        osm = true;
        loadButtonChecker();
    }
}//GEN-LAST:event_jButton_out_osmActionPerformed

private void jButton_dir_srtmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dir_srtmActionPerformed
    if (chooser("", "directory for srtm files", false, jFormattedTextField_dir_srtm, 2)) {
        srtm = true;
        loadButtonChecker();
    }
}//GEN-LAST:event_jButton_dir_srtmActionPerformed

private void jButton_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loadActionPerformed
    pbf_w = new PBFOSMWorker(files[0], files[1], files[2]);
    pbf_w.init();
    pbf_w.config(null, jCheckBox_srtm.isSelected());
    pbf_w.execute();

    while (pbf_w.getBounds() == null) {
        try {
            // FIXME can this somehow be replaced by callback
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(PBFtoOSMFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    jButton_load.setEnabled(false);

    bounds = pbf_w.getBounds();
    left_lon_bounds = bounds[0];
    right_lon_bounds = bounds[1];
    top_lat_bounds = bounds[2];
    bottom_lat_bounds = bounds[3];

    setLongLat(new double[]{-1, -1, -1, -1});

    pbf_w.kill();
    pbf_w = null;
    busyLabel.setBusy(false);

    GeoPosition[] geo_array = new GeoPosition[]{
        new GeoPosition(top_lat_bounds, left_lon_bounds),
        new GeoPosition(top_lat_bounds, right_lon_bounds),
        new GeoPosition(bottom_lat_bounds, right_lon_bounds),
        new GeoPosition(bottom_lat_bounds, left_lon_bounds)
    };

    Set<GeoPosition> geo_set = new HashSet<>();
    geo_set.addAll(Arrays.asList(geo_array));

    painter_bounds = new PolyPainter(geo_array, Color.RED, Color.RED, true, false);

    map.addMouseListener(painter_sele);
    map.addMouseMotionListener(painter_sele);

    cp.setPainters(painter_bounds, painter_sele, painter_intersection);
    cp.setCacheable(false);

    map.setOverlayPainter(cp);
    map.setZoom(1);
    map.calculateZoomFrom(geo_set);
    map.repaint();
}//GEN-LAST:event_jButton_loadActionPerformed

private void jButton_workActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_workActionPerformed
    if (pbf_w != null) {
        pbf_w.kill();
    } else {
        pbf_w = new PBFOSMWorker(files[0], files[1], files[2]);
        pbf_w.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equalsIgnoreCase("status")) {
                    jFormattedTextField_progess.setText(evt.getNewValue().toString());
                } else if (pbf_w.getState().toString().equalsIgnoreCase("done")) {
                    busyLabel.setBusy(false);
                    jButton_work.setText("run");
                    jFormattedTextField_progess.setText("");
                    map.addMouseMotionListener(painter_sele);
                    pbf_w = null;
                }
            }
        });
        pbf_w.init();
        pbf_w.config(new double[]{
                    left_lon_sele,
                    right_lon_sele,
                    top_lat_sele,
                    bottom_lat_sele,},
                jCheckBox_srtm.isSelected());
        pbf_w.execute();
        busyLabel.setBusy(true);
        jButton_work.setText("stop");
        map.removeMouseMotionListener(painter_sele);
    }

}//GEN-LAST:event_jButton_workActionPerformed

private void jCheckBox_srtmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_srtmActionPerformed
    if (jCheckBox_srtm.isSelected()) {
        if (files[2] == null) {
            srtm = false;
        }
        jFormattedTextField_dir_srtm.setEnabled(true);
        jButton_dir_srtm.setEnabled(true);
        loadButtonChecker();
    } else {
        srtm = true;
        jFormattedTextField_dir_srtm.setEnabled(false);
        jButton_dir_srtm.setEnabled(false);
        loadButtonChecker();
    }
}//GEN-LAST:event_jCheckBox_srtmActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static org.jdesktop.swingx.JXBusyLabel busyLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler_horStut_05;
    private javax.swing.Box.Filler filler_hor_strut3;
    private javax.swing.Box.Filler filler_pre;
    private javax.swing.JButton jButton_dir_srtm;
    private javax.swing.JButton jButton_in_pbf;
    private javax.swing.JButton jButton_load;
    private javax.swing.JButton jButton_out_osm;
    private static javax.swing.JButton jButton_work;
    private javax.swing.JCheckBox jCheckBox_srtm;
    private static javax.swing.JFormattedTextField jFormattedTextField_bottom_lat;
    private javax.swing.JFormattedTextField jFormattedTextField_dir_srtm;
    private javax.swing.JFormattedTextField jFormattedTextField_in_pbf;
    private static javax.swing.JFormattedTextField jFormattedTextField_left_lon;
    private javax.swing.JFormattedTextField jFormattedTextField_out_osm;
    private javax.swing.JFormattedTextField jFormattedTextField_progess;
    private static javax.swing.JFormattedTextField jFormattedTextField_right_lon;
    private static javax.swing.JFormattedTextField jFormattedTextField_top_lat;
    private javax.swing.JLabel jLabel_bottom_lat;
    private javax.swing.JLabel jLabel_left_lon;
    private javax.swing.JLabel jLabel_osm;
    private javax.swing.JLabel jLabel_pbf;
    private javax.swing.JLabel jLabel_right_lon;
    private javax.swing.JLabel jLabel_srtm;
    private javax.swing.JLabel jLabel_top_lat;
    private javax.swing.JPanel jPanel_leftSide;
    private javax.swing.JPanel jPanel_loader;
    private javax.swing.JPanel jPanel_map;
    private javax.swing.JPanel jPanel_progress;
    private javax.swing.JPanel jPanel_selection;
    private javax.swing.JSplitPane jSplitPane1;
    private org.jdesktop.swingx.JXMapKit jXMapKit;
    // End of variables declaration//GEN-END:variables
}
