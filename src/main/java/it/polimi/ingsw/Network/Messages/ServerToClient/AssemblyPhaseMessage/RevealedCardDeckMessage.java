package it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class RevealedCardDeckMessage extends Message {

    private final ArrayList<String> componentCards;

    public RevealedCardDeckMessage(MessageType messageType, ArrayList<String> componentCards) {
        super(messageType);
        this.componentCards = componentCards;
    }

    public ArrayList<String> getComponentCards() {
        return this.componentCards;
    }
}
