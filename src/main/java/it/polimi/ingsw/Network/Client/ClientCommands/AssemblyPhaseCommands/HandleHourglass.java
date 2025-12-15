package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.SeeTimeRemainingHourglassMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class HandleHourglass implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        try {
            if (!command.equals("hourglass")) {
                throw new GenericCommnadException("Invalid syntax!");
            }
            Message message = new Message(MessageType.SEE_TIME_REMAINING);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.SEE_TIME_REMAINING_KO) {
                client.getTUI().print(((ErrorMessage) response).getErrorSpecifiy());
            }
            client.getTUI().print("Time remaining: " +
                    TimeUnit.MILLISECONDS.toSeconds(((SeeTimeRemainingHourglassMessage) response).getTimeRemaining())
                    + " seconds");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SEE_TIME_REMAINING_OK, MessageType.SEE_TIME_REMAINING_KO)
        );
    }
}
