/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.buffs.status;

/**
 *
 * @author Carl
 */
public class BuffVisualisationComponent{

    public BuffVisualisationComponent(String name){
        this.name = name;
    }
    private String name;

    public String getName(){
        return name;
    }
}