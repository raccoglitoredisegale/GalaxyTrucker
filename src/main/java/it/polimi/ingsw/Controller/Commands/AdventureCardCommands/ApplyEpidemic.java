package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Epidemic;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ApplyEpidemic implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Epidemic epidemic = (Epidemic) gc.getCurrentAdventureCard();

        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageEpidemic(handler.getPlayer());
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(epidemic, msg);
            gc.setAffectedPlayers(handler.getPlayer());
            handler.sendToClient(new Message(MessageType.APPLY_EPIDEMIC_OK));
            if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size()) {
                gc.completeCurrentAdventureCard();
                gc.checkStalledPlayers();
                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                        gc.getStalledPlayers());
                gc.checkGameEnded();
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.APPLY_EPIDEMIC_KO));
        }
    }
}
