package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.ShowPlayerShipboardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.OtherPlayerShipboardResponseMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleShowPlayerShipboard implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        String[] parts = command.split("\\s+");
        if (parts.length != 2) {
            throw new GenericCommnadException("The command is correct, but the syntax is wrong!" +
                    "\nType 'help' to see available commands.");
        }
        // TODO da mettere
//        else if (parts[1].equals(client.getUsername())) {
//            throw new GenericCommnadException("To see your shipboard type only shipboard!");
//        }
        Message message = new ShowPlayerShipboardMessage(MessageType.SHOW_OTHER_PLAYER_SHIPBOARD, parts[1]);
        Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
        if (response.getType() == MessageType.SHOW_OTHER_PLAYER_SHIPBOARD_KO) {
            throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
        } else {
            client.getTUI().getModelElementPrinter().printShipboard(
                    ((OtherPlayerShipboardResponseMessage) response).getShipboard()
            );
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SHOW_OTHER_PLAYER_SHIPBOARD_OK, MessageType.SHOW_OTHER_PLAYER_SHIPBOARD_KO)
        );
    }


}
