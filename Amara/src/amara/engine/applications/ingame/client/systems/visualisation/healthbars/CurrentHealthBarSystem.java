/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.healthbars;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.engine.materials.PaintableImage;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class CurrentHealthBarSystem extends TopHUDAttachmentSystem{

    public CurrentHealthBarSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, HealthBarStyleManager healthBarStyleManager){
        super(hudAttachmentsSystem, entityHeightMap, HealthComponent.class);
        this.healthBarStyleManager = healthBarStyleManager;
        hudOffset = new Vector3f(0, 0, 1);
    }
    private HealthBarStyleManager healthBarStyleManager;
    private Vector3f preparedHudOffset;
        
    @Override
    protected Spatial createVisualAttachment(EntityWorld entityWorld, int entity){
        return healthBarStyleManager.createGeometry(entityWorld, entity);
    }

    @Override
    protected void updateVisualAttachment(EntityWorld entityWorld, int entity, Spatial visualAttachment){
        HealthBarStyle style = healthBarStyleManager.getStyle(entityWorld, entity);
        PaintableImage paintableImage = healthBarStyleManager.getImage_CurrentHealth(entity, style);
        MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(entity, MaximumHealthComponent.class);
        if(maximumHealthComponent != null){
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float maximumHealth = maximumHealthComponent.getValue();
            float healthPortion = (health / maximumHealth);
            style.drawCurrentHealth(paintableImage, healthPortion);
        }
        Geometry geometry = (Geometry) visualAttachment;
        Texture texture = geometry.getMaterial().getTextureParam("ColorMap").getTextureValue();
        texture.setImage(paintableImage.getImage());
        preparedHudOffset = hudOffset.clone().setY(-1 * style.getBarHeight());
    }

    @Override
    protected Vector3f getPreparedHUDOffset(){
        return preparedHudOffset;
    }
}
