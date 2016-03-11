/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spawns;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ApplySpawnsSystems implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SpawnComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            PositionComponent targetPositionComponent = null;
            DirectionComponent targetDirectionComponent = null;
            if(targetEntity != -1){
                targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                targetDirectionComponent = entityWorld.getComponent(targetEntity, DirectionComponent.class);
            }
            int casterEntity = entityWrapper.getComponent(EffectCastSourceComponent.class).getSourceEntity();
            TeamComponent teamComponent = entityWorld.getComponent(casterEntity, TeamComponent.class);
            for(int spawnInformationEntity : entityWrapper.getComponent(SpawnComponent.class).getSpawnInformationEntites()){
                EntityWrapper spawnedObject = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(spawnInformationEntity);
                EntityUtil.transferComponents(entityWrapper, spawnedObject, new Class[]{
                    EffectCastSourceComponent.class,
                    EffectCastSourceSpellComponent.class
                });
                if(teamComponent != null){
                    spawnedObject.setComponent(new TeamComponent(teamComponent.getTeamEntity()));
                }
                boolean moveToTarget = spawnInformation.hasComponent(SpawnMoveToTargetComponent.class);
                Vector2f position;
                Vector2f direction = null;
                if(targetDirectionComponent != null){
                    direction = targetDirectionComponent.getVector().clone();
                }
                PositionComponent casterPositionComponent = entityWorld.getComponent(casterEntity, PositionComponent.class);
                if((targetPositionComponent != null) && (!moveToTarget)){
                    position = targetPositionComponent.getPosition().clone();
                    if((direction == null) && (casterPositionComponent != null)){
                        direction = position.subtract(casterPositionComponent.getPosition()).normalizeLocal();
                    }
                }
                else if(casterPositionComponent != null){
                    position = casterPositionComponent.getPosition().clone();
                }
                else{
                    //Caster does no longer exist or an invalid combination was specified
                    continue;
                }
                SpawnRelativePositionComponent spawnRelativePositionComponent = spawnInformation.getComponent(SpawnRelativePositionComponent.class);
                if(spawnRelativePositionComponent != null){
                    DirectionComponent casterDirectionComponent = entityWorld.getComponent(casterEntity, DirectionComponent.class);
                    if(casterDirectionComponent !=  null){
                        float casterDirection_Radian = casterDirectionComponent.getRadian();
                        Vector2f relativePosition = spawnRelativePositionComponent.getPosition().clone();
                        relativePosition.rotateAroundOrigin(casterDirection_Radian, false);
                        position.addLocal(relativePosition);
                    }
                }
                spawnedObject.setComponent(new PositionComponent(position));
                if(direction != null){
                    SpawnRelativeDirectionComponent spawnRelativeDirectionComponent = spawnInformation.getComponent(SpawnRelativeDirectionComponent.class);
                    if(spawnRelativeDirectionComponent != null){
                        direction.rotateAroundOrigin(spawnRelativeDirectionComponent.getAngle_Radian(), false);
                    }
                    spawnedObject.setComponent(new DirectionComponent(direction));
                }
                SpawnMovementSpeedComponent spawnMovementSpeedComponent = spawnInformation.getComponent(SpawnMovementSpeedComponent.class);
                if(spawnMovementSpeedComponent != null){
                    EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                    if(moveToTarget){
                        movement.setComponent(new MovementTargetComponent(targetEntity));
                    }
                    else if(targetDirectionComponent != null){
                        Vector2f movementDirection = targetDirectionComponent.getVector().clone();
                        SpawnMovementRelativeDirectionComponent spawnMovementRelativeDirectionComponent = spawnInformation.getComponent(SpawnMovementRelativeDirectionComponent.class);
                        if(spawnMovementRelativeDirectionComponent != null){
                            movementDirection.rotateAroundOrigin(spawnMovementRelativeDirectionComponent.getAngle_Radian(), false);
                        }
                        movement.setComponent(new MovementDirectionComponent(movementDirection));
                    }
                    movement.setComponent(new MovementSpeedComponent(spawnMovementSpeedComponent.getSpeed()));
                    SpawnMovementAnimationComponent spawnMovementAnimationComponent = spawnInformation.getComponent(SpawnMovementAnimationComponent.class);
                    if(spawnMovementAnimationComponent != null){
                        movement.setComponent(new MovementAnimationComponent(spawnMovementAnimationComponent.getAnimationEntity()));
                    }
                    spawnedObject.setComponent(new MovementComponent(movement.getId()));
                }
                if(spawnInformation.hasComponent(SpawnAttackMoveComponent.class)){
                    spawnedObject.setComponent(new AttackMoveComponent(targetEntity));
                }
                EntityTemplate.loadTemplates(entityWorld, spawnedObject.getId(), spawnInformation.getComponent(SpawnTemplateComponent.class).getTemplateNames());
            }
        }
    }
}
