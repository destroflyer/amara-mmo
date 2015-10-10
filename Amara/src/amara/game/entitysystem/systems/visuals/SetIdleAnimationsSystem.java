/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.visuals;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class SetIdleAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AnimationComponent.class);
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(AnimationComponent.class)){
            IdleAnimationComponent idleAnimationComponent = entityWorld.getComponent(entity, IdleAnimationComponent.class);
            if(idleAnimationComponent != null){
                entityWorld.setComponent(entity, new AnimationComponent(idleAnimationComponent.getAnimationEntity()));
            }
        }
    }
}
