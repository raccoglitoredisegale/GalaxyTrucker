package it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage;

import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ChooseColorMessage extends Message {
    private final Color color;

    public ChooseColorMessage(MessageType messageType, Color color) {
        super(messageType);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
