/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameStarted extends AbstractMessage{
    
    public Message_GameStarted(){
        
    }
    
    public Message_GameStarted(int port, int authentificationKey){
        this.port = port;
        this.authentificationKey = authentificationKey;
    }
    private int port;
    private int authentificationKey;

    public int getPort(){
        return port;
    }

    public int getAuthentificationKey(){
        return authentificationKey;
    }
}
