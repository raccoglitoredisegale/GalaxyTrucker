package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Slavers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ApplySlavers implements ServerControllerCommand {
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Slavers slavers = (Slavers) gc.getCurrentAdventureCard();

        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageSlavers(handler.getPlayer(), gc.getFlightboard());
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(slavers, msg);
            slavers.setCompleted(true);
            handler.sendToClient(new Message(MessageType.APPLY_SLAVERS_OK));
            gc.completeCurrentAdventureCard();
            gc.checkStalledPlayers();
            gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                    gc.getStalledPlayers());
            gc.checkGameEnded();
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.APPLY_SLAVERS_KO));
        }
    }
}
