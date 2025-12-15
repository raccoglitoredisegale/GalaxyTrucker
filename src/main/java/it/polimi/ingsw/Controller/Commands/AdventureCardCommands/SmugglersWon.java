package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersWonCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class SmugglersWon implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        OperationVisitor applyOperation = new ApplyOperation();
        Smugglers smugglers = (Smugglers) gc.getCurrentAdventureCard();
        try {
            handler.getPlayer().getShip().acceptRemoveBattery(applyOperation, ((SmugglersWonCardMessage) message).getRemovedBatteryFrom());
            handler.sendToClient(new Message(MessageType.SMUGGLERS_WON_OK));
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.SMUGGLERS_WON_KO));
            gc.setCurrentPlayer();
            smugglers.setAlreadyShown(true);
            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, smugglers, gc.getCurrentPlayer());
            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                    new ArrayList<Player>());

        }
    }
}
