package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleFinished implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("finished")) {
            throw new GenericCommnadException("Invalid syntax!");
        }
        try {
            Message message = new Message(MessageType.FINISHED_ASSEMBLY_PHASE);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.FINISHED_ASSEMBLY_PHASE_KO) {
                throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
            } else {
                client.getTUI().print("You've now finished the assembly phase!");
                client.getTUI().setTUIState(TUIState.PRE_FLIGHT_PHASE);
                client.getTUI().printHelpForCurrentState();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.FINISHED_ASSEMBLY_PHASE_OK, MessageType.FINISHED_ASSEMBLY_PHASE_KO)
        );
    }
}
