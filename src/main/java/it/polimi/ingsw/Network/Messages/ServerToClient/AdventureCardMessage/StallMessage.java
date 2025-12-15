package it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class StallMessage extends Message {
    private final String stalledPlayerUsername;

    public StallMessage(MessageType messageType, String stalledPlayerUsername) {
        super(messageType);
        this.stalledPlayerUsername = stalledPlayerUsername;
    }

    public String getstalledPlayerUsername() {
        return stalledPlayerUsername;
    }
}
