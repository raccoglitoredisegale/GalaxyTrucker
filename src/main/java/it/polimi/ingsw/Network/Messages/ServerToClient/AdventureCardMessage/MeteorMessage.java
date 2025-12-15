package it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class MeteorMessage extends Message {
    private final String direction;
    private final String dice;
    private final String dimension;

    public MeteorMessage(MessageType messageType, String dimension, String direction, String dice) {
        super(messageType);
        this.direction = direction;
        this.dice = dice;
        this.dimension = dimension;
    }

    public String getDirection() {
        return direction;
    }

    public String getDice() {
        return dice;
    }

    public String getDimension() {
        return dimension;
    }
}

