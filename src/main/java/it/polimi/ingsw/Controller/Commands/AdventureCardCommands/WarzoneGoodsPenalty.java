package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneGoodsPenaltyCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class WarzoneGoodsPenalty implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Warzone warzone = (Warzone) gc.getCurrentAdventureCard();

        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageWarzone(handler.getPlayer(), gc.getFlightboard(), null, false, null, ((WarzoneGoodsPenaltyCardMessage) message).getRemovedBatteryFrom(), ((WarzoneGoodsPenaltyCardMessage) message).getRemovedGoodsFrom(), 4);
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(warzone, msg);
            handler.sendToClient(new Message(MessageType.WARZONE_GOODS_PENALTY_OK));
            warzone.setAlreadyShown(true);
            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, warzone, gc.getCurrentPlayer());
            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                    new ArrayList<Player>());

        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.WARZONE_GOODS_PENALTY_KO));


        }
    }

}
