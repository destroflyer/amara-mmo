/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import amara.engine.gui.GameScreenController;

/**
 *
 * @author Carl
 */
public class ScreenController_GameOver extends GameScreenController{

    public ScreenController_GameOver(){
        super("gameOver");
    }
    
    public void exit(){
        mainApplication.stop();
    }
}