/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.damage;

/**
 *
 * @author Carl
 */
public class ScalingAttackDamagePhysicalDamageComponent{

    public ScalingAttackDamagePhysicalDamageComponent(float ratio){
        this.ratio = ratio;
    }
    private float ratio;

    public float getRatio(){
        return ratio;
    }
}
