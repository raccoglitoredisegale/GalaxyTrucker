package it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class OtherPlayerShipboardResponseMessage extends Message {

    private final Shipboard shipboard;

    public OtherPlayerShipboardResponseMessage(MessageType messageType, Shipboard shipboard) {
        super(messageType);
        this.shipboard = shipboard;
    }

    public Shipboard getShipboard() {
        return this.shipboard;
    }
}
