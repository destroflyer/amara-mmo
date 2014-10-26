/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Carl
 */
public class RemoveCancelledMovementsEffectTriggersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, MovementComponent.class, TriggerSourceComponent.class);
        for(int entity : observer.getChanged().getEntitiesWithAll(MovementComponent.class)){
            removeMovementEffectTriggers(entityWorld, observer, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(MovementComponent.class)){
            removeMovementEffectTriggers(entityWorld, observer, entity);
        }
        observer.reset();
    }
    
    private void removeMovementEffectTriggers(EntityWorld entityWorld, ComponentMapObserver observer, int entity){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TargetReachedTriggerComponent.class)){
            if(entityWorld.hasComponent(effectTriggerEntity, TriggerTemporaryComponent.class)){
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if((sourceEntity == entity) && (!observer.getNew().hasEntity(effectTriggerEntity))){
                    entityWorld.removeEntity(effectTriggerEntity);
                }
            }
        }
    }
}