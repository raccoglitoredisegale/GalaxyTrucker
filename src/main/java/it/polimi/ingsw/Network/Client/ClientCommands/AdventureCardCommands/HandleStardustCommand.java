package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleStardustCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();

        try {
            if (command.equals("apply")) {
                Message message = new Message(MessageType.APPLY_STARDUST);
                Message response = client.sendMessageAndWaitResponse(message, getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.APPLY_STARDUST_OK) {
                    tui.print("stardust has been applied");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage2());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                        System.out.println("Shipboard updated");
                    } else tui.print("error updating your shipboard");
                    tui.printHelpForCurrentState();
                } else {
                    tui.print("Error applying stardust from the server");
                }
            } else tui.print("Syntax error for command: " + command);
        } catch (Exception e) {
            tui.print("Error occurred while handling stardust");
            tui.printHelpForCurrentState();
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.APPLY_STARDUST_OK, MessageType.APPLY_STARDUST_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
