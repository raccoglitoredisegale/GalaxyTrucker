package it.polimi.ingsw.Controller.Commands.LobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class LeaveGame implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        try {
            SystemController systemController = SystemController.getInstance();
            systemController.gameLeaved(handler);
            handler.sendToClient(new Message(MessageType.LEAVE_GAME_OK));
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendToClient(new Message(MessageType.LEAVE_GAME_KO));
        }
    }
}
