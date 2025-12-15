package it.polimi.ingsw.Controller.Commands.PreLobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.PreLobbyMessage.ShowLobby;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ListLobbies implements ServerControllerCommand {
    private SystemController controller;

    @Override
    public void execute(ClientHandler handler, Message message) {
        try {
            SystemController sc = SystemController.getInstance();
            Message serverResponse = new ShowLobby(MessageType.SHOW_LOBBY_OK, sc.lobbiesToString());
            handler.sendToClient(serverResponse);
        } catch (Exception e) {
            Message serverResponse = new ErrorMessage(MessageType.SHOW_LOBBY_KO, e.getMessage());
            handler.sendToClient(serverResponse);
        }
    }
}
