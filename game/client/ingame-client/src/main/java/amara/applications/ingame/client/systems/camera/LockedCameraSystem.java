/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.camera;

import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.libraries.applications.display.ingame.appstates.IngameCameraAppState;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class LockedCameraSystem implements EntitySystem{

    public LockedCameraSystem(int playerEntity, IngameCameraAppState ingameCameraAppState){
        this.playerEntity = playerEntity;
        this.ingameCameraAppState = ingameCameraAppState;
    }
    private int playerEntity;
    private IngameCameraAppState ingameCameraAppState;
    private boolean isEnabled;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(isEnabled){
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
            if(playerCharacterComponent != null){
                PositionComponent positionComponent = entityWorld.getComponent(playerCharacterComponent.getEntity(), PositionComponent.class);
                if(positionComponent != null){
                    ingameCameraAppState.lookAt(positionComponent.getPosition());
                }
            }
        }
    }
    
    public void setEnabled(boolean isEnabled){
        if(isEnabled != this.isEnabled){
            this.isEnabled = isEnabled;
            ingameCameraAppState.setMovementEnabled(!isEnabled);
        }
    }

    public boolean isEnabled(){
        return isEnabled;
    }
}