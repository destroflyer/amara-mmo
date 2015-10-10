/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import amara.engine.JMonkeyUtil;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class DirectionSystem implements EntitySystem{
    
    public DirectionSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, DirectionComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(DirectionComponent.class)){
            updateDirection(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(DirectionComponent.class)){
            updateDirection(entityWorld, entity);
        }
    }
    
    private void updateDirection(EntityWorld entityWorld, Integer entity){
        Node node = entitySceneMap.requestNode(entity);
        DirectionComponent directionComponent = entityWorld.getComponent(entity, DirectionComponent.class);
        JMonkeyUtil.setLocalRotation(node, new Vector3f(directionComponent.getVector().getX(), 0, directionComponent.getVector().getY()));
    }
}
