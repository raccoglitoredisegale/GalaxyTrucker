package it.polimi.ingsw.Controller.Commands.PreLobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.JoinLobbyMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class JoinLobby implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) {
        try {
            String lobbyId = ((JoinLobbyMessage) message).getLobbyId();
            var systemController = SystemController.getInstance();
            systemController.joinGame(lobbyId, handler);
            handler.sendToClient(new Message(MessageType.JOIN_LOBBY_OK));
        } catch (IllegalArgumentException e) {
            handler.sendToClient(new Message(MessageType.JOIN_LOBBY_KO));
        }
    }
}
