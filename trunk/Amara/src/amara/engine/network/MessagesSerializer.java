/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import com.jme3.network.serializing.Serializer;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.AuthentificationInformation;
import amara.engine.network.messages.*;
import amara.engine.network.messages.entitysystem.*;
import amara.engine.network.messages.protocol.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.objectives.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.spells.specials.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.units.intersections.*;
import amara.game.entitysystem.components.units.movement.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.synchronizing.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.games.PlayerData;
import amara.launcher.client.protocol.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            Message_EditUserMeta.class,
            Message_GetPlayerProfileData.class,
            Message_GetPlayerStatus.class,
            Message_Login.class,
                AuthentificationInformation.class,
            Message_LoginResult.class,
            Message_Logout.class,
            Message_PlayerProfileData.class,
                PlayerProfileData.class,
            Message_PlayerProfileDataNotExistant.class,
            Message_PlayerStatus.class,
            
            Message_StartGame.class,
                PlayerData.class,
            Message_GameCreated.class,
            
            Message_Command.class,
                AutoAttackCommand.class,
                Command.class,
                MoveCommand.class,
                StopCommand.class,
                CastLinearSkillshotSpellCommand.class,
                CastPositionalSkillshotSpellCommand.class,
                CastSelfcastSpellCommand.class,
                CastSingleTargetSpellCommand.class,
            Message_ClientConnection.class,
            Message_ClientDisconnection.class,
            Message_ClientInitialized.class,
            Message_EntityChanges.class,
                EntityChange.class,
                    NewComponentChange.class,
                        //attributes
                        AbilityPowerComponent.class,
                        AttackDamageComponent.class,
                        AttackSpeedComponent.class,
                        BaseAbilityPowerComponent.class,
                        BaseAttackDamageComponent.class,
                        BaseAttackSpeedComponent.class,
                        BaseMaximumHealthComponent.class,
                        BonusFlatAbilityPowerComponent.class,
                        BonusFlatAttackDamageComponent.class,
                        BonusFlatMaximumHealthComponent.class,
                        BonusPercentageAttackSpeedComponent.class,
                        HealthComponent.class,
                        MaximumHealthComponent.class,
                        RequestUpdateAttributesComponent.class,
                        //buffs
                        ContinuousEffectComponent.class,
                        RemoveEffectTriggersComponent.class,
                        RepeatingEffectComponent.class,
                        //buffs/status
                        ActiveBuffComponent.class,
                        BuffVisualisationComponent.class,
                        RemainingBuffDurationComponent.class,
                        RemoveFromTargetComponent.class,
                        TimeSinceLastRepeatingEffectComponent.class,
                        //effects
                        AffectedTargetsComponent.class,
                        ApplyEffectImpactComponent.class,
                        //effects/buffs
                        AddBuffComponent.class,
                        RemoveBuffComponent.class,
                        //effects/crowdcontrol
                        AddBindingComponent.class,
                        AddBindingImmuneComponent.class,
                        AddSilenceComponent.class,
                        AddSilenceImmuneComponent.class,
                        AddStunComponent.class,
                        AddStunImmuneComponent.class,
                        RemoveBindingComponent.class,
                        RemoveSilenceComponent.class,
                        RemoveStunComponent.class,
                        //effects/damage
                        AddTargetabilityComponent.class,
                        AddVulnerabilityComponent.class,
                        FlatMagicDamageComponent.class,
                        FlatPhysicalDamageComponent.class,
                        MagicDamageComponent.class,
                        PhysicalDamageComponent.class,
                        RemoveTargetabilityComponent.class,
                        RemoveVulnerabilityComponent.class,
                        ScalingAbilityPowerMagicDamageComponent.class,
                        ScalingAttackDamagePhysicalDamageComponent.class,
                        EffectSourceComponent.class,
                        PrepareEffectComponent.class,
                        //effects/heal
                        FlatHealComponent.class,
                        HealComponent.class,
                        //effects/movement,
                        MoveToEntityPositionComponent.class,
                        //effects/spells
                        ReplaceSpellWithExistingSpellComponent.class,
                        ReplaceSpellWithNewSpellComponent.class,
                        //general
                        DescriptionComponent.class,
                        NameComponent.class,
                        //input
                        CastLinearSkillshotSpellComponent.class,
                        CastPositionalSkillshotSpellComponent.class,
                        CastSelfcastSpellComponent.class,
                        CastSingleTargetSpellComponent.class,
                        //items
                        InventoryComponent.class,
                        ItemVisualisationComponent.class,
                        //objectives
                        FinishedObjectiveComponent.class,
                        MissingEntitiesComponent.class,
                        OpenObjectiveComponent.class,
                        //physics
                        AntiGhostComponent.class,
                        CollisionGroupComponent.class,
                        DirectionComponent.class,
                        HitboxComponent.class,
                            Circle.class,
                            Rectangle.class,
                            RegularCyclic.class,
                            Shape.class,
                            SimpleConvex.class,
                            Transform.class,
                            Vector2D.class,
                        MapIntersectionPushComponent.class,
                        MovementComponent.class,
                        MovementSpeedComponent.class,
                        MovementTargetComponent.class,
                        PositionComponent.class,
                        ScaleComponent.class,
                        UnitIntersectionPushComponent.class,
                        //players
                        ClientComponent.class,
                        IsAliveComponent.class,
                        SelectedUnitComponent.class,
                        //spawns
                        CastSourceComponent.class,
                        RelativeSpawnPositionComponent.class,
                        SpawnMovementSpeedComponent.class,
                        SpawnTemplateComponent.class,
                        //spells
                        ApplyCastedSpellComponent.class,
                        CastAnimationComponent.class,
                        CastTypeComponent.class,
                        CooldownComponent.class,
                        InstantSpawnsComponent.class,
                        InstantTargetBuffComponent.class,
                        InstantEffectTriggersComponent.class,
                        RemainingCooldownComponent.class,
                        SpellVisualisationComponent.class,
                        //spells/specials
                        TeleportCasterToTargetPositionComponent.class,
                        //units
                        AutoAggroComponent.class,
                        AutoAttackComponent.class,
                        AutoAttackTargetComponent.class,
                        EffectTriggersComponent.class,
                        IntersectionRulesComponent.class,
                        IsTargetableComponent.class,
                        IsVulnerableComponent.class,
                        LifetimeComponent.class,
                        SpellsComponent.class,
                        TargetsInAggroRangeComponent.class,
                        TeamComponent.class,
                        //units/animations
                        AutoAttackAnimationComponent.class,
                        IdleAnimationComponent.class,
                        WalkAnimationComponent.class,
                        //units/crowdcontrol
                        IsBindedComponent.class,
                        IsBindedImmuneComponent.class,
                        IsSilencedComponent.class,
                        IsSilencedImmuneComponent.class,
                        IsStunnedComponent.class,
                        IsStunnedImmuneComponent.class,
                        //units/effecttriggers
                        TriggeredEffectComponent.class,
                        //units/effecttriggers/targets
                        CustomTargetComponent.class,
                        TargetTargetComponent.class,
                        //units/effecttriggers/triggers
                        CollisionTriggerComponent.class,
                        TargetReachedTriggerComponent.class,
                        //units/intersections
                        AcceptAlliesComponent.class,
                        AcceptEnemiesComponent.class,
                        //units/movement
                        TargetedMovementComponent.class,
                        //visuals
                        ModelComponent.class,
                        AnimationComponent.class,
                        StopPlayingAnimationComponent.class,
                        TitleComponent.class,
                        //visuals/animations
                        LoopDurationComponent.class,
                        PassedLoopTimeComponent.class,
                        RemainingLoopsComponent.class,
                    RemovedComponentChange.class,
                    RemovedEntityChange.class,
            Message_GameInfo.class,
            Message_GameStarted.class,
            Message_GameOver.class,
            Message_PlayerAuthentification.class
        );
    }
}
