/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class FinishTargetedMovementsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if (entityWorld.hasComponent(movementEntity, MovementTargetReachedComponent.class)) {
                entityWorld.removeComponent(entity, MovementComponent.class);
            }
        }
    }
}