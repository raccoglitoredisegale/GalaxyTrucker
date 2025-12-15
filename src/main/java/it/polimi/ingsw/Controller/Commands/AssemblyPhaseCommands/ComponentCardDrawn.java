package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.Exceptions.DeckException;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.ComponentCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ComponentCardDrawn implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) throws DeckException {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        try {
            String id;
            do {
                id = gc.getComponentCardDeck().getCardID();
            } while (id.equals("11811") || id.equals("11812") || id.equals("11831") || id.equals("11830")); // these are the starting cabin
            ComponentCardMessage componentCardMessage = new ComponentCardMessage(MessageType.COMPONENT_CARD_DRAWN_OK, id);
            handler.sendToClient(componentCardMessage);
        } catch (Exception e) { // DeckFinished
            ErrorMessage response = new ErrorMessage(MessageType.COMPONENT_CARD_DRAWN_KO, e.getMessage());
            handler.sendToClient(response);
        }
    }
}
