/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Launcher.java
 *
 * Created on 15.09.2012, 02:33:08
 */
package amara.launcher.client;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import amara.engine.applications.launcher.startscreen.screens.*;
import amara.engine.network.HostInformation;
import amara.engine.settings.DefaultSettings;
import amara.launcher.FrameUtil;
import amara.launcher.client.panels.*;

/**
 *
 * @author Carl
 */
public class ClientLauncher extends JFrame{

    public ClientLauncher(){
        initComponents();
        PanLauncher panLauncher = new PanLauncher();
        panLauncher.setSize(panImage.getSize());
        panImage.add(panLauncher);
        txtMasterserverPort.setText("" + DefaultSettings.NETWORK_PORT);
        Toolkit.getDefaultToolkit().addAWTEventListener(keyListener, AWTEvent.KEY_EVENT_MASK);
        getContentPane().requestFocus();
        FrameUtil.initFrameSpecials(this);
        FrameUtil.centerFrame(this);
        //checkForUpdate();
    }
    private boolean wasUpdateNeeded = false;
    private PanLogin forcedLoginPanel;
    private AWTEventListener keyListener = new AWTEventListener(){

        public void eventDispatched(AWTEvent event){
            KeyEvent keyEvent = (KeyEvent) event;
            if(keyEvent.getID() == KeyEvent.KEY_RELEASED){
                switch(keyEvent.getKeyCode()){
                    case KeyEvent.VK_NUMPAD1:
                        forcedLoginPanel = new PanLogin_JME(new LoginScreen_Forest());
                        break;
                }
            }
        }
    };
    
    /*private UpdateFile[] filesToUpdate;
    
    private void checkForUpdate(){
        filesToUpdate = Updater.getUpdateFiles();
        new Thread(new Runnable(){

            public void run(){
                update();
            }
        }).start();
    }
    
    private void update(){
        btnPlay.setEnabled(false);
        pbrCompleteUpdate.setMaximum(filesToUpdate.length);
        for(int i=0;i<filesToUpdate.length;i++){
            UpdateFile updateFile = filesToUpdate[i];
            pbrCompleteUpdate.setString(updateFile.getFilePath());
            updateFile(updateFile);
            pbrCompleteUpdate.setValue(i + 1);
        }
        pbrCompleteUpdate.setString("Das Spiel ist auf dem neuesten Stand.");
        pbrCurrentFile.setString("");
        if(wasUpdateNeeded){
            pbrCurrentFile.setString("Damit das Update aktiv wird, starte das Spiel neu.");
            btnPlay.setText("Beenden");
        }
        btnPlay.setEnabled(true);
    }
    
    private void updateFile(UpdateFile updateFile){
        if(updateFile.isDirectory()){
            FileManager.createDirectoryIfNotExists(updateFile.getFilePath());
        }
        else if(updateFile.needsUpdate()){
            try{
                int serverFileSize = updateFile.getServerFile_Size();
                pbrCurrentFile.setMaximum(serverFileSize);
                pbrCurrentFile.setString("Initialisiere Download (" + (serverFileSize / 100) + " kB)");
                InputStream inputStream = updateFile.getServerFile_URL("content").openStream();
                FileOutputStream fileOutputStream = new FileOutputStream(updateFile.getFilePath());
                int writtenBytes = 0;
                byte[] buffer = new byte[2048];
                int readBytes;
                while((readBytes = inputStream.read(buffer)) != -1){
                    fileOutputStream.write(buffer, 0, readBytes);
                    writtenBytes += readBytes;
                    pbrCurrentFile.setString(Util.getPercentage_Rounded(serverFileSize, writtenBytes) + "%");
                    pbrCurrentFile.setValue(writtenBytes);
                }
                inputStream.close();
                fileOutputStream.close();
            }catch(Exception ex){
                System.out.println("Error while downloading file '" + updateFile.getFilePath() + "'.");
            }
            VersionManager.getInstance().onFileUpdated(updateFile.getFilePath());
            wasUpdateNeeded = true;
        }
        else{
            pbrCurrentFile.setValue(pbrCurrentFile.getMaximum());
            pbrCurrentFile.setString("Datei vollst�ndig");
        }
    }*/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panImage = new javax.swing.JPanel();
        cbxMasterserverHost = new javax.swing.JComboBox();
        txtMasterserverPort = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        pbrCurrentFile = new javax.swing.JProgressBar();
        pbrCompleteUpdate = new javax.swing.JProgressBar();
        btnPlay = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        cbxMasterserverHost.setEditable(true);
        cbxMasterserverHost.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "141.70.100.145" }));

        javax.swing.GroupLayout panImageLayout = new javax.swing.GroupLayout(panImage);
        panImage.setLayout(panImageLayout);
        panImageLayout.setHorizontalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panImageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxMasterserverHost, 0, 146, Short.MAX_VALUE)
                    .addComponent(txtMasterserverPort))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panImageLayout.setVerticalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panImageLayout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addComponent(cbxMasterserverHost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMasterserverPort, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pbrCurrentFile.setString("");
        pbrCurrentFile.setStringPainted(true);

        pbrCompleteUpdate.setString("");
        pbrCompleteUpdate.setStringPainted(true);

        btnPlay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbrCurrentFile, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(pbrCompleteUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pbrCurrentFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbrCompleteUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
    if(wasUpdateNeeded){
        System.exit(0);
    }
    else{
        String host = cbxMasterserverHost.getSelectedItem().toString();
        int port = Integer.parseInt(txtMasterserverPort.getText());
        Toolkit.getDefaultToolkit().removeAWTEventListener(keyListener);
        PanLogin panLogin = ((forcedLoginPanel != null)?forcedLoginPanel:new PanLogin_Swing());
        MainFrame mainFrame = new MainFrame(new HostInformation(host, port), panLogin);
        this.setVisible(false);
        mainFrame.setVisible(true);
    }
}//GEN-LAST:event_btnPlayActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        FrameUtil.initProgramProperties();
        java.awt.EventQueue.invokeLater(new Runnable(){

            public void run(){
                new ClientLauncher().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JComboBox cbxMasterserverHost;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panImage;
    private javax.swing.JProgressBar pbrCompleteUpdate;
    private javax.swing.JProgressBar pbrCurrentFile;
    private javax.swing.JTextField txtMasterserverPort;
    // End of variables declaration//GEN-END:variables
}
