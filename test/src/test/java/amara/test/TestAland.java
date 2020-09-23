package amara.test;

import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackDamageComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatAttackSpeedComponent;
import amara.applications.ingame.entitysystem.components.attributes.BonusFlatWalkSpeedComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.network.messages.objects.commands.AutoAttackCommand;
import amara.applications.ingame.network.messages.objects.commands.StopCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastPositionalSkillshotSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSelfcastSpellCommand;
import amara.applications.ingame.network.messages.objects.commands.casting.CastSingleTargetSpellCommand;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestAland extends CommandingPlayerTest {

    public TestAland() {
        characterTemplate = "units/aland";
    }
    private static final String NAME_PASSIVE_STACKS_BUFF = "Aland's Target";
    private static final String NAME_PASSIVE_EFFECT_BUFF = "Previous Aland Target";
    private static final String NAME_Q_BUFF = "Stealthy";
    private static final String NAME_R_BUFF = "Caught";
    private static final String NAME_TRAP = "The Bear Trap";

    @Test
    public void testP_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1.25f);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(1, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        tickSeconds(1.25f);
        assertEquals(933.3334f, getHealth(targetDummy), EPSILON);
        assertEquals(2, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        tickSeconds(1.25f);
        assertEquals(900.0001f, getHealth(targetDummy), EPSILON);
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        queueCommand(new StopCommand());
        tickSeconds(2);
        assertEquals(833.3333f, getHealth(targetDummy), EPSILON);
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testP_Decay() {
        int targetDummy = createTargetDummy(new Vector2f(15, 10));
        onLogicStart();

        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        queueCommand(new AutoAttackCommand(targetDummy));
        tickSeconds(1.25f);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(1, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        queueCommand(new StopCommand());
        tickSeconds(4);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_CancelOnCast() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_BUFF));
        assertTrue(isStealthed(character));
        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertFalse(hasBuff(character, NAME_Q_BUFF));
        assertFalse(isStealthed(character));

        onLogicEnd(false, false);
    }

    @Test
    public void testQ_Decay() {
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_Q));
        tickSeconds(1);
        assertTrue(hasBuff(character, NAME_Q_BUFF));
        assertTrue(isStealthed(character));
        tickSeconds(4);
        assertFalse(hasBuff(character, NAME_Q_BUFF));
        assertFalse(isStealthed(character));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Hit() {
        int targetDummy1 = createTargetDummy(new Vector2f(13, 10));
        int targetDummy2 = createTargetDummy(new Vector2f(10, 13));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertEquals(953.3333f, getHealth(targetDummy1), EPSILON);
        assertEquals(953.3333f, getHealth(targetDummy2), EPSILON);
        assertEquals(1, getBuffStacks(targetDummy1, NAME_PASSIVE_STACKS_BUFF));
        assertEquals(1, getBuffStacks(targetDummy2, NAME_PASSIVE_STACKS_BUFF));
        assertEquals(17, getX(targetDummy1), EPSILON);
        assertEquals(10, getY(targetDummy1), EPSILON);
        assertEquals(10, getX(targetDummy2), EPSILON);
        assertEquals(17, getY(targetDummy2), EPSILON);
        tickSeconds(4);
        assertEquals(0, getBuffStacks(targetDummy1, NAME_PASSIVE_STACKS_BUFF));
        assertEquals(0, getBuffStacks(targetDummy2, NAME_PASSIVE_STACKS_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testW_Miss() {
        int targetDummy = createTargetDummy(new Vector2f(20, 20));
        onLogicStart();

        queueCommand(new CastSelfcastSpellCommand(SPELL_INDEX_W));
        tickSeconds(1);
        assertTrue(isFullHealth(targetDummy));
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertEquals(20, getX(targetDummy), EPSILON);
        assertEquals(20, getY(targetDummy), EPSILON);

        onLogicEnd(false, false);
    }

    @Test
    public void testE() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        onLogicStart();

        queueCommand(new CastSingleTargetSpellCommand(SPELL_INDEX_E, targetDummy));
        tickSeconds(1);
        assertEquals(933.3333f, getHealth(targetDummy), EPSILON);
        assertEquals(1, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        tickSeconds(4);
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Hit() {
        int targetDummy = createTargetDummy(new Vector2f(20, 10));
        int targetDummyBaseAttributes = entityWorld.getComponent(targetDummy, BaseAttributesComponent.class).getBonusAttributesEntity();
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackDamageComponent(10));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatAttackSpeedComponent(1));
        entityWorld.setComponent(targetDummyBaseAttributes, new BonusFlatWalkSpeedComponent(10));
        int targetDummyAutoAttack = entityWorld.createEntity();
        EntityTemplate.loadTemplate(entityWorld, targetDummyAutoAttack, "spells/melee_autoattack");
        entityWorld.setComponent(targetDummy, new AutoAttackComponent(targetDummyAutoAttack));
        entityWorld.setComponent(targetDummy, new IsAutoAttackEnabledComponent());
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(15, 10)));
        tickSeconds(1);
        int trap = findEntity(NAME_TRAP);
        assertEquals(15, getX(trap), EPSILON);
        assertEquals(10, getY(trap), EPSILON);
        assertEquals(0, getBuffStacks(targetDummy, NAME_R_BUFF));
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        entityWorld.setComponent(targetDummy, new AggroTargetComponent(character));
        tickSeconds(0.4f);
        assertEquals(966.6667f, getHealth(targetDummy), EPSILON);
        assertEquals(1, getBuffStacks(targetDummy, NAME_R_BUFF));
        assertEquals(1, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        tickSeconds(0.2f);
        assertEquals(933.3334f, getHealth(targetDummy), EPSILON);
        assertEquals(2, getBuffStacks(targetDummy, NAME_R_BUFF));
        assertEquals(2, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        tickSeconds(0.2f);
        assertEquals(900.0001f, getHealth(targetDummy), EPSILON);
        assertEquals(3, getBuffStacks(targetDummy, NAME_R_BUFF));
        assertEquals(0, getBuffStacks(targetDummy, NAME_PASSIVE_STACKS_BUFF));
        assertTrue(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));
        entityWorld.removeComponent(targetDummy, SightRangeComponent.class);
        tickSeconds(2);
        assertNull(findEntity(NAME_TRAP));
        assertEquals(833.3333f, getHealth(targetDummy), EPSILON);
        assertEquals(0, getBuffStacks(targetDummy, NAME_R_BUFF));
        assertFalse(hasBuff(targetDummy, NAME_PASSIVE_EFFECT_BUFF));

        onLogicEnd(false, false);
    }

    @Test
    public void testR_Decay() {
        onLogicStart();

        queueCommand(new CastPositionalSkillshotSpellCommand(SPELL_INDEX_R, new Vector2f(15, 10)));
        tickSeconds(1);
        int trap = findEntity(NAME_TRAP);
        assertEquals(15, getX(trap), EPSILON);
        assertEquals(10, getY(trap), EPSILON);
        tickSeconds(180);
        assertNull(findEntity(NAME_TRAP));

        onLogicEnd(false, false);
    }
}
