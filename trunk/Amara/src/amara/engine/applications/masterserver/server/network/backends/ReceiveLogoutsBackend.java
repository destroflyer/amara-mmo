/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.network.*;
import amara.engine.network.messages.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.*;

/**
 *
 * @author Carl
 */
public class ReceiveLogoutsBackend implements MessageBackend{

    public ReceiveLogoutsBackend(ConnectedPlayers connectedPlayers){
        this.connectedPlayers = connectedPlayers;
    }
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_Logout){
            Message_Logout message = (Message_Logout) receivedMessage;
            logout(messageResponse.getClientID());
        }
        else if(receivedMessage instanceof Message_ClientDisconnection){
            Message_ClientDisconnection message = (Message_ClientDisconnection) receivedMessage;
            logout(messageResponse.getClientID());
        }
    }
    
    private void logout(int clientID){
        Player player = connectedPlayers.getPlayer(clientID);
        connectedPlayers.logout(clientID);
        System.out.println("Logout '" + player.getLogin() + "' (#" + player.getID() + ")");
    }
}
