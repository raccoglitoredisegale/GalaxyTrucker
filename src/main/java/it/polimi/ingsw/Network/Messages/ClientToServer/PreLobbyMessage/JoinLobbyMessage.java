package it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class JoinLobbyMessage extends Message {
    private final String lobbyId;

    public JoinLobbyMessage(MessageType messageType, String lobbyId) {
        super(messageType);
        this.lobbyId = lobbyId;
    }

    public String getLobbyId() {
        return this.lobbyId;
    }
}
