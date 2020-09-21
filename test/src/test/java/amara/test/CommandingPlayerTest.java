package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.status.ActiveBuffComponent;
import amara.applications.ingame.entitysystem.components.buffs.status.StacksComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementSpeedComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.ClientComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindedComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupedComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.applications.ingame.entitysystem.systems.units.shields.ShieldUtil;
import amara.applications.ingame.network.messages.objects.commands.casting.SpellIndex;
import com.jme3.math.Vector2f;
import org.junit.Before;

import java.util.LinkedList;

import static org.junit.Assert.fail;

public class CommandingPlayerTest extends GameLogicTest {

    protected SpellIndex SPELL_INDEX_Q = new SpellIndex(SpellIndex.SpellSet.SPELLS, 0);
    protected SpellIndex SPELL_INDEX_W = new SpellIndex(SpellIndex.SpellSet.SPELLS, 1);
    protected SpellIndex SPELL_INDEX_E = new SpellIndex(SpellIndex.SpellSet.SPELLS, 2);
    protected SpellIndex SPELL_INDEX_R = new SpellIndex(SpellIndex.SpellSet.SPELLS, 3);

    protected String characterTemplate = "units/annie";
    protected int player;
    protected int character;

    @Before
    public void initializePlayer() {
        player = entityWorld.createEntity();
        character = createEntity(characterTemplate);
        entityWorld.setComponent(player, new ClientComponent(0));
        entityWorld.setComponent(player, new PlayerCharacterComponent(character));
        entityWorld.setComponent(character, new PositionComponent(new Vector2f(10, 10)));
        entityWorld.setComponent(character, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(character, new SightRangeComponent(30));
        entityWorld.setComponent(character, new TeamComponent(1));
        LearnableSpellsComponent learnableSpellsComponent = entityWorld.getComponent(character, LearnableSpellsComponent.class);
        if (learnableSpellsComponent != null) {
            entityWorld.setComponent(character, new SpellsComponent(learnableSpellsComponent.getSpellsEntities()));
        }
    }

    protected int createTargetDummy(Vector2f position) {
        int targetDummy = createEntity("units/target_dummy");
        entityWorld.setComponent(targetDummy, new PositionComponent(position));
        entityWorld.setComponent(targetDummy, new DirectionComponent(new Vector2f(0, 1)));
        entityWorld.setComponent(targetDummy, new TeamComponent(2));
        return targetDummy;
    }

    protected Integer findEntity(String name) {
        for (int entity : entityWorld.getEntitiesWithAny(NameComponent.class)) {
            String currentName = entityWorld.getComponent(entity, NameComponent.class).getName();
            if (name.equals(currentName)) {
                return entity;
            }
        }
        return null;
    }

    protected LinkedList<Integer> findEntities(String name) {
        LinkedList<Integer> entities = new LinkedList<>();
        for (int entity : entityWorld.getEntitiesWithAny(NameComponent.class)) {
            String currentName = entityWorld.getComponent(entity, NameComponent.class).getName();
            if (name.equals(currentName)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    protected float getX(int entity) {
        return getPosition(entity).getX();
    }

    protected float getY(int entity) {
        return getPosition(entity).getY();
    }

    private Vector2f getPosition(int entity) {
        return entityWorld.getComponent(entity, PositionComponent.class).getPosition();
    }

    protected float getMovementSpeed(int entity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if (movementComponent != null) {
            MovementSpeedComponent movementSpeedComponent = entityWorld.getComponent(movementComponent.getMovementEntity(), MovementSpeedComponent.class);
            if (movementSpeedComponent != null) {
                return movementSpeedComponent.getSpeed();
            }
        }
        return 0;
    }

    protected boolean isFullHealth(int entity) {
        return (getHealth(entity) == entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue());
    }

    protected float getHealth(int entity) {
        return entityWorld.getComponent(entity, HealthComponent.class).getValue();
    }

    protected float getTotalShieldAmount(int entity) {
        return ShieldUtil.getTotalShieldAmount(entityWorld, entity);
    }

    protected float getAttackSpeed(int entity) {
        return entityWorld.getComponent(entity, AttackSpeedComponent.class).getValue();
    }

    protected float getArmor(int entity) {
        return entityWorld.getComponent(entity, ArmorComponent.class).getValue();
    }

    protected float getMagicResistance(int entity) {
        return entityWorld.getComponent(entity, MagicResistanceComponent.class).getValue();
    }

    protected boolean isAlive(int entity) {
        return entityWorld.hasComponent(entity, IsAliveComponent.class);
    }

    protected boolean isBinded(int entity) {
        return entityWorld.hasComponent(entity, IsBindedComponent.class);
    }

    protected boolean isKnockuped(int entity) {
        return entityWorld.hasComponent(entity, IsKnockupedComponent.class);
    }

    protected boolean isStunned(int entity) {
        return entityWorld.hasComponent(entity, IsStunnedComponent.class);
    }

    protected boolean hasBuff(int entity, String buffName) {
        return (getBuffStatusEntity(entity, buffName) != null);
    }

    protected int getBuffStacks(int entity, String buffName) {
        Integer buffStatusEntity = getBuffStatusEntity(entity, buffName);
        if (buffStatusEntity != null) {
            StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
            if (stacksComponent != null) {
                return stacksComponent.getStacks();
            }
        }
        return 0;
    }

    private Integer getBuffStatusEntity(int entity, String buffName) {
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                NameComponent nameComponent = entityWorld.getComponent(buffEntity, NameComponent.class);
                if ((nameComponent != null) && buffName.equals(nameComponent.getName())) {
                    return buffStatusEntity;
                }
            }
        }
        return null;
    }

    protected void resetCooldown(int entity, SpellIndex spellIndex) {
        int spellEntity = getSpellEntity(entity, spellIndex);
        entityWorld.removeComponent(spellEntity,RemainingCooldownComponent.class);
    }

    protected float getRemainingCooldown(int entity, SpellIndex spellIndex) {
        int spellEntity = getSpellEntity(entity, spellIndex);
        RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(spellEntity, RemainingCooldownComponent.class);
        return ((remainingCooldownComponent != null) ? remainingCooldownComponent.getDuration() : 0);
    }

    private int getSpellEntity(int entity, SpellIndex spellIndex) {
        int spellEntity = ExecutePlayerCommandsSystem.getSpellEntity(entityWorld, entity, spellIndex);
        if (spellEntity == -1) {
            fail();
        }
        return spellEntity;
    }
}
