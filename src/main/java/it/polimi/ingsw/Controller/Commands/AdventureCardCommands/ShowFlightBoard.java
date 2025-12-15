package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ShowFlightBoardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ShowFlightBoard implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        try {
            ShowFlightBoardMessage okMessage = new ShowFlightBoardMessage(MessageType.SHOW_FLIGHT_BOARD_OK, gc.getFlightboard());
//            ModelElementPrinter printer = new ModelElementPrinter();
//            printer.displayFlightboard(okMessage.getFlightBoard());
            handler.sendToClient(okMessage);
            System.out.println("Show flight board");
        } catch (Exception e) {
            Message koResponse = new Message(MessageType.SHOW_FLIGHT_BOARD_KO);
            handler.sendToClient(koResponse);
        }
    }
}
