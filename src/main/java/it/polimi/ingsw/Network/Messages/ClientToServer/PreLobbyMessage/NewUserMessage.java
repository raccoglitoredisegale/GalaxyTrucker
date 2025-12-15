package it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class NewUserMessage extends Message {

    private final String username;

    public NewUserMessage(MessageType messageType, String username) {
        super(messageType);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
