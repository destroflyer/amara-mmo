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
public class OnBuffRemoveEffectTriggersComponent{

    public OnBuffRemoveEffectTriggersComponent(){
        
    }
    
    public OnBuffRemoveEffectTriggersComponent(int... effectTriggerEntities){
        this.effectTriggerEntities = effectTriggerEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] effectTriggerEntities;

    public int[] getEffectTriggerEntities(){
        return effectTriggerEntities;
    }
}
