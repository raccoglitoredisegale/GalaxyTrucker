package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleRotateHourglass implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("rotate")) {
            throw new GenericCommnadException("Invalid syntax!");
        }
        Message message = new Message(MessageType.ROTATE_HOURGLASS);
        Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
        if (response.getType() == MessageType.ROTATE_HOURGLASS_KO) {
            throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
        } else {
            client.getTUI().print("Hourglass rotated!");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ROTATE_HOURGLASS_OK, MessageType.ROTATE_HOURGLASS_KO)
        );
    }
}
