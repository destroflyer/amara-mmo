/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.attributes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BonusPercentageWalkSpeedComponent{

    public BonusPercentageWalkSpeedComponent(){
        
    }
    
    public BonusPercentageWalkSpeedComponent(float value){
        this.value = value;
    }
    private float value;

    public float getValue(){
        return value;
    }
}