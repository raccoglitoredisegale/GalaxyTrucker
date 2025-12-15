package it.polimi.ingsw.Controller.Commands.FinalPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;


public class Exit implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        if (sc.getGameController(handler.getPlayer().getGameID()) == null) {
            try {
                handler.getPlayer().setColor(null);
                handler.sendToClient(new Message(MessageType.EXIT_OK));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                handler.sendToClient(new Message(MessageType.EXIT_KO));
            }
        } else {
            GameController gc = sc.getGameController(handler.getPlayer().getGameID());
            try {
                handler.getPlayer().setColor(null);
                gc.removePlayer(handler.getPlayer());
                sc.removeGameController(handler.getPlayer().getGameID());
                handler.sendToClient(new Message(MessageType.EXIT_OK));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                handler.sendToClient(new Message(MessageType.EXIT_KO));

            }
        }

    }
}
