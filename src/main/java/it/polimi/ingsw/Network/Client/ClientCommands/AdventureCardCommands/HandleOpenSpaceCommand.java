package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.OpenSpaceCardMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleOpenSpaceCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();

        try {
            if (command.equals("apply")) {
                OpenSpaceCardMessage openSpaceCardMessage = tui.handleOpenSpace(client);
                Message response = client.sendMessageAndWaitResponse(openSpaceCardMessage, getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.APPLY_OPEN_SPACE_OK) {
                    tui.print("open space has been applied");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage2());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                } else {
                    tui.print("error applying open space");
                }
            } else tui.print("Syntax error for command: " + command);
        } catch (Exception e) {
            tui.print("Error occurred while handling open space");
            tui.printHelpForCurrentState();
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.APPLY_OPEN_SPACE_OK, MessageType.APPLY_OPEN_SPACE_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
