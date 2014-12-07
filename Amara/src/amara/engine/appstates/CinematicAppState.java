/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.control.CameraControl;
import amara.engine.cinematics.*;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.ingame.client.gui.*;

/**
 *
 * @author Carl
 */
public class CinematicAppState extends BaseDisplayAppState{

    public CinematicAppState(){
        
    }
    private Cinematic currentCinematic;
    private CameraNode cameraNode;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        cameraNode = new CameraNode("cinematics_camera_node", mainApplication.getCamera());
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        cameraNode.setEnabled(false);
        mainApplication.getRootNode().attachChild(cameraNode);
    }
    
    public void playCinematic(Cinematic cinematic){
        stopCinematic();
        cinematic.reset();
        currentCinematic = cinematic;
        setCinematicModeEnabled(true);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(currentCinematic != null){
            currentCinematic.update(lastTimePerFrame, mainApplication);
            if(currentCinematic.isFinished()){
                onCinematicStop();
            }
        }
    }
    
    public void stopCinematic(){
        if(currentCinematic != null){
            currentCinematic.stop(mainApplication);
            onCinematicStop();
        }
    }
    
    private void onCinematicStop(){
        currentCinematic = null;
        setCinematicModeEnabled(false);
    }
    
    private void setCinematicModeEnabled(boolean isEnabled){
        getAppState(IngameCameraAppState.class).setEnabled(!isEnabled);
        getAppState(SendPlayerCommandsAppState.class).setEnabled(!isEnabled);
        if(isEnabled){
            getAppState(NiftyAppState.class).goToScreen(ScreenController_HUD.class, "cinematic");
        }
        else{
            getAppState(MapAppState.class).initializeCamera();
            getAppState(NiftyAppState.class).goToScreen(ScreenController_Cinematic.class, "start");
        }
    }

    public Cinematic getCurrentCinematic(){
        return currentCinematic;
    }

    public CameraNode getCameraNode(){
        return cameraNode;
    }
}
