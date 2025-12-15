package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.DiscardComponentCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleDiscardComponentCard implements ClientCommand {

    /**
     * The correctness of the command has already been checked in the tutorial
     */
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        String[] parts = command.split("\\s+");
        Message message = new DiscardComponentCardMessage(MessageType.DISCARD_COMPONENT_CARD_DRAWN, parts[1]);
        try {
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.DISCARD_COMPONENT_CARD_DRAWN_KO) {
                throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
            } else {
                client.getTUI().setTUIState(TUIState.ASSEMBLY_PHASE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.DISCARD_COMPONENT_CARD_DRAWN_OK, MessageType.DISCARD_COMPONENT_CARD_DRAWN_KO)
        );
    }
}
