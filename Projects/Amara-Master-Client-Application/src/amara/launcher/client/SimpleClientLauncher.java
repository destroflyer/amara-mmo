/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client;

import amara.core.Launcher_Core;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;
import amara.engine.network.messages.protocol.*;
import amara.launcher.Launcher_Game;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;

/**
 *
 * @author Carl
 */
public class SimpleClientLauncher extends javax.swing.JFrame{

    public SimpleClientLauncher(){
        initComponents();
        panStart.setVisible(false);
    }
    private MasterserverClientApplication masterClient;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panMasterserver = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMasterserverPort = new javax.swing.JTextField();
        btnMasterserverConnect = new javax.swing.JButton();
        txtMasterserverHost = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        panStart = new javax.swing.JPanel();
        btnStartGame = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amara - Client");

        panMasterserver.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Masterserver:");

        txtMasterserverPort.setText("33900");

        btnMasterserverConnect.setText("Connect");
        btnMasterserverConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasterserverConnectActionPerformed(evt);
            }
        });

        txtMasterserverHost.setText("localhost");

        jLabel2.setText("Port:");

        jLabel3.setText("Login:");

        jLabel4.setText("Password:");

        javax.swing.GroupLayout panMasterserverLayout = new javax.swing.GroupLayout(panMasterserver);
        panMasterserver.setLayout(panMasterserverLayout);
        panMasterserverLayout.setHorizontalGroup(
            panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMasterserverLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(panMasterserverLayout.createSequentialGroup()
                        .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMasterserverPort)
                            .addComponent(txtMasterserverHost)
                            .addComponent(btnMasterserverConnect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(txtLogin)
                            .addComponent(txtPassword))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        panMasterserverLayout.setVerticalGroup(
            panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMasterserverLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMasterserverHost)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMasterserverPort)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLogin)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panMasterserverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPassword)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMasterserverConnect)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panStart.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnStartGame.setText("Start Game");
        btnStartGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panStartLayout = new javax.swing.GroupLayout(panStart);
        panStart.setLayout(panStartLayout);
        panStartLayout.setHorizontalGroup(
            panStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panStartLayout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(btnStartGame, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panStartLayout.setVerticalGroup(
            panStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panStartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStartGame)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panMasterserver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panMasterserver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasterserverConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterserverConnectActionPerformed
        HostInformation hostInformation = new HostInformation(txtMasterserverHost.getText(), Integer.parseInt(txtMasterserverPort.getText()));
        AuthentificationInformation authentificationInformation = new AuthentificationInformation(txtLogin.getText(), txtPassword.getText());
        try{
            masterClient = new MasterserverClientApplication(hostInformation);
            masterClient.start();
            NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
            networkClient.sendMessage(new Message_Login(authentificationInformation));
            panStart.setVisible(true);
        }catch(ServerConnectionException ex){
            ex.printStackTrace();
        }catch(ServerConnectionTimeoutException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnMasterserverConnectActionPerformed

    private void btnStartGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartGameActionPerformed
        NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_CreateLobby(new LobbyData("testmap")));
        networkClient.sendMessage(new Message_SetLobbyPlayerData(new LobbyPlayerData(1)));
        networkClient.sendMessage(new Message_InviteLobbyPlayer(2));
        networkClient.sendMessage(new Message_StartGame());
    }//GEN-LAST:event_btnStartGameActionPerformed

    public static void main(String args[]){
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        java.awt.EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run(){
                new SimpleClientLauncher().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMasterserverConnect;
    private javax.swing.JButton btnStartGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panMasterserver;
    private javax.swing.JPanel panStart;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtMasterserverHost;
    private javax.swing.JTextField txtMasterserverPort;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
