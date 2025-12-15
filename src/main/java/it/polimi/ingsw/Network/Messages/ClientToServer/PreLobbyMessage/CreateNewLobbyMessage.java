package it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class CreateNewLobbyMessage extends Message {
    private final int maxPlayers;
    private final String lobbyName;

    public CreateNewLobbyMessage(MessageType messageType, int maxPlayers, String lobbyName) {
        super(messageType);
        this.maxPlayers = maxPlayers;
        this.lobbyName = lobbyName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}
