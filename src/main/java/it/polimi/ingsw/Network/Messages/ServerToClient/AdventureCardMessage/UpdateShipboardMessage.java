package it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class UpdateShipboardMessage extends Message {
    private Shipboard shipboard;

    public UpdateShipboardMessage(MessageType messageType) {
        super(messageType);
    }

    public Shipboard getShipboard() {
        return shipboard;
    }

    public void setShipboard(Shipboard shipboard) {
        this.shipboard = shipboard;
    }
}

