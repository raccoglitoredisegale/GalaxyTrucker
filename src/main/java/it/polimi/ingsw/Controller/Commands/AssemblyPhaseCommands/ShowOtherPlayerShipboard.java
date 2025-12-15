package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.Exceptions.InvalidUsername;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.ShowPlayerShipboardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.OtherPlayerShipboardResponseMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ShowOtherPlayerShipboard implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        ShowPlayerShipboardMessage showPlayerShipboardMessage = (ShowPlayerShipboardMessage) message;
        String username = showPlayerShipboardMessage.getUsername();
        try {
            Message response = new OtherPlayerShipboardResponseMessage(
                    MessageType.SHOW_OTHER_PLAYER_SHIPBOARD_OK, gc.getShipboardByUsername(username)
            );
            handler.sendToClient(response);
        } catch (InvalidUsername e) {
            Message response = new ErrorMessage(MessageType.SHOW_OTHER_PLAYER_SHIPBOARD_KO, "Player " + username + " not found!");
            handler.sendToClient(response);
        }

    }
}
