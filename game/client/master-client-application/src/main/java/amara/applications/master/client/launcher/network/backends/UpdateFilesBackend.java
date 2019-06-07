/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.ClientLauncher;
import amara.applications.master.network.messages.Message_UpdateFiles;
import amara.applications.master.network.messages.objects.UpdateFile;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class UpdateFilesBackend implements MessageBackend{

    public UpdateFilesBackend(ClientLauncher clientLauncher){
        this.clientLauncher = clientLauncher;
    }
    private ClientLauncher clientLauncher;
    private LinkedList<UpdateFile> tmpUpdateFiles = new LinkedList<>();
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_UpdateFiles){
            Message_UpdateFiles message = (Message_UpdateFiles) receivedMessage;
            for(UpdateFile updateFile : message.getUpdateFiles()){
                tmpUpdateFiles.add(updateFile);
            }
            if(message.isEndReached()){
                clientLauncher.update(new LinkedList<>(tmpUpdateFiles));
                tmpUpdateFiles.clear();
            }
        }
    }
}