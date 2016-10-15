/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.vision;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.vision.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddStealthSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStealthComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new IsStealthedComponent());
        }
    }
}
