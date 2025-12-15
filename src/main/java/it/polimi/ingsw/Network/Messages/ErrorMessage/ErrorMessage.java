package it.polimi.ingsw.Network.Messages.ErrorMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ErrorMessage extends Message {
    private final String errorSpecifiy;

    public ErrorMessage(MessageType messageType, String errorSpecifiy) {
        super(messageType);
        this.errorSpecifiy = errorSpecifiy;
    }

    public String getErrorSpecifiy() {
        return this.errorSpecifiy;
    }
}
