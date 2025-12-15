package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.ComponentCardPlacedMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ComponentCardPlaced implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        try {
            ComponentCardPlacedMessage componentCardPlaced = (ComponentCardPlacedMessage) message;
            Integer x = componentCardPlaced.getX();
            Integer y = componentCardPlaced.getY();
            ComponentCard componentCard = componentCardPlaced.getComponentCard();
            handler.getPlayer().getShip().addComponent(
                    componentCard, x, y
            );
            handler.sendToClient(new Message(MessageType.COMPONENT_CARD_PLACED_OK));
        } catch (Exception e) {
            handler.sendToClient(new ErrorMessage(MessageType.COMPONENT_CARD_PLACED_KO,
                    "Cannot place this component card on your shipboard!"));
        }
    }
}
