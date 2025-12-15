package it.polimi.ingsw.Network.Messages.ServerToClient.PreLobbyMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ShowLobby extends Message {
    private final String lobbies;

    public ShowLobby(MessageType messageType, String lobbies) {
        super(messageType);
        this.lobbies = lobbies;
    }

    public String getLobbies() {
        return lobbies;
    }
}
