package it.polimi.ingsw.Network.Messages.ErrorMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class UsernameError extends Message {
    private String errorMessage;

    public UsernameError(MessageType messageType, String errorMessage) {
        super(messageType);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
