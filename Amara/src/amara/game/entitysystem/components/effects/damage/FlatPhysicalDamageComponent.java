/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.damage;

/**
 *
 * @author Carl
 */
public class FlatPhysicalDamageComponent{

    public FlatPhysicalDamageComponent(float value){
        this.value = value;
    }
    private float value;

    public float getValue(){
        return value;
    }
}
