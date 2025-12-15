package it.polimi.ingsw.Network.Messages.ErrorMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class StartGameError extends Message {

    public StartGameError(MessageType messageType) {
        super(messageType);
    }
}
