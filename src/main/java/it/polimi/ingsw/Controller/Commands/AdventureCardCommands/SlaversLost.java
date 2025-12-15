package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Slavers;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SlaversLostCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class SlaversLost implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        OperationVisitor applyOperation = new ApplyOperation();
        Slavers slavers = (Slavers) gc.getCurrentAdventureCard();
        try {
            handler.getPlayer().getShip().acceptRemoveCrew(applyOperation, ((SlaversLostCardMessage) message).getRemovedCrewFrom());
            gc.setAffectedPlayers(handler.getPlayer());
            handler.sendToClient(new Message(MessageType.SLAVERS_LOST_OK));
            if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size()) {
                gc.checkStalledPlayers();
                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                        gc.getStalledPlayers());
                gc.checkGameEnded();
            } else {
                gc.setCurrentPlayer();
                slavers.setAlreadyShown(true);
                ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, slavers, gc.getCurrentPlayer());
                gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                        new ArrayList<Player>());
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.SLAVERS_LOST_KO));
            gc.setCurrentPlayer();
            slavers.setAlreadyShown(true);
            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, slavers, gc.getCurrentPlayer());
            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                    new ArrayList<Player>());

        }
    }
}
