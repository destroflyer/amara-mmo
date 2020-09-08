/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import amara.core.settings.Settings;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.units.IsHoveredComponent;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.ingame.appstates.IngameMouseCursorAppState;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.applications.display.models.RegisteredModel;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class MarkHoveredUnitsSystem implements EntitySystem {

    public MarkHoveredUnitsSystem(EntitySceneMap entitySceneMap, PlayerTeamSystem playerTeamSystem, IngameMouseCursorAppState ingameMouseCursorAppState) {
        this.entitySceneMap = entitySceneMap;
        this.playerTeamSystem = playerTeamSystem;
        this.ingameMouseCursorAppState = ingameMouseCursorAppState;
    }
    private final static String NODE_NAME_MARKER = "hoveredMarker";
    private EntitySceneMap entitySceneMap;
    private PlayerTeamSystem playerTeamSystem;
    private IngameMouseCursorAppState ingameMouseCursorAppState;
    private ColorRGBA colorAllies = new ColorRGBA(0.1f, 1, 0.1f, 1);
    private ColorRGBA colorEnemies = new ColorRGBA(1, 0.1f, 0.1f, 1);

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHoveredComponent.class);
        // Check removed first to keep a potential hover mouse cursor at the end
        for (int entity : observer.getRemoved().getEntitiesWithAny(IsHoveredComponent.class)) {
            ingameMouseCursorAppState.setCursor_Default();
            Node node = entitySceneMap.requestNode(entity);
            ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
            Spatial marker = modelObject.getChild(NODE_NAME_MARKER);
            modelObject.unregisterModel(marker);
        }
        for (int entity : observer.getNew().getEntitiesWithAny(IsHoveredComponent.class)) {
            boolean isAllied = playerTeamSystem.isAllied(entityWorld, entity);
            if (isAllied) {
                ingameMouseCursorAppState.setCursor_Default();
            } else {
                ingameMouseCursorAppState.setCursor_Enemy();
            }
            float hoverOutlineThickness = Settings.getFloat("hover_outline_thickness");
            if (hoverOutlineThickness > 0) {
                Node node = entitySceneMap.requestNode(entity);
                ModelObject modelObject = (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
                RegisteredModel clonedModel = modelObject.loadAndRegisterModel();
                clonedModel.getNode().setName(NODE_NAME_MARKER);
                for (Geometry geometry : JMonkeyUtil.getAllGeometryChilds(clonedModel.getNode())) {
                    Material material = new Material(MaterialFactory.getAssetManager(), "Shaders/cartoonedge/matdefs/cartoonedge.j3md");
                    material.setColor("EdgesColor", (isAllied ? colorAllies : colorEnemies));
                    material.setFloat("EdgeSize", hoverOutlineThickness / geometry.getWorldScale().getY());
                    geometry.setMaterial(material);
                }
                JMonkeyUtil.setHardwareSkinningPreferred(clonedModel.getNode(), false);
            }
        }
    }
}