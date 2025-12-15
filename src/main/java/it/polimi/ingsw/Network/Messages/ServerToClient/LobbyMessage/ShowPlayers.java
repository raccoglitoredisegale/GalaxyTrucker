package it.polimi.ingsw.Network.Messages.ServerToClient.LobbyMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ShowPlayers extends Message {

    private final String players;

    public ShowPlayers(MessageType messageType, String players) {
        super(messageType);
        this.players = players;
    }

    public String getPlayers() {
        return this.players;
    }
}
