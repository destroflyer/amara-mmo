/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class AddBuffComponent{

    public AddBuffComponent(){
        
    }
    
    public AddBuffComponent(int buffEntity, float duration){
        this.buffEntity = buffEntity;
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public int getBuffEntity(){
        return buffEntity;
    }

    public float getDuration(){
        return duration;
    }
}
