package amara.applications.ingame.client.systems.visualisation.effects.crodwcontrol;

import amara.applications.ingame.client.systems.visualisation.EntityHeightMap;
import amara.applications.ingame.client.systems.visualisation.HUDAttachmentsSystem;
import amara.applications.ingame.client.systems.visualisation.TopHUDAttachmentSystem;
import amara.applications.ingame.client.systems.visualisation.meshes.SpeechBubbleMesh;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnedComponent;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class StunVisualisationSystem extends TopHUDAttachmentSystem {

    public StunVisualisationSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, AssetManager assetManager) {
        super(hudAttachmentsSystem, entityHeightMap, IsStunnedComponent.class, assetManager);
        hudOffset = new Vector3f(0, 22, 0);
    }

    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity) {
        Geometry geometry = new Geometry("", new SpeechBubbleMesh());
        Material material = MaterialFactory.generateUnshadedMaterial(assetManager, "Textures/effects/stun.png");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        return geometry;
    }
}
