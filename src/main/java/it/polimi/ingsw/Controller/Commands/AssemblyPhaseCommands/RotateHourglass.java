package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.Exceptions.HourglassException;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RotateHourglass implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        try {
            SystemController sc = SystemController.getInstance();
            GameController gc = sc.getGameController(handler.getPlayer().getGameID());
            if (!gc.turnTheHourglass(handler.getPlayer().getUsername())) {
                Timer timer = new Timer();
                TimerTask assemblyPhaseFinished = new TimerTask() {
                    public void run() {
                        System.out.println("Assembly phase finished!");
                        gc.addToFlightBoard();
                        gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.FINISHED_ASSEMBLY_PHASE), new ArrayList<>());
                    }
                };
                timer.schedule(assemblyPhaseFinished, 90000);
            }
            Message response = new Message(MessageType.ROTATE_HOURGLASS_OK);
            handler.sendToClient(response);
        } catch (HourglassException e) {
            Message response = new ErrorMessage(MessageType.ROTATE_HOURGLASS_KO, e.getMessage());
            handler.sendToClient(response);
        }
    }
}
