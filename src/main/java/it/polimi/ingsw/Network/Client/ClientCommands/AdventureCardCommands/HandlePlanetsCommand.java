package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.PlanetsCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandlePlanetsCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        TextUserInterface tui = client.getTUI();
        if (command.equals("occupy")) {
            try {
                if (client.getShipboard().getStorageList().size() < 1) {
                    tui.print("\nYou don't have any storage to store the resources");
                    return;
                }
                PlanetsCardMessage planetsCardMessage = tui.handlePlanets(client);
                Message response = client.sendMessageAndWaitResponse(planetsCardMessage, getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.OCCUPY_PLANET_OK) {
                    tui.print("Planet occupied");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage2());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");
                    tui.printHelpForCurrentState();
                } else if (response.getType() == MessageType.ADVENTURE_CARD_COMPLETED) {
                    tui.print("\nCard handled");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage2());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                    tui.setTUIState(TUIState.ADVENTURE_CARD_DRAW);
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("Error occurred while handling planets card");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.OCCUPY_PLANET_OK, MessageType.OCCUPY_PLANET_KO, MessageType.ADVENTURE_CARD_COMPLETED)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
