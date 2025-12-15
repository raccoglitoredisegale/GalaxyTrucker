package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class SkipAdventureCard implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        try {
            if (gc.getPlayerActionOrder().isEmpty()) {
                gc.getCurrentAdventureCard().setCompleted(true);
                gc.completeCurrentAdventureCard();
                handler.sendToClient(new Message(MessageType.ADVENTURE_CARD_COMPLETED));
                gc.checkStalledPlayers();
                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                        gc.getStalledPlayers());
                gc.checkGameEnded();
            } else {
                handler.sendToClient(new Message(MessageType.SKIP_ADVENTURE_CARD_OK));
                gc.setCurrentPlayer();
                gc.getCurrentAdventureCard().setAlreadyShown(true);
                ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, gc.getCurrentAdventureCard(), gc.getCurrentPlayer());
                gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                        new ArrayList<Player>());
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.SKIP_ADVENTURE_CARD_KO));
        }
    }

}
