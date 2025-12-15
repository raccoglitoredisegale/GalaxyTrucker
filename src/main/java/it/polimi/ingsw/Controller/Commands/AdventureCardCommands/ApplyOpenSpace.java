package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.OpenSpace;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.OpenSpaceCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class ApplyOpenSpace implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        OpenSpace openSpace = (OpenSpace) gc.getCurrentAdventureCard();

        try {
            if (gc.getCurrentPlayer() == handler.getPlayer().getUsername()) {
                AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
                msg.MessageOpenSpace(gc.getFlightboard(), handler.getPlayer(), ((OpenSpaceCardMessage) message).getEnginePower(), ((OpenSpaceCardMessage) message).getRemovedBatteryFrom());
                ApplyEffect applyEffect = new ApplyEffect();
                applyEffect.visit(openSpace, msg);
                if (((OpenSpaceCardMessage) message).getEnginePower() == 0) {
                    gc.addStalledPlayer(handler.getPlayer());
                }
                handler.sendToClient(new Message(MessageType.APPLY_OPEN_SPACE_OK));
                if (gc.getPlayerActionOrder().isEmpty()) {
                    gc.completeCurrentAdventureCard();
                    openSpace.setCompleted(true);
                    gc.checkStalledPlayers();
                    gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                            new ArrayList<Player>());
                    gc.checkGameEnded();
                } else {
                    gc.setCurrentPlayer();
                    openSpace.setAlreadyShown(true);
                    ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, openSpace, gc.getCurrentPlayer());
                    gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                            new ArrayList<Player>());
                }
            } else {
                handler.sendToClient(new Message(MessageType.APPLY_OPEN_SPACE_KO));
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.APPLY_OPEN_SPACE_KO));
        }
    }
}
