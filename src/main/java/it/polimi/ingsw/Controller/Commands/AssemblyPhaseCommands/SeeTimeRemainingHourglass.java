package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.SeeTimeRemainingHourglassMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class SeeTimeRemainingHourglass implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        try {
            SystemController sc = SystemController.getInstance();
            GameController gc = sc.getGameController(handler.getPlayer().getGameID());
            Message response = new SeeTimeRemainingHourglassMessage(
                    MessageType.SEE_TIME_REMAINING_OK, gc.getRemainingTime()
            );
            handler.sendToClient(response);
        } catch (RuntimeException e) {
            handler.sendToClient(new ErrorMessage(MessageType.SEE_TIME_REMAINING_KO, e.getMessage()));
        }
    }
}
