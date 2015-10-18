/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ContinuousEffectComponent{

    public ContinuousEffectComponent(){
        
    }
    
    public ContinuousEffectComponent(int effectEntity){
        this.effectEntity = effectEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int effectEntity;

    public int getEffectEntity(){
        return effectEntity;
    }
}
