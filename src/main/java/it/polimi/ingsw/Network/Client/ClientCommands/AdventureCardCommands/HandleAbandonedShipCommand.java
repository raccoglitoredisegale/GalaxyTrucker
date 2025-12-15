package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedShip;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.AbandonedShipCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleAbandonedShipCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        OperationVisitor applyOperation = new ApplyOperation();
        TextUserInterface tui = client.getTUI();
        AbandonedShip abandonedShip = (AbandonedShip) client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("occupy")) {
            try {
                if (client.getShipboard().acceptCalculateCrew(applyOperation) >= abandonedShip.getCrewLost()) {
                    AbandonedShipCardMessage abandonedShipCardMessage = tui.handleAbandonedShip(client);
                    Message response = client.sendMessageAndWaitResponse(abandonedShipCardMessage, getCreateLobbyTypesMessage());
                    if (response.getType() == MessageType.OCCUPY_SHIP_OK) {
                        tui.print("Ship occupied");
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
                tui.print("Error occurred while handling abandoned ship card");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.OCCUPY_SHIP_OK, MessageType.OCCUPY_SHIP_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }

}
