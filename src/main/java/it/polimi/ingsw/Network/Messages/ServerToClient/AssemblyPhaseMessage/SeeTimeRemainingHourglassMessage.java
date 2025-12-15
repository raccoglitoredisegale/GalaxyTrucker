package it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class SeeTimeRemainingHourglassMessage extends Message {

    private final long timeRemaining;

    public SeeTimeRemainingHourglassMessage(MessageType messageType, long timeRemaining) {
        super(messageType);
        this.timeRemaining = timeRemaining;
    }

    public long getTimeRemaining() {
        return this.timeRemaining;
    }
}
