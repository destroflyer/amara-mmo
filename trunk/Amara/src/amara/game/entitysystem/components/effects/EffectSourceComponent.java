/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects;

/**
 *
 * @author Carl
 */
public class EffectSourceComponent{

    public EffectSourceComponent(int sourceEntityID){
        this.sourceEntityID = sourceEntityID;
    }
    private int sourceEntityID;

    public int getSourceEntityID(){
        return sourceEntityID;
    }
}
