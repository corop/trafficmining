package de.lmu.ifi.dbs.trafficmining.ui;

import de.lmu.ifi.dbs.trafficmining.TileServer;
import de.lmu.ifi.dbs.trafficmining.TileServerFactory;
import de.lmu.ifi.dbs.trafficmining.painter.MapBoundsPainter;
import de.lmu.ifi.dbs.trafficmining.painter.MapSelectionPainter;
import de.lmu.ifi.dbs.trafficmining.utils.MapBounds;
import de.lmu.ifi.dbs.trafficmining.utils.PbfBoundsLoader;
import de.lmu.ifi.dbs.trafficmining.utils.PbfOsmLoader;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

public class PbfImportFrame extends javax.swing.JFrame {

    static final Logger log = Logger.getLogger(PbfImportFrame.class.getName());
    public final String EVT_GRAPH_LOADED = "EVT_GRAPH_LOADED";
    private final MapSelectionPainter selectionPainter = new MapSelectionPainter();
    private JXMapViewer map = null;
    private String recentlyUsedDirectory = "";
    private PbfOsmLoader worker = null;
    private MapBounds pbfBounds;
    private File pbfFile;
    private File osmFile;
    private File srtmFile;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Creates new form NewJFrame
     */
    public PbfImportFrame() {
        initComponents();
        map = mapKit.getMainMap();
        map.addMouseListener(selectionPainter);
        map.addMouseMotionListener(selectionPainter);

        try {
            TileServer defaultServer = TileServerFactory.get().getDefaultServer();
            setMapTileServer(defaultServer);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }

    }

