/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 02.08.2012, 23:50:37
 */
package amara.launcher.client;

import javax.swing.JPanel;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.applications.masterserver.client.appstates.LoginAppState.LoginResult;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.HostInformation;
import amara.engine.network.NetworkClient;
import amara.engine.network.messages.protocol.*;
import amara.launcher.FrameUtil;
import amara.launcher.client.panels.*;
import amara.launcher.client.protocol.*;

/**
 *
 * @author Carl
 */
public class MainFrame extends javax.swing.JFrame{

    public MainFrame(HostInformation masterserverHostInformation, PanLogin panLogin){
        initComponents();
        instance = this;
        this.masterserverHostInformation = masterserverHostInformation;
        this.panLogin = panLogin;
        setDisplayedPanel(panLogin);
        panLogin.start();
        FrameUtil.initFrameSpecials(this);
        FrameUtil.centerFrame(this);
    }
    private static MainFrame instance;
    private PanLogin panLogin;
    private HostInformation masterserverHostInformation;
    private MasterserverClientApplication masterClient;
    
    public void setDisplayedPanel(JPanel panel){
        panContainer.removeAll();
        panContainer.add(panel);
        panContainer.updateUI();
    }
    
    public void login(final AuthentificationInformation authentificationInformation){
        new Thread(new Runnable(){

            @Override
            public void run(){
                panLogin.showIsLoading(true);
                masterClient = new MasterserverClientApplication(masterserverHostInformation, authentificationInformation);
                masterClient.start();
                LoginResult loginResult;
                while(true){
                    LoginAppState loginAppState = masterClient.getStateManager().getState(LoginAppState.class);
                    if((loginAppState != null) && (loginAppState.getResult() != LoginAppState.LoginResult.PENDING)){
                        loginResult = loginAppState.getResult();
                        break;
                    }
                    try{
                        Thread.sleep(100);
                    }catch(Exception ex){
                    }
                }
                panLogin.showIsLoading(false);
                switch(loginResult){
                    case NO_CONNECTION_TO_MASTERSERVER:
                        FrameUtil.showMessageDialog(MainFrame.this, "Couldn't connect to masterserver.", FrameUtil.MessageType.ERROR);
                        break;
                    
                    case FAILED:
                        FrameUtil.showMessageDialog(MainFrame.this, "Login failed. Possible reasons:\n\n- Wrong login\n- Wrong password", FrameUtil.MessageType.ERROR);
                        break;
                    
                    case SUCCESSFUL:
                        panLogin.close();
                        setDisplayedPanel(new PanMainMenu());
                        break;
                }
            }
        }).start();
    }
    
    public String getLogin(){
        return masterClient.getAuthentificationInformation().getLogin();
    }
    
    public PlayerProfileData getPlayerProfile(String login){
        PlayerProfilesAppState playerProfilesAppState = masterClient.getStateManager().getState(PlayerProfilesAppState.class);
        playerProfilesAppState.onUpdateStarted(login);
        PlayerProfileData playerProfileData = playerProfilesAppState.getProfile(login);
        long cachedTimestamp = ((playerProfileData != null)?playerProfileData.getTimestamp():-1);
        NetworkClient networkClient = getNetworkClient();
        networkClient.sendMessage(new Message_GetPlayerProfileData(login, cachedTimestamp));
        while(true){
            if(!playerProfilesAppState.isUpdating(login)){
                playerProfileData = playerProfilesAppState.getProfile(login);
                break;
            }
            try{
                Thread.sleep(100);
            }catch(Exception ex){
            }
        }
        return playerProfileData;
    }
    
    public PlayerStatus getPlayerStatus(int playerID){
        PlayerStatusesAppState playerStatusesAppState = masterClient.getStateManager().getState(PlayerStatusesAppState.class);
        playerStatusesAppState.onUpdateStarted(playerID);
        NetworkClient networkClient = getNetworkClient();
        networkClient.sendMessage(new Message_GetPlayerStatus(playerID));
        while(true){
            if(!playerStatusesAppState.isUpdating(playerID)){
                return playerStatusesAppState.getStatus(playerID);
            }
            try{
                Thread.sleep(100);
            }catch(Exception ex){
            }
        }
    }

    public NetworkClient getNetworkClient(){
        return masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }

    public static MainFrame getInstance(){
        return instance;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panContainer.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panContainer;
    // End of variables declaration//GEN-END:variables
}
