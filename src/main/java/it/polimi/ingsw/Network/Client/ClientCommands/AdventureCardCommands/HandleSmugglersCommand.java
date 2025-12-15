package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersLostCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersWonCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleSmugglersCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();
        Smugglers smugglers = (Smugglers) client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("fight") && !smugglers.isControlled()) {
            SmugglersWonCardMessage smugglerssWonCardMessage = tui.handleSmugglersControl(client);
            if (smugglerssWonCardMessage.getCannonPower() >= smugglers.getCannonsPower()) {
                Message response = client.sendMessageAndWaitResponse(smugglerssWonCardMessage, getCreateLobbyTypesMessage1());
                if (response.getType() == MessageType.SMUGGLERS_WON_OK) {
                    smugglers.setControlled(true);
                    tui.setTUIState(TUIState.ADVENTURE_CARD_SMUGGLERS);
                    tui.print("you won against smugglers, choose what to do");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                    tui.printHelpForCurrentState();
                }
                if (response.getType() == MessageType.SMUGGLERS_WON_KO) {
                    tui.print("error handling smugglers by server");
                }
            } else {
                SmugglersLostCardMessage smugglersLostCardMessage = tui.handleSmugglersLost(client);
                Message response = client.sendMessageAndWaitResponse(smugglersLostCardMessage, getCreateLobbyTypesMessage1());
                if (response.equals(MessageType.SMUGGLERS_LOST_OK)) {
                    tui.print("you lost against smugglers");

                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                    tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                    tui.printHelpForCurrentState();
                }
            }

        } else if (command.equals("fight") && smugglers.isControlled()) tui.print("you already defeated the smugglers");
        else if (command.startsWith("apply") && smugglers.isControlled()) {

            SmugglersCardMessage smugglersCardMessage = tui.handleSmugglersMessage(client);
            Message response = client.sendMessageAndWaitResponse(smugglersCardMessage, getCreateLobbyTypesMessage2());
            if (response.getType() == MessageType.APPLY_SMUGGLERS_OK) {
                tui.print("smugglers applied");
                Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                    Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                    client.updateShipboard(shipboard);
                } else tui.print("error updating your shipboard");

                tui.printHelpForCurrentState();
            }
            if (response.getType() == MessageType.APPLY_SMUGGLERS_KO) {
                tui.print("error applying smugglers by server");
            }
        } else if (command.equals("apply") && !smugglers.isControlled())
            tui.print("you have to fight the smugglers first");
        else {
            tui.print("unknown command");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage1() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SMUGGLERS_WON_OK, MessageType.SMUGGLERS_WON_KO, MessageType.SMUGGLERS_LOST_OK, MessageType.SMUGGLERS_LOST_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.APPLY_SMUGGLERS_OK, MessageType.APPLY_SMUGGLERS_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage3() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