    public void addOsmLoadListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    private void convert() {
        try {
            if (worker != null) {
                worker.cancel(true);
                importButton.setText("convert");
                worker = null;
            } else {
                worker = new PbfOsmLoader(pbfFile, osmFile, srtmFile,
                        selectionPainter.getBounds(), srtmCheckbox.isSelected());
                // manage progress bar
                worker.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingWorker sw = (SwingWorker) evt.getSource();
                        if (sw.getState() == SwingWorker.StateValue.DONE) {
                            importButton.setText("convert");
                            progressBar.setIndeterminate(false);
                            worker = null;
                        }
                    }
                });
                // fire property change if according checkbox is selected
                worker.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        SwingWorker sw = (SwingWorker) evt.getSource();
                        if (sw.getState() == SwingWorker.StateValue.DONE
                                && autoLoadCheckBox.isSelected()) {
                            pcs.firePropertyChange(EVT_GRAPH_LOADED, null, osmFile);
                            dispose();
                        }
                    }
                });

                worker.execute();
                progressBar.setIndeterminate(true);
                importButton.setText("stop");
            }
        } catch (IOException ex) {
            Logger.getLogger(PbfImportFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane();
        javax.swing.JPanel leftPanel = new javax.swing.JPanel();
        javax.swing.JPanel loaderPanel = new javax.swing.JPanel();
        javax.swing.JLabel pbfLabel = new javax.swing.JLabel();
        pbfFilenameLabel = new javax.swing.JFormattedTextField();
        setInputFileButton = new javax.swing.JButton();
        javax.swing.JLabel osmLabel = new javax.swing.JLabel();
        osmFilenameLabel = new javax.swing.JFormattedTextField();
        setOutputFileButton = new javax.swing.JButton();
        srtmCheckbox = new javax.swing.JCheckBox();
        srtmDirectoryLabel = new javax.swing.JFormattedTextField();
        setSrtmDirButton = new javax.swing.JButton();
        autoLoadCheckBox = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JPanel progressPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        javax.swing.Box.Filler filler = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        javax.swing.Box.Filler filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        importButton = new javax.swing.JButton();
        javax.swing.JButton closeButton = new javax.swing.JButton();
        javax.swing.JPanel mapPanel = new javax.swing.JPanel();
        mapKit = new org.jdesktop.swingx.JXMapKit();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Convert PBF");

        leftPanel.setLayout(new java.awt.GridBagLayout());

        loaderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Loader"));
        loaderPanel.setLayout(new java.awt.GridBagLayout());

        pbfLabel.setText("PBF source");
        pbfLabel.setMaximumSize(new java.awt.Dimension(90, 15));
        pbfLabel.setMinimumSize(new java.awt.Dimension(90, 15));
        pbfLabel.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loaderPanel.add(pbfLabel, gridBagConstraints);

        pbfFilenameLabel.setEditable(false);
        pbfFilenameLabel.setMinimumSize(new java.awt.Dimension(150, 20));
        pbfFilenameLabel.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        loaderPanel.add(pbfFilenameLabel, gridBagConstraints);

        setInputFileButton.setText("...");
        setInputFileButton.setMaximumSize(null);
        setInputFileButton.setMinimumSize(null);
        setInputFileButton.setPreferredSize(new java.awt.Dimension(65, 20));
        setInputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setInputFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        loaderPanel.add(setInputFileButton, gridBagConstraints);

        osmLabel.setText("OSM output");
        osmLabel.setMaximumSize(new java.awt.Dimension(90, 15));
        osmLabel.setMinimumSize(new java.awt.Dimension(90, 15));
        osmLabel.setPreferredSize(new java.awt.Dimension(90, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loaderPanel.add(osmLabel, gridBagConstraints);

        osmFilenameLabel.setEditable(false);
        osmFilenameLabel.setMinimumSize(new java.awt.Dimension(150, 20));
        osmFilenameLabel.setOpaque(false);
        osmFilenameLabel.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        loaderPanel.add(osmFilenameLabel, gridBagConstraints);

        setOutputFileButton.setText("...");
        setOutputFileButton.setMaximumSize(null);
        setOutputFileButton.setMinimumSize(null);
        setOutputFileButton.setPreferredSize(new java.awt.Dimension(65, 20));
        setOutputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOutputFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        loaderPanel.add(setOutputFileButton, gridBagConstraints);

        srtmCheckbox.setToolTipText("<html>\nUse SRTM?<br>\nyes or no\n</html>");
        srtmCheckbox.setMaximumSize(new java.awt.Dimension(26, 21));
        srtmCheckbox.setMinimumSize(new java.awt.Dimension(26, 21));
        srtmCheckbox.setPreferredSize(new java.awt.Dimension(26, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loaderPanel.add(srtmCheckbox, gridBagConstraints);

        srtmDirectoryLabel.setEditable(false);
        srtmDirectoryLabel.setMinimumSize(new java.awt.Dimension(150, 20));
        srtmDirectoryLabel.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        loaderPanel.add(srtmDirectoryLabel, gridBagConstraints);

        setSrtmDirButton.setText("...");
        setSrtmDirButton.setMaximumSize(null);
        setSrtmDirButton.setMinimumSize(null);
        setSrtmDirButton.setPreferredSize(new java.awt.Dimension(65, 20));
        setSrtmDirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSrtmDirButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        loaderPanel.add(setSrtmDirButton, gridBagConstraints);

        autoLoadCheckBox.setSelected(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loaderPanel.add(autoLoadCheckBox, gridBagConstraints);

        jLabel1.setText("SRTM directory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        loaderPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("load graph after conversion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        loaderPanel.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        leftPanel.add(loaderPanel, gridBagConstraints);

        progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));
        progressPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 2, 1, 2);
        progressPanel.add(progressBar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        progressPanel.add(filler, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        leftPanel.add(progressPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        leftPanel.add(filler1, gridBagConstraints);

        importButton.setText("Ok");
        importButton.setPreferredSize(null);
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });
        jPanel1.add(importButton);

        closeButton.setText("Cancel");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jPanel1.add(closeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        leftPanel.add(jPanel1, gridBagConstraints);

        splitPane.setLeftComponent(leftPanel);

        mapPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        mapPanel.setLayout(new java.awt.GridBagLayout());

        mapKit.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        mapKit.setAddressLocationShown(false);
        mapKit.setPreferredSize(new java.awt.Dimension(800, 600));
        mapKit.setZoom(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mapPanel.add(mapKit, gridBagConstraints);

        splitPane.setRightComponent(mapPanel);

        getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setMapTileServer(TileServer ts) {
        map.setTileFactory(ts.getTileFactory());
        ((DefaultTileFactory) map.getTileFactory()).setThreadPoolSize(8);
        map.setRestrictOutsidePanning(true);
        map.setHorizontalWrapped(false);
    }

    private void loadApproximation() {
        try {
            final PbfBoundsLoader loader = new PbfBoundsLoader(pbfFile);
            loader.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(PbfBoundsLoader.EVT_BOUNDS)) {
                        setBounds(loader.getMapBounds());
                    }
                }
            });
            loader.loadAsync();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PbfImportFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setBounds(MapBounds bounds) {
        this.pbfBounds = bounds;

        Color area = new Color(0, 0, 255, 30);
        Painter boundsPainter = new MapBoundsPainter(pbfBounds, area, Color.BLUE);

        CompoundPainter painters = new CompoundPainter<>(boundsPainter, selectionPainter);
        map.setOverlayPainter(painters);
        map.setZoom(1);
        map.calculateZoomFrom(pbfBounds.toSet());
        map.repaint();
    }

    private File showFilechooser(final String suffix, final String description,
            final boolean fileOnly, JFormattedTextField target) {
        JFileChooser fileChooser = new JFileChooser(recentlyUsedDirectory);
        fileChooser.setMultiSelectionEnabled(false);

        if (!fileOnly) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(suffix);
                }

                @Override
                public String getDescription() {
                    return suffix + " - " + description;
                }
            });
        }

        // If the user pressed "okay", try to load the files
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(getContentPane())) {
            recentlyUsedDirectory = fileChooser.getCurrentDirectory().getAbsolutePath();
            target.setText(fileChooser.getSelectedFile().getAbsolutePath());
            return fileChooser.getSelectedFile();
        }
        return null;
    }

private void setInputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setInputFileButtonActionPerformed
    pbfFile = showFilechooser(".pbf", "compressed osm file (pbf)", true, pbfFilenameLabel);
    if (pbfFile != null) {
        loadApproximation();
    }
}//GEN-LAST:event_setInputFileButtonActionPerformed

private void setOutputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOutputFileButtonActionPerformed
    osmFile = showFilechooser(".osm", "output osm file", true, osmFilenameLabel);
}//GEN-LAST:event_setOutputFileButtonActionPerformed

private void setSrtmDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSrtmDirButtonActionPerformed
    srtmFile = showFilechooser("", "directory for srtm files", false, srtmDirectoryLabel);
}//GEN-LAST:event_setSrtmDirButtonActionPerformed

private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
    convert();
}//GEN-LAST:event_importButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoLoadCheckBox;
    private javax.swing.JButton importButton;
    private org.jdesktop.swingx.JXMapKit mapKit;
    private javax.swing.JFormattedTextField osmFilenameLabel;
    private javax.swing.JFormattedTextField pbfFilenameLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton setInputFileButton;
    private javax.swing.JButton setOutputFileButton;
    private javax.swing.JButton setSrtmDirButton;
    private javax.swing.JCheckBox srtmCheckbox;
    private javax.swing.JFormattedTextField srtmDirectoryLabel;
    // End of variables declaration//GEN-END:variables
}