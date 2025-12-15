package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedStation;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.AbandonedStationCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Map;

public class OccupyStation implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = ((AbandonedStationCardMessage) message).getGoodsMap();
        AbandonedStation abandonedStation = (AbandonedStation) gc.getCurrentAdventureCard();

        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageAbandonedStation(handler.getPlayer(), gc.getFlightboard(), goodsMap);
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(abandonedStation, msg);
            if (abandonedStation.getIsCompleted()) {
                gc.completeCurrentAdventureCard();
                handler.sendToClient(new Message(MessageType.OCCUPY_STATION_OK));
                gc.checkStalledPlayers();

                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                        gc.getStalledPlayers());
                gc.checkGameEnded();
            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.OCCUPY_STATION_KO));
            gc.setCurrentPlayer();
            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, abandonedStation, gc.getCurrentPlayer());
            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                    new ArrayList<Player>());

        }
    }
}
