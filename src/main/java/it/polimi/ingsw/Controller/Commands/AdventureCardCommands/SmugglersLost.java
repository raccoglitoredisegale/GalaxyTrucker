package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersLostCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class SmugglersLost implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        OperationVisitor applyOperation = new ApplyOperation();
        Smugglers smugglers = (Smugglers) gc.getCurrentAdventureCard();
        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageSmugglers(false, handler.getPlayer(), gc.getFlightboard(), ((SmugglersLostCardMessage) message).getRemovedGoodsFrom(), ((SmugglersLostCardMessage) message).getRemovedBatteryFrom());
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(smugglers, msg);
            gc.setAffectedPlayers(handler.getPlayer());
            handler.sendToClient(new Message(MessageType.SMUGGLERS_LOST_OK));
            if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size()) {
                gc.checkStalledPlayers();
                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                        gc.getStalledPlayers());
                gc.checkGameEnded();
            } else {
                gc.setCurrentPlayer();
                smugglers.setAlreadyShown(true);
                ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, smugglers, gc.getCurrentPlayer());
                gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                        new ArrayList<Player>());
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.SMUGGLERS_LOST_KO));
            gc.setCurrentPlayer();
            smugglers.setAlreadyShown(true);
            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, smugglers, gc.getCurrentPlayer());
            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                    new ArrayList<Player>());

        }
    }
}
