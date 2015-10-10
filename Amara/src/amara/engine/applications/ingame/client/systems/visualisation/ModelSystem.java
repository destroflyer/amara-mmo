/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class ModelSystem implements EntitySystem{
    
    public ModelSystem(EntitySceneMap entitySceneMap, DisplayApplication mainApplication){
        this.entitySceneMap = entitySceneMap;
        this.mainApplication = mainApplication;
    }
    public final static String NODE_NAME_MODEL = "model";
    private EntitySceneMap entitySceneMap;
    private DisplayApplication mainApplication;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, ModelComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(ModelComponent.class)){
            updateModel(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(ModelComponent.class)){
            updateModel(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(ModelComponent.class)){
            entitySceneMap.removeNode(entity);
        }
    }
    
    private void updateModel(EntityWorld entityWorld, Integer entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(NODE_NAME_MODEL);
        ModelComponent modelComponent = entityWorld.getComponent(entity, ModelComponent.class);
        ModelObject modelObject = new ModelObject(mainApplication, "/" + modelComponent.getModelSkinPath());
        modelObject.setName(NODE_NAME_MODEL);
        modelObject.setShadowMode(RenderQueue.ShadowMode.Cast);
        node.attachChild(modelObject);
    }
}
