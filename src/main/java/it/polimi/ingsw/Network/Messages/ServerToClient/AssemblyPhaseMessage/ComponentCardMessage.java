package it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ComponentCardMessage extends Message {

    private final String componentCardId;

    public ComponentCardMessage(MessageType messageType, String id) {
        super(messageType);
        this.componentCardId = id;
    }

    public String getComponentCardId() {
        return this.componentCardId;
    }
}
