package it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;


public class TakeComponentCardFromRevealedDeckMessage extends Message {

    private final int idx;

    public TakeComponentCardFromRevealedDeckMessage(MessageType messageType, int idx) {
        super(messageType);
        this.idx = idx;
    }

    public int getIdx() {
        return this.idx;
    }
}
