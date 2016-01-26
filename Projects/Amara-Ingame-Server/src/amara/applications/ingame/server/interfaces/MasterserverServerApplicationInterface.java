/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.interfaces;

import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.server.IngameServerApplication;
import amara.libraries.applications.headless.applications.HeadlessAppState;

/**
 *
 * @author Carl
 */
public interface MasterserverServerApplicationInterface{
    
    public abstract <T extends HeadlessAppState> T getState(Class<T> stateClass);
    
    public abstract void onGameServerInitialized(Game game);
    
    public abstract void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception);
    
    public abstract void onGameOver(IngameServerApplication ingameServerApplication);
}