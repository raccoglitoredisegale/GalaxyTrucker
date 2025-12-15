package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class DrawAdventureCard implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());

        if (gc.canDrawAdventureCard(handler.getPlayer())) {
            if (gc.getDeckManager().getMainDeck().isEmpty()) {
                Message koResponse = new Message(MessageType.DRAW_ADVENTURE_CARD_KO);
            } else {
                AdventureCard adventureCard = gc.getDeckManager().drawCard();
                gc.setCurrentAdventureCard(adventureCard);
                gc.setPlayerActionOrder(adventureCard);
                gc.clearAffectedPlayers();
                Message okResponse = new Message(MessageType.DRAW_ADVENTURE_CARD_OK);
                handler.sendToClient(okResponse);
                ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, adventureCard, gc.getCurrentPlayer());
                gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                        new ArrayList<Player>());
            }
        } else {
            Message koResponse = new Message(MessageType.DRAW_ADVENTURE_CARD_KO);
            handler.sendToClient(koResponse);
        }
    }
}



