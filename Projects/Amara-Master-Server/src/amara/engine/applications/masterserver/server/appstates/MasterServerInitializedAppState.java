/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import amara.engine.applications.masterserver.server.network.backends.ReceiveLogoutsBackend;
import amara.engine.network.NetworkServer;
import amara.game.players.ConnectedPlayers;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;

/**
 *
 * @author Carl
 */
public class MasterServerInitializedAppState extends ServerBaseAppState{
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        //Needs to be added after the other backends that want to react to player logouts using the ConnectedPlayers link
        networkServer.addMessageBackend(new ReceiveLogoutsBackend(connectedPlayers));
        System.out.println("Masterserver initialized.");
    }
}
