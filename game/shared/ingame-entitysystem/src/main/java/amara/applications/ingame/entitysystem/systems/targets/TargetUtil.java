package amara.applications.ingame.entitysystem.systems.targets;

import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.libraries.entitysystem.*;

public class TargetUtil {

    public static boolean isValidTarget(EntityWorld entityWorld, int sourceEntity, int targetEntity, int targetRulesEntity) {
        boolean isValid;
        if (entityWorld.hasComponent(targetRulesEntity, RequireProjectileComponent.class)) {
            isValid = entityWorld.hasComponent(targetEntity, IsProjectileComponent.class);
        } else {
            isValid = entityWorld.hasComponent(targetEntity, IsTargetableComponent.class);
        }
        if (isValid) {
            if (entityWorld.hasComponent(targetRulesEntity, RequireCharacterComponent.class)) {
                isValid = entityWorld.hasComponent(targetEntity, IsCharacterComponent.class);
            }
            if(isValid){
                TeamComponent sourceTeamComponent = entityWorld.getComponent(sourceEntity, TeamComponent.class);
                TeamComponent targetTeamComponent = entityWorld.getComponent(targetEntity, TeamComponent.class);
                if((sourceTeamComponent != null) && (targetTeamComponent != null)){
                    isValid = false;
                    if(entityWorld.hasComponent(targetRulesEntity, AcceptAlliesComponent.class)){
                        isValid |= (sourceTeamComponent.getTeamEntity() == targetTeamComponent.getTeamEntity());
                    }
                    if(entityWorld.hasComponent(targetRulesEntity, AcceptEnemiesComponent.class)){
                        isValid |= (sourceTeamComponent.getTeamEntity() != targetTeamComponent.getTeamEntity());
                    }
                }
            }
            if (isValid) {
                RequireBuffsComponent requireBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireBuffsComponent.class);
                if ((requireBuffsComponent != null) && (!BuffUtil.hasBuffs(entityWorld, targetEntity, requireBuffsComponent.getBuffEntities()))) {
                    return false;
                }
                RequireEntityComponent requireEntityComponent = entityWorld.getComponent(targetRulesEntity, RequireEntityComponent.class);
                if ((requireEntityComponent != null) && (targetEntity != requireEntityComponent.getEntity())) {
                    return false;
                }
            }
        }
        return isValid;
    }
}
