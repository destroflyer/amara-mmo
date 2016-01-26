/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ApplyReplaceSpellsWithNewSpellsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellWithNewSpellComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = entityWrapper.getComponent(ReplaceSpellWithNewSpellComponent.class);
            EntityWrapper newSpell = EntityTemplate.createFromTemplate(entityWorld, replaceSpellWithNewSpellComponent.getNewSpellTemplate());
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntities();
            int[] newSpellsEntities = Util.cloneArray(spellsEntities);
            newSpellsEntities[replaceSpellWithNewSpellComponent.getSpellIndex()] = newSpell.getId();
            entityWorld.setComponent(targetEntity, new SpellsComponent(newSpellsEntities));
        }
    }
}