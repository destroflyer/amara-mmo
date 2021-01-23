package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Dosaz_Ult extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Dosaz_Ult(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "dosaz_ult");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        Geometry geometry = GroundTextures.create(assetManager, "Textures/effects/dosaz_ult_area.png", -7.5f, 7.5f, 15, 15, RenderState.BlendMode.AlphaAdditive);
        node.attachChild(geometry);
        return node;
    }
}
