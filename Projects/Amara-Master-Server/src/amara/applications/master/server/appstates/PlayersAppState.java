/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.master.network.messages.objects.PlayerStatus;
import amara.applications.master.server.games.RunningGames;
import amara.applications.master.server.network.backends.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.core.encoding.*;
import amara.core.files.FileManager;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

/**
 *
 * @author Carl
 */
public class PlayersAppState extends ServerBaseAppState{

    private Encoder passwordEncoder;
    private ConnectedPlayers connectedPlayers = new ConnectedPlayers();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        initializePasswordEncoder();
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        PlayersContentsAppState playersContentsAppState = getAppState(PlayersContentsAppState.class);
        networkServer.addMessageBackend(new ReceiveLoginsBackend(databaseAppState, this));
        networkServer.addMessageBackend(new SendPlayerProfilesDataBackend(databaseAppState));
        networkServer.addMessageBackend(new SendPlayerStatusesBackend(this));
        networkServer.addMessageBackend(new EditUserMetaBackend(databaseAppState, connectedPlayers));
        networkServer.addMessageBackend(new SendGameContentsBackend(databaseAppState, connectedPlayers, playersContentsAppState));
        networkServer.addMessageBackend(new EditActiveCharacterSkinsBackend(databaseAppState, connectedPlayers));
        networkServer.addMessageBackend(new EditCharacterInventoriesBackend(databaseAppState, connectedPlayers));
    }
    
    private void initializePasswordEncoder(){
        String[] keyPartLines = FileManager.getFileLines("./key_to_the_city.ini");
        long keyPart1 = Long.parseLong(keyPartLines[0]);
        long keyPart2 = Long.parseLong(keyPartLines[1]);
        passwordEncoder = new AES_Encoder(keyPart1, keyPart2, 0, 0);
    }
    
    public PlayerStatus getPlayerStatus(int playerID){
        if(connectedPlayers.getClientID(playerID) != -1){
            RunningGames runningGames = getAppState(GamesAppState.class).getRunningGames();
            if(runningGames.getGame(playerID) != null){
                return PlayerStatus.INGAME;
            }
            return PlayerStatus.ONLINE;
        }
        return PlayerStatus.OFFLINE;
    }

    public Encoder getPasswordEncoder(){
        return passwordEncoder;
    }

    public ConnectedPlayers getConnectedPlayers(){
        return connectedPlayers;
    }
}
