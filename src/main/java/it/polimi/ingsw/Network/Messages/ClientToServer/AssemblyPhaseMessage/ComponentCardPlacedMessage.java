package it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ComponentCardPlacedMessage extends Message {
    private final ComponentCard componentCard;
    private final Integer x;
    private final Integer y;

    public ComponentCardPlacedMessage(MessageType messageType, ComponentCard componentCard, int x, int y) {
        super(messageType);
        this.componentCard = componentCard;
        this.x = x;
        this.y = y;
    }

    public ComponentCard getComponentCard() {
        return this.componentCard;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }
}
