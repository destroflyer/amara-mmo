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

import java.util.LinkedList;
import amara.Util;
import amara.engine.applications.masterserver.server.network.messages.Message_StartGame;
import amara.engine.network.NetworkClient;
import amara.game.games.PlayerData;
import amara.launcher.FrameUtil;
import amara.launcher.client.MainFrame;
import amara.launcher.client.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
public class PanPlay extends javax.swing.JPanel{

    public PanPlay(){
        initComponents();
        lblMapIcon.setIcon(Util.getResourceImageIcon("/Interface/client/unknown.jpg", 120, 120));
    }
    private LinkedList<PlayerProfileData> players = new LinkedList<PlayerProfileData>();
    private PanPlay_Player[] panPlay_Players;
    
    private void updatePlayersList(){
        panPlayers.removeAll();
        int y = 0;
        panPlay_Players = new PanPlay_Player[players.size()];
        for(int i=0;i<players.size();i++){
            PanPlay_Player panPlay_Player = new PanPlay_Player();
            panPlay_Player.setLocation(0, y);
            panPlay_Player.setSize(300, 30);
            panPlay_Player.setPlayer(players.get(i));
            panPlayers.add(panPlay_Player);
            panPlay_Players[i] = panPlay_Player;
            y += 30;
        }
        panPlayers.updateUI();
    }
    
    private boolean isPlayerParticipating(String login){
        for(PlayerProfileData player : players){
            if(player.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrHostOrConnect = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMapIcon = new javax.swing.JLabel();
        panPlayers = new javax.swing.JPanel();
        btnInvite = new javax.swing.JButton();
        cbxMapName = new javax.swing.JComboBox();
        btnClearPlayers = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setBackground(new java.awt.Color(30, 30, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Map:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Players:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Options:");

        panPlayers.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panPlayersLayout = new javax.swing.GroupLayout(panPlayers);
        panPlayers.setLayout(panPlayersLayout);
        panPlayersLayout.setHorizontalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panPlayersLayout.setVerticalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnInvite.setText("Invite");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        cbxMapName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "testmap", "destroforest" }));

        btnClearPlayers.setText("Clear");
        btnClearPlayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPlayersActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMapIcon)
                    .addComponent(cbxMapName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvite, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInvite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnClearPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMapIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxMapName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 125, Short.MAX_VALUE))
                            .addComponent(panPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        String login = FrameUtil.showInputDialog(this, "Enter login:");
        if((login != null) && (!login.isEmpty())){
            PlayerProfileData playerProfileData = MainFrame.getInstance().getPlayerProfile(login);
            if(playerProfileData != null){
                if(isPlayerParticipating(login)){
                    FrameUtil.showMessageDialog(this, "'" + login + "' is already participating.", FrameUtil.MessageType.WARNING);
                }
                else{
                    players.add(playerProfileData);
                    updatePlayersList();
                }
            }
            else{
                FrameUtil.showMessageDialog(this, "Player '" + login + "' wasn't found.", FrameUtil.MessageType.ERROR);
            }
        }
    }//GEN-LAST:event_btnInviteActionPerformed

    private void btnClearPlayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPlayersActionPerformed
        players.clear();
        panPlayers.removeAll();
        panPlayers.updateUI();
    }//GEN-LAST:event_btnClearPlayersActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String mapName = cbxMapName.getSelectedItem().toString();
        PlayerData[] playerDatas = new PlayerData[players.size()];
        for(int i=0;i<players.size();i++){
            PlayerProfileData playerProfileData = players.get(i);
            PanPlay_Player panPlay_Player = panPlay_Players[i];
            playerDatas[i] = new PlayerData(playerProfileData.getID(), panPlay_Player.getPlayerEntityTemplate());
        }
        NetworkClient networkClient = MainFrame.getInstance().getNetworkClient();
        networkClient.sendMessage(new Message_StartGame(mapName, playerDatas));
    }//GEN-LAST:event_btnStartActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrHostOrConnect;
    private javax.swing.JButton btnClearPlayers;
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox cbxMapName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblMapIcon;
    private javax.swing.JPanel panPlayers;
    // End of variables declaration//GEN-END:variables
}
