/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * panPlay.java
 *
 * Created on 02.08.2012, 23:56:34
 */
package amara.launcher.client.panels;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import amara.Util;
import amara.engine.settings.*;
import amara.launcher.FrameUtil;
import amara.launcher.client.buttons.DefaultButtonBuilder;

/**
 *
 * @author Carl
 */
public class PanSettings extends javax.swing.JPanel{

    public PanSettings(){
        initComponents();
        JComponent btnSave = FrameUtil.addImageBackgroundButton(panContainer_btnSave, new DefaultButtonBuilder("default_150x50", "Save"));
        btnSave.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt){
                super.mouseClicked(evt);
                saveSettings();
            }
        });
        loadSystemInformation();
        loadSettings();
    }
    
    private void loadSystemInformation(){
        lblOperatingSystem.setText(System.getProperty("os.name"));
        lblOperatingSystemVersion.setText(System.getProperty("os.version"));
        lblJavaVersion.setText(System.getProperty("java.version"));
        lblUser.setText(System.getProperty("user.name"));
        lblSystemResolution.setText(getResolutionString(Util.getScreenResolution()));
    }
    
    private void loadSettings(){
        for(int i=0;i<DefaultSettings.RESOLUTIONS.length;i++){
            Dimension resolution = DefaultSettings.RESOLUTIONS[i];
            cbxResolution.addItem(getResolutionString(resolution));
            if((resolution.getWidth() == Settings.getInt("resolution_width")) && (resolution.getHeight() == Settings.getInt("resolution_height"))){
                cbxResolution.setSelectedIndex(i);
            }
        }
        cbxFullscreen.setSelected(Settings.getBoolean("fullscreen"));
        for(int i=0;i<DefaultSettings.ANTIALIASING_SAMPLES.length;i++){
            int antiAliasingSamples = DefaultSettings.ANTIALIASING_SAMPLES[i];
            cbxAntialiasing.addItem((antiAliasingSamples != 0)?antiAliasingSamples + "x":"Deactivated");
            if(antiAliasingSamples == Settings.getInt("antialiasing")){
                cbxAntialiasing.setSelectedIndex(i);
            }
        }
        cbxVSync.setSelected(Settings.getBoolean("vsync"));
        cbxHardwareSkinning.setSelected(Settings.getBoolean("hardware_skinning"));
        sldShadowQuality.setValue(Settings.getInt("shadow_quality"));
    }
    
    private static String getResolutionString(Dimension resolution){
        return ((int) resolution.getWidth()) + "x" + ((int) resolution.getHeight());
    }
    
    private void saveSettings(){
        Dimension resolution = DefaultSettings.RESOLUTIONS[cbxResolution.getSelectedIndex()];
        Settings.set("resolution_width", (int) resolution.getWidth());
        Settings.set("resolution_height", (int) resolution.getHeight());
        Settings.set("fullscreen", cbxFullscreen.isSelected());
        Settings.set("antialiasing", DefaultSettings.ANTIALIASING_SAMPLES[cbxAntialiasing.getSelectedIndex()]);
        Settings.set("vsync", cbxVSync.isSelected());
        Settings.set("hardware_skinning", cbxHardwareSkinning.isSelected());
        Settings.set("shadow_quality", sldShadowQuality.getValue());
        Settings.saveFile();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxResolution = new javax.swing.JComboBox();
        cbxFullscreen = new javax.swing.JCheckBox();
        cbxAntialiasing = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxVSync = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbxHardwareSkinning = new javax.swing.JCheckBox();
        sldShadowQuality = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblOperatingSystem = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblOperatingSystemVersion = new javax.swing.JLabel();
        lblJavaVersion = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSystemResolution = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panContainer_btnSave = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Graphics"));

        jLabel1.setText("Resolution:");

        cbxFullscreen.setText("Activated");

        jLabel2.setText("Antialiasing:");

        jLabel3.setText("Fullscreen");

        jLabel4.setText("VSync");

        cbxVSync.setText("Activated");

        jLabel10.setText("Shadow quality:");

        jLabel11.setText("Hardware skinning:");

        cbxHardwareSkinning.setText("Activated");

        sldShadowQuality.setMaximum(4);
        sldShadowQuality.setMinorTickSpacing(1);
        sldShadowQuality.setPaintTicks(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxVSync, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(cbxAntialiasing, javax.swing.GroupLayout.Alignment.TRAILING, 0, 146, Short.MAX_VALUE)
                    .addComponent(cbxResolution, javax.swing.GroupLayout.Alignment.TRAILING, 0, 146, Short.MAX_VALUE)
                    .addComponent(cbxFullscreen, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(cbxHardwareSkinning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldShadowQuality, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxFullscreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxAntialiasing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxVSync, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxHardwareSkinning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sldShadowQuality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("System information"));

        jLabel5.setText("OS:");

        lblOperatingSystem.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblOperatingSystem.setText("?");

        jLabel6.setText("OS version:");

        lblOperatingSystemVersion.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblOperatingSystemVersion.setText("?");

        lblJavaVersion.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJavaVersion.setText("?");

        lblUser.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblUser.setText("?");

        jLabel7.setText("User:");

        jLabel8.setText("Java version");

        lblSystemResolution.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSystemResolution.setText("?");

        jLabel9.setText("Resolution:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSystemResolution, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(lblJavaVersion, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(lblOperatingSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(lblOperatingSystemVersion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(lblOperatingSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(lblOperatingSystemVersion, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(lblJavaVersion, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(lblSystemResolution, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout panContainer_btnSaveLayout = new javax.swing.GroupLayout(panContainer_btnSave);
        panContainer_btnSave.setLayout(panContainer_btnSaveLayout);
        panContainer_btnSaveLayout.setHorizontalGroup(
            panContainer_btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panContainer_btnSaveLayout.setVerticalGroup(
            panContainer_btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panContainer_btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(panContainer_btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxAntialiasing;
    private javax.swing.JCheckBox cbxFullscreen;
    private javax.swing.JCheckBox cbxHardwareSkinning;
    private javax.swing.JComboBox cbxResolution;
    private javax.swing.JCheckBox cbxVSync;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblJavaVersion;
    private javax.swing.JLabel lblOperatingSystem;
    private javax.swing.JLabel lblOperatingSystemVersion;
    private javax.swing.JLabel lblSystemResolution;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel panContainer_btnSave;
    private javax.swing.JSlider sldShadowQuality;
    // End of variables declaration//GEN-END:variables
}