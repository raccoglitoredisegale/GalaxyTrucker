package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCard;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageWarzoneInfo;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneControlCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class WarzoneControl implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        Warzone warzone = (Warzone) gc.getCurrentAdventureCard();
        OperationVisitor applyOperation = new ApplyOperation();
        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();

        if (!warzone.getFirstControl() && !warzone.getSecondControl() && !warzone.getThirdControl()) {
            if (!((WarzoneControlCardMessage) message).getRemovedBatteryFrom().isEmpty()) {
                handler.getPlayer().getShip().acceptRemoveBattery(applyOperation, ((WarzoneControlCardMessage) message).getRemovedBatteryFrom());
            }
            warzone.setFirstPlayersParameters(handler.getPlayer(), ((WarzoneControlCardMessage) message).getParameter());
            gc.setAffectedPlayers(handler.getPlayer());
            if (gc.getInGamePlayers().size() == gc.getAffectedPlayers().size()) {
                try {
                    Player loser = gc.getPlayerByName(warzone.getLoser(warzone.getFirstPlayersParameters()));
                    msg.MessageWarzone(loser, gc.getFlightboard(), null, false, null, null, null, 1);
                    ApplyEffect applyEffect = new ApplyEffect();
                    applyEffect.visit(warzone, msg);
                    warzone.setFirstControl(true);
                    gc.setPlayerActionOrder(warzone);
                    gc.clearAffectedPlayers();
                    handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));
                    gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), "0000", ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(0).get(0), 'D', warzone.getLoser(warzone.getFirstPlayersParameters())),
                            new ArrayList<Player>());
                    warzone.setAlreadyShown(true);
                    ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.CONTINUE_ADVENTURE_CARD, warzone, gc.getCurrentPlayer());
                    gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                            new ArrayList<Player>());
                } catch (Exception e) {
                    handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_KO));
                }
            } else {
                handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));
                gc.setCurrentPlayer();
                gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), gc.getCurrentPlayer(), ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(0).get(0), 'Z', "Z"),
                        new ArrayList<Player>());
            }
        } else if (warzone.getFirstControl() && !warzone.getSecondControl() && !warzone.getThirdControl()) {
            if (!((WarzoneControlCardMessage) message).getRemovedBatteryFrom().isEmpty()) {
                handler.getPlayer().getShip().acceptRemoveBattery(applyOperation, ((WarzoneControlCardMessage) message).getRemovedBatteryFrom());
            }
            warzone.setSecondPlayersParameters(handler.getPlayer(), ((WarzoneControlCardMessage) message).getParameter());
            gc.setAffectedPlayers(handler.getPlayer());
            if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size()) {
                try {
                    warzone.setSecondControl(true);
                    gc.setPlayerActionOrder(warzone);
                    gc.clearAffectedPlayers();
                    switch (warzone.getWarzoneCriteria().get(1).get(1)) {
                        case 'T': {
                            handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));

                            gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), "0000", ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(1).get(0), 'C', warzone.getLoser(warzone.getSecondPlayersParameters())),
                                    new ArrayList<Player>());
                            warzone.setAlreadyShown(true);
                            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.WARZONE_CREW_PENALTY, warzone, warzone.getLoser(warzone.getSecondPlayersParameters()));
                            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                                    new ArrayList<Player>());
                            break;
                        }
                        case 'G': {
                            handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));

                            gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), "0000", ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(1).get(0), 'G', warzone.getLoser(warzone.getSecondPlayersParameters())),
                                    new ArrayList<Player>());
                            warzone.setAlreadyShown(true);
                            ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.WARZONE_GOODS_PENALTY, warzone, warzone.getLoser(warzone.getSecondPlayersParameters()));
                            gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                                    new ArrayList<Player>());
                            break;
                        }
                    }

                } catch (Exception e) {
                    handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_KO));
                }
            } else {
                handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));
                gc.setCurrentPlayer();
                gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), gc.getCurrentPlayer(), ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(1).get(0), 'Z', "Z"),
                        new ArrayList<Player>());
            }
        } else if (warzone.getFirstControl() && warzone.getSecondControl() && !warzone.getThirdControl()) {
            if (!((WarzoneControlCardMessage) message).getRemovedBatteryFrom().isEmpty()) {
                handler.getPlayer().getShip().acceptRemoveBattery(applyOperation, ((WarzoneControlCardMessage) message).getRemovedBatteryFrom());
            }
            warzone.setThirdPlayersParameters(handler.getPlayer(), ((WarzoneControlCardMessage) message).getParameter());
            gc.setAffectedPlayers(handler.getPlayer());
            if (gc.getAffectedPlayers().size() == gc.getInGamePlayers().size()) {
                try {
                    warzone.setThirdControl(true);
                    gc.setPlayerActionOrder(warzone);
                    gc.clearAffectedPlayers();
                    handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));

                    gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), "0000", ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(2).get(0), 'B', warzone.getLoser(warzone.getThirdPlayersParameters())),
                            new ArrayList<Player>());
                    warzone.setAlreadyShown(true);
                    ContinueAdventureCardMessage serverResponse = new ContinueAdventureCardMessage(MessageType.WARZONE_BLAST_PENALTY, warzone, warzone.getLoser(warzone.getThirdPlayersParameters()));
                    gc.sendBroadCastMessage(new BroadcastMessageAdventureCard(MessageType.BROADCAST, MessageType.BC_ADVENTURE_CARD, serverResponse),
                            new ArrayList<Player>());
                } catch (Exception e) {
                    handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_KO));
                }
            } else {
                handler.sendToClient(new Message(MessageType.WARZONE_CONTROL_OK));
                gc.setCurrentPlayer();
                gc.sendBroadCastMessage(new BroadcastMessageWarzoneInfo(MessageType.BROADCAST, MessageType.WARZONE_CONTROL_INFO, handler.getPlayer().getUsername(), gc.getCurrentPlayer(), ((WarzoneControlCardMessage) message).getParameter(), warzone.getWarzoneCriteria().get(2).get(0), 'Z', "Z"),
                        new ArrayList<Player>());
            }
        }

    }
}
