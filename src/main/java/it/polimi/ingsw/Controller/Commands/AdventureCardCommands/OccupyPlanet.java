package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Planets;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.PlanetsCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Map;

public class OccupyPlanet implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Planets currentPlanets = (Planets) gc.getCurrentAdventureCard();
        int occupedPlanet = ((PlanetsCardMessage) message).getOccupiedPlanetIndex();
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = ((PlanetsCardMessage) message).getGoodsMap();
        try {
            if (currentPlanets.getOccupedPlanets().get(occupedPlanet) == 0) {
                currentPlanets.setOccupedPlanets(occupedPlanet);
                AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
                msg.MessagePlanets(handler.getPlayer(), gc.getFlightboard(), goodsMap);
                ApplyEffect applyEffect = new ApplyEffect();
                applyEffect.visit(currentPlanets, msg);
                if (gc.getPlayerActionOrder().isEmpty() || allPlanetsAreOccupied(currentPlanets.getOccupedPlanets())) {
                    currentPlanets.setCompleted(true);
                    gc.completeCurrentAdventureCard();
                    handler.sendToClient(new Message(MessageType.ADVENTURE_CARD_COMPLETED));
                    gc.checkStalledPlayers();
                    gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_COMPLETED),
                            gc.getStalledPlayers());
                    gc.checkGameEnded();
                } else {
                    handler.sendToClient(new Message(MessageType.OCCUPY_PLANET_OK));
                    gc.setCurrentPlayer();
                    currentPlanets.setAlreadyShown(true);
                    ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, currentPlanets, gc.getCurrentPlayer());
                    gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                            new ArrayList<Player>());
                }


            }
        } catch (Exception e) {
            handler.sendToClient(new Message(MessageType.OCCUPY_PLANET_KO));
        }
    }

    private boolean allPlanetsAreOccupied(ArrayList<Integer> planets) {
        for (Integer planet : planets) {
            if (planet == 0) {
                return false;
            }
        }
        return true;
    }

}
