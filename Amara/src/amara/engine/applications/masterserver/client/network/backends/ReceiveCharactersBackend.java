/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.client.appstates.CharactersAppState;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;

/**
 *
 * @author Carl
 */
public class ReceiveCharactersBackend implements MessageBackend{

    public ReceiveCharactersBackend(CharactersAppState charactersAppState){
        this.charactersAppState = charactersAppState;
    }
    private CharactersAppState charactersAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameContents){
            Message_GameContents message = (Message_GameContents) receivedMessage;
            charactersAppState.setCharacters(message.getCharacters());
        }
    }
}
