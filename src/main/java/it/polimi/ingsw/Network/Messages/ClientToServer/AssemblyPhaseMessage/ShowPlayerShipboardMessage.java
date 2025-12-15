package it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ShowPlayerShipboardMessage extends Message {
    private final String username;

    public ShowPlayerShipboardMessage(MessageType type, String username) {
        super(type);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
