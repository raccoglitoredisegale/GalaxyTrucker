package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Slavers;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SlaversLostCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SlaversWonCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleSlaversCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();
        Slavers slavers = (Slavers) client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("fight") && !slavers.isControlled()) {
            SlaversWonCardMessage slaversWonCardMessage = tui.handleSlaversControl(client);
            if (slaversWonCardMessage.getCannonPower() >= slavers.getSlaversCannonPower()) {
                Message response = client.sendMessageAndWaitResponse(slaversWonCardMessage, getCreateLobbyTypesMessage1());
                if (response.getType() == MessageType.SLAVERS_WON_OK) {
                    slavers.setControlled(true);
                    tui.setTUIState(TUIState.ADVENTURE_CARD_SLAVERS);
                    client.getClientGameController().setIsDefeated(true);
                    tui.print("you won against slavers, choose what to do");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                    tui.printHelpForCurrentState();
                }
                if (response.getType() == MessageType.SLAVERS_WON_KO) {
                    tui.print("error handling slavers by server");
                }

            } else {
                SlaversLostCardMessage slaversLostCardMessage = tui.handleSlaversLost(client);
                Message response = client.sendMessageAndWaitResponse(slaversLostCardMessage, getCreateLobbyTypesMessage1());
                if (response.getType() == MessageType.SLAVERS_LOST_OK) {
                    tui.print("you lost against slavers");

                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");
                    tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                    tui.printHelpForCurrentState();
                } else tui.print("error applying slavers' penalty");
            }

        } else if (command.equals("fight") && slavers.isControlled()) tui.print("you already defeated the slavers");
        else if (command.startsWith("apply") && slavers.isControlled()) {

            Message message = new Message(MessageType.APPLY_SLAVERS);
            Message response = client.sendMessageAndWaitResponse(message, getCreateLobbyTypesMessage2());
            if (response.getType() == MessageType.APPLY_SLAVERS_OK) {
                tui.print("slavers applied");
                Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                    Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                    client.updateShipboard(shipboard);
                } else tui.print("error updating your shipboard");

                tui.printHelpForCurrentState();
            } else if (response.getType() == MessageType.APPLY_SLAVERS_KO) {
                tui.print("error applying slavers by server");
            }
        } else if (command.equals("apply") && !slavers.isControlled()) tui.print("you have to fight the slavers first");
        else {
            tui.print("unknown command");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage1() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SLAVERS_WON_OK, MessageType.SLAVERS_WON_KO, MessageType.SLAVERS_LOST_OK, MessageType.SLAVERS_LOST_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.APPLY_SLAVERS_OK, MessageType.APPLY_SLAVERS_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage3() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
