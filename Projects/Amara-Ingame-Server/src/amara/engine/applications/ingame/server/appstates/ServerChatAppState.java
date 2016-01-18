/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.network.NetworkServer;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;

/**
 *
 * @author Carl
 */
public class ServerChatAppState extends ServerBaseAppState{

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceiveChatMessagesBackend(mainApplication));
    }
}
