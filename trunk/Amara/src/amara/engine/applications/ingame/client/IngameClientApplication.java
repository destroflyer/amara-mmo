package amara.engine.applications.ingame.client;

import java.io.File;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.appstates.*;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;
import amara.game.maps.MapFileHandler;

/**
 * @author Carl
 */
public class IngameClientApplication extends DisplayApplication{

    public IngameClientApplication(HostInformation hostInformation, int authentificationKey){
        this.hostInformation = hostInformation;
        this.authentificationKey = authentificationKey;
        settings = new AppSettings(true);
        settings.setTitle("Amara");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setFrameRate(60);
        setPauseOnLostFocus(false);
    }
    private HostInformation hostInformation;
    private int authentificationKey;

    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        try{
            stateManager.attach(new NetworkClientAppState(hostInformation.getHost(), hostInformation.getPort()));
            stateManager.attach(new PlayerAuthentificationAppState(authentificationKey));
            stateManager.attach(new EventManagerAppState());
            stateManager.attach(new NiftyAppState());
            stateManager.attach(new NiftyAppState_IngameClient());
            stateManager.attach(new AudioAppState());
            stateManager.attach(new LightAppState());
            stateManager.attach(new PostFilterAppState());
            stateManager.attach(new SendPlayerCommandsAppState());
            stateManager.attach(new MapAppState(MapFileHandler.loadFile(new File("./assets/Maps/testmap/map.xml"))));
            stateManager.attach(new MapObstaclesAppState());
            stateManager.attach(new LocalEntitySystemAppState());
            stateManager.attach(new IngameCameraAppState());
            stateManager.attach(new ClientInitializedAppState());
            //Debug Camera
            cam.setLocation(new Vector3f(22, 34, -10));
            cam.lookAtDirection(new Vector3f(0, -1.3f, 1), Vector3f.UNIT_Y);
        }catch(ServerConnectionException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }catch(ServerConnectionTimeoutException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
