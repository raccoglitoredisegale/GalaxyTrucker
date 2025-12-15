package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedStation;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.AbandonedStationCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleAbandonedStationCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        OperationVisitor applyOperation = new ApplyOperation();
        TextUserInterface tui = client.getTUI();
        AbandonedStation abandonedStation = (AbandonedStation) client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("occupy")) {
            try {
                if (client.getShipboard().acceptCalculateCrew(applyOperation) >= abandonedStation.getCrewNeeded()) {
                    AbandonedStationCardMessage abandonedStationCardMessage = tui.handleAbandonedStation(client);
                    Message response = client.sendMessageAndWaitResponse(abandonedStationCardMessage, getCreateLobbyTypesMessage());
                    if (response.getType() == MessageType.OCCUPY_STATION_OK) {
                        tui.print("Station occupied");
                        Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage2());
                        if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                            Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                            client.updateShipboard(shipboard);
                        } else tui.print("error updating your shipboard");
                        tui.printHelpForCurrentState();
                    } else {
                        tui.print(((ErrorMessage) response).getErrorSpecifiy());
                    }
                } else {
                    tui.print("You don't have enough crew");
                }
            } catch (Exception e) {
                tui.print("Error occurred while handling abandoned station card");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.OCCUPY_STATION_OK, MessageType.OCCUPY_STATION_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
