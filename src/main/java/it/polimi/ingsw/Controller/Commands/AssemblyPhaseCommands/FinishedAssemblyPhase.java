package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class FinishedAssemblyPhase implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        gc.addToPlayerWhoFinishedAssemblyPhase(handler.getPlayer().getUsername());
        gc.getFlightboard().insertPlayer(handler.getPlayer(), 2);
        Message response = new Message(MessageType.FINISHED_ASSEMBLY_PHASE_OK);
        handler.sendToClient(response);
    }
}
