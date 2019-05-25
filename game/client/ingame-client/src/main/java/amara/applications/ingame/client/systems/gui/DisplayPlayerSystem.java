/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayPlayerSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayPlayerSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, NameComponent.class);
        check(observer.getNew().getComponent(playerEntity, NameComponent.class));
        check(observer.getChanged().getComponent(playerEntity, NameComponent.class));
    }
    
    private void check(NameComponent nameComponent){
        if(nameComponent != null){
            screenController.setPlayerName(nameComponent.getName());
        }
    }
}
