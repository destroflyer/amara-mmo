/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.network.backends;

import com.jme3.network.Message;
import amara.engine.applications.masterserver.client.appstates.ItemsAppState;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;

/**
 *
 * @author Carl
 */
public class ReceiveItemsBackend implements MessageBackend{

    public ReceiveItemsBackend(ItemsAppState itemsAppState){
        this.itemsAppState = itemsAppState;
    }
    private ItemsAppState itemsAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameContents){
            Message_GameContents message = (Message_GameContents) receivedMessage;
            itemsAppState.setItems(message.getItems());
        }
    }
}