package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Pirates;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.BlastCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class ManageBlast implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        CardType type = gc.getCurrentAdventureCard().getCardType();

        switch (type) {
            case PIRATES: {
                Pirates pirates = (Pirates) gc.getCurrentAdventureCard();
                try {
                    AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
                    msg.MessagePirates(false, handler.getPlayer(), gc.getFlightboard(), ((BlastCardMessage) message).getCanDefend(), ((BlastCardMessage) message).getRemovedComponentFrom(), ((BlastCardMessage) message).getRemovedBatteryFrom());
                    ApplyEffect applyEffect = new ApplyEffect();
                    applyEffect.visit(pirates, msg);
                    handler.sendToClient(new Message(MessageType.MANAGE_BLAST_OK));
                    if (pirates.getCurrentShots().isEmpty()) {
                        gc.setAffectedPlayers(handler.getPlayer());
                    }
                    if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size() && pirates.getCurrentShots().isEmpty()) {
                        gc.completeCurrentAdventureCard();
                        gc.checkStalledPlayers();
                        gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                                gc.getStalledPlayers());
                        gc.checkGameEnded();
                    } else if (gc.getAffectedPlayers().size() != gc.getInGamePlayers().size() && pirates.getCurrentShots().isEmpty()) {
                        pirates.resetCurrentShots();
                        gc.setCurrentPlayer();
                        pirates.setAlreadyShown(true);
                        ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, pirates, gc.getCurrentPlayer());
                        gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                                new ArrayList<Player>());
                    }
                } catch (Exception e) {
                    handler.sendToClient(new Message(MessageType.MANAGE_BLAST_KO));
                }
                break;
            }

            case WARZONE: {
                Warzone warzone = (Warzone) gc.getCurrentAdventureCard();
                try {
                    AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
                    msg.MessageWarzone(handler.getPlayer(), gc.getFlightboard(), null, ((BlastCardMessage) message).getCanDefend(), ((BlastCardMessage) message).getRemovedComponentFrom(), ((BlastCardMessage) message).getRemovedBatteryFrom(), null, 3);
                    ApplyEffect applyEffect = new ApplyEffect();
                    applyEffect.visit(warzone, msg);
                    handler.sendToClient(new Message(MessageType.MANAGE_BLAST_OK));
                    if (warzone.getCurrentBlastArray().isEmpty()) {
                        gc.completeCurrentAdventureCard();
                        gc.checkStalledPlayers();
                        gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                                gc.getStalledPlayers());
                        gc.checkGameEnded();
                    }
                } catch (Exception e) {
                    handler.sendToClient(new Message(MessageType.MANAGE_BLAST_KO));
                }
                break;
            }
        }
    }
}
