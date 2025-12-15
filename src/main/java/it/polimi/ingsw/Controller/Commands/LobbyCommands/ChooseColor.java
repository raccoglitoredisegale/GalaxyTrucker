package it.polimi.ingsw.Controller.Commands.LobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.ChooseColorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ChooseColor implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Color color = ((ChooseColorMessage) message).getColor();
        if (!gc.isColorAlreadyTaken(color)) {
            handler.getPlayer().setColor(color);
            handler.sendToClient(new Message(MessageType.CHOOSE_COLOR_OK));
        } else {
            handler.sendToClient(new Message(MessageType.CHOOSE_COLOR_KO));
        }
    }
}
