/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import amara.Util;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.launcher.client.MasterserverClientUtil;
import amara.launcher.client.comboboxes.ComboboxModel_OwnedCharacters;

/**
 *
 * @author Carl
 */
public class PanLobby_Player extends javax.swing.JPanel{

    public PanLobby_Player(PanLobby panLobby, LobbyPlayer lobbyPlayer){
        initComponents();
        this.panLobby = panLobby;
        this.lobbyPlayer = lobbyPlayer;
        PlayerProfileData playerProfileData = MasterserverClientUtil.getPlayerProfile(lobbyPlayer.getID());
        String avatarResourcePath = PanAvatarSelection.getAvatarResourcePath(playerProfileData.getMeta("avatar"));
        lblIcon.setIcon(Util.getResourceImageIcon(avatarResourcePath, 30, 30));
        lblName.setText(playerProfileData.getLogin());
        int characterID = lobbyPlayer.getPlayerData().getCharacterID();
        boolean isOwnPlayer = (MasterserverClientUtil.getPlayerID() == lobbyPlayer.getID());
        if(isOwnPlayer){
            OwnedGameCharacter[] ownedCharacters = MasterserverClientUtil.getOwnedCharacters();
            cbxCharacter.setModel(new ComboboxModel_OwnedCharacters(MasterserverClientUtil.getOwnedCharacters()));
            for(int i=0;i<ownedCharacters.length;i++){
                if(ownedCharacters[i].getCharacter().getID() == characterID){
                    cbxCharacter.setSelectedIndex(i);
                    break;
                }
            }
        }
        else{
            GameCharacter character = MasterserverClientUtil.getCharacter(characterID);
            cbxCharacter.setModel(new DefaultComboBoxModel(new String[]{ComboboxModel_OwnedCharacters.getItemTitle(character)}));
        }
        cbxCharacter.setEnabled(isOwnPlayer);
        btnKick.setEnabled(panLobby.isOwner() && (!isOwnPlayer));
    }
    private PanLobby panLobby;
    private LobbyPlayer lobbyPlayer;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblSeparator1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        cbxCharacter = new javax.swing.JComboBox();
        btnKick = new javax.swing.JButton();

        setBackground(new java.awt.Color(30, 30, 30));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        add(lblIcon);

        lblSeparator1.setPreferredSize(new java.awt.Dimension(5, 30));
        add(lblSeparator1);

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("???");
        lblName.setPreferredSize(new java.awt.Dimension(115, 30));
        add(lblName);

        cbxCharacter.setPreferredSize(new java.awt.Dimension(110, 30));
        cbxCharacter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCharacterItemStateChanged(evt);
            }
        });
        add(cbxCharacter);

        btnKick.setText("X");
        btnKick.setPreferredSize(new java.awt.Dimension(40, 30));
        btnKick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKickActionPerformed(evt);
            }
        });
        add(btnKick);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCharacterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCharacterItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            OwnedGameCharacter ownedCharacter = MasterserverClientUtil.getOwnedCharacters()[cbxCharacter.getSelectedIndex()];
            int characterID = ownedCharacter.getCharacter().getID();
            if(characterID != lobbyPlayer.getPlayerData().getCharacterID()){
                panLobby.sendMessage(new Message_SetLobbyPlayerData(new LobbyPlayerData(characterID)));
            }
        }
    }//GEN-LAST:event_cbxCharacterItemStateChanged

    private void btnKickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKickActionPerformed
        panLobby.sendMessage(new Message_KickLobbyPlayer(lobbyPlayer.getID()));
    }//GEN-LAST:event_btnKickActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKick;
    private javax.swing.JComboBox cbxCharacter;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSeparator1;
    // End of variables declaration//GEN-END:variables
}
