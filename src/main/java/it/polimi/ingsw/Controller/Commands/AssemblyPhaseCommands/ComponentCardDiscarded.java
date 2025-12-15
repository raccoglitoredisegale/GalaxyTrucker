package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.DiscardComponentCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ComponentCardDiscarded implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        try {
            String id = ((DiscardComponentCardMessage) message).getComponentCardID();
            gc.getComponentCardDeck().updateRevealedCard(id);
            handler.sendToClient(new Message(MessageType.DISCARD_COMPONENT_CARD_DRAWN_OK));
        } catch (Exception e) {
            handler.sendToClient(new ErrorMessage(MessageType.DISCARD_COMPONENT_CARD_DRAWN_KO, "Cannot add to revealed card"));
        }

    }
}
