package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Map;

public class ApplySmugglers implements ServerControllerCommand {
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Smugglers smugglers = (Smugglers) gc.getCurrentAdventureCard();
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = ((SmugglersCardMessage) message).getGoodsMap();
        try {
            AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
            msg.MessageSmugglers(true, handler.getPlayer(), gc.getFlightboard(), goodsMap, null);
            ApplyEffect applyEffect = new ApplyEffect();
            applyEffect.visit(smugglers, msg);
            smugglers.setCompleted(true);
            handler.sendToClient(new Message(MessageType.APPLY_SMUGGLERS_OK));
            gc.completeCurrentAdventureCard();
            gc.checkStalledPlayers();
            gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                    gc.getStalledPlayers());
            gc.checkGameEnded();
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.APPLY_SMUGGLERS_KO));
        }
    }
}
