package it.polimi.ingsw.Controller.Commands.PreLobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.CreateNewLobbyMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class CreateNewLobby implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message clientMessage) {
        CreateNewLobbyMessage createNewLobbyMessage = (CreateNewLobbyMessage) clientMessage;
        SystemController systemController = SystemController.getInstance();
        systemController.createGame(createNewLobbyMessage.getMaxPlayers(), handler, createNewLobbyMessage.getLobbyName());
        Message serverResponse = new Message(MessageType.LOBBY_CREATED_OK);
        handler.sendToClient(serverResponse);
    }
}
