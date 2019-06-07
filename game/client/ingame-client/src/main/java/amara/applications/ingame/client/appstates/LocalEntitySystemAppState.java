/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.network.backends.*;
import amara.applications.ingame.client.systems.audio.*;
import amara.applications.ingame.client.systems.cinematics.*;
import amara.applications.ingame.client.systems.filters.*;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.client.systems.visualisation.buffs.*;
import amara.applications.ingame.client.systems.visualisation.effects.crodwcontrol.*;
import amara.applications.ingame.client.systems.visualisation.effects.reactions.*;
import amara.applications.ingame.client.systems.visualisation.healthbars.*;
import amara.applications.ingame.entitysystem.synchronizing.ParallelNetworkSystems;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.applications.display.ingame.maps.MapHeightmap;
import amara.libraries.entitysystem.EntitySystem;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class LocalEntitySystemAppState extends EntitySystemDisplayAppState<IngameClientApplication> {

    public LocalEntitySystemAppState() {
        
    }
    private Node entitiesNode = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap(entitiesNode);

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(entitiesNode);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        ingameNetworkAppState.addMessageBackend(new GameStartedBackend(entityWorld, getAppState(LoadingScreenAppState.class)));
        ingameNetworkAppState.addMessageBackend(new GameCrashedBackend(mainApplication));
        ingameNetworkAppState.addMessageBackend(new GameOverBackend(mainApplication));
        for (EntitySystem entitySystem : ParallelNetworkSystems.generateSystems()) {
            addEntitySystem(entitySystem);
        }
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        addEntitySystem(playerAppState.getPlayerTeamSystem());
        MapAppState mapAppState = getAppState(MapAppState.class);
        MapHeightmap mapHeightmap = mapAppState.getMapHeightmap();
        PositionSystem positionSystem = new PositionSystem(entitySceneMap, mapHeightmap);
        addEntitySystem(positionSystem);
        addEntitySystem(new CollisionDebugSystem(getAppState(MapObstaclesAppState.class).getObstaclesNode()));
        addEntitySystem(new BushesSystem(entitySceneMap, playerAppState.getPlayerEntity()));
        addEntitySystem(new TeamModelSystem(playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new ModelSystem(entitySceneMap, mainApplication));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new MarkHoveredUnitsSystem(entitySceneMap, playerAppState.getPlayerTeamSystem(), getAppState(IngameMouseCursorAppState.class)));
        addEntitySystem(new KnockupVisualisationSystem(entitySceneMap, positionSystem));
        addEntitySystem(new BuffVisualisationSystem_Backporting(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_BaronNashor(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Bubble(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Burning(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Electrified(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Empowered(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Frost(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Ganfaul_Binded(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Healing(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Intervention(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Maria_Passive(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Meditating(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_RobinsGift(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Slap(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_SonicWaveMark(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Turbo(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Wither(entitySceneMap));
        addEntitySystem(new BuffVisualisationSystem_Golden_Eagle(entitySceneMap));
    }

    public void onInitialWorldLoaded() {
        addEntitySystem(new AudioSystem(getAppState(AudioAppState.class), getAppState(IngameCameraAppState.class)));
        MapAppState mapAppState = getAppState(MapAppState.class);
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        HealthBarStyleManager healthBarStyleManager = new HealthBarStyleManager();
        HUDAttachmentsSystem hudAttachmentsSystem = new HUDAttachmentsSystem(mainApplication.getGuiNode(), mainApplication.getCamera(), mapAppState.getMapHeightmap(), healthBarStyleManager, playerAppState.getOwnTeamVisionSystem());
        addEntitySystem(hudAttachmentsSystem);
        EntityHeightMap entityHeightMap = new EntityHeightMap(entitySceneMap);
        addEntitySystem(new MaximumHealthBarSystem(hudAttachmentsSystem, entityHeightMap, healthBarStyleManager, playerAppState.getPlayerTeamSystem()));
        addEntitySystem(new CurrentHealthBarSystem(hudAttachmentsSystem, entityHeightMap, healthBarStyleManager));
        addEntitySystem(new StunVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new SilenceVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new TitleSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new GoldChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new ExperienceChangeSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new LevelUpNotificationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new MinionAggroIndicatorSystem(hudAttachmentsSystem, entityHeightMap, playerAppState.getPlayerEntity()));
        addEntitySystem(new ReactionVisualisationSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new PopupSystem(hudAttachmentsSystem, entityHeightMap));
        addEntitySystem(new WaterSpeedSystem(mapAppState));
        addEntitySystem(new CinematicsSystem(getAppState(CinematicAppState.class)));
    }

    public int getEntity(Spatial spatial) {
        while (spatial != null) {
            Integer entity = spatial.getUserData("entity");
            if (entity != null) {
                return entity;
            }
            spatial = spatial.getParent();
        }
        return -1;
    }

    public Node getEntitiesNode() {
        return entitiesNode;
    }

    public EntitySceneMap getEntitySceneMap() {
        return entitySceneMap;
    }
}