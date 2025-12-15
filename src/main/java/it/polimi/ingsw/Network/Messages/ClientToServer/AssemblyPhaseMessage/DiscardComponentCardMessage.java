package it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class DiscardComponentCardMessage extends Message {

    private final String componentCardID;

    public DiscardComponentCardMessage(MessageType messageType, String componentCardID) {
        super(messageType);
        this.componentCardID = componentCardID;
    }

    public String getComponentCardID() {
        return this.componentCardID;
    }
}
