package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.Exceptions.DeckException;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.TakeComponentCardFromRevealedDeckMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.ComponentCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class TakeCardFromRevealedDeck implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        int idx = ((TakeComponentCardFromRevealedDeckMessage) message).getIdx();
        try {
            String id = gc.getComponentCardDeck().getComponentCardFromRevealedDeck(idx);
            Message response = new ComponentCardMessage(MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK_OK, id);
            handler.sendToClient(response);
        } catch (DeckException e) {
            Message response = new ErrorMessage(MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK_KO, e.getMessage());
            handler.sendToClient(response);
        }
    }
}
