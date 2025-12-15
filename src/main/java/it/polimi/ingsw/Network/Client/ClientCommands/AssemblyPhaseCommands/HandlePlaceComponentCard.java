package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.ComponentCardPlacedMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandlePlaceComponentCard implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        try {
            String[] parts = command.split("\\s+");
            ComponentCard componentCard = client.getClientGameController().getComponentCard(parts[3]);
            int x = Integer.parseInt(parts[1]) - 5;
            int y = Integer.parseInt(parts[2]) - 4;
            client.getShipboard().addComponent(
                    componentCard, x, y);
            Message message = new ComponentCardPlacedMessage(MessageType.COMPONENT_CARD_PLACED, componentCard, x, y);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.COMPONENT_CARD_PLACED_KO) {
                throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
            } else {
                // After I welded the component I have to return to the assembly phase in order to perform all the commands in
                client.getTUI().setTUIState(TUIState.ASSEMBLY_PHASE);
            }
        } catch (Exception e) {
            throw new GenericCommnadException(e.getMessage());
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.COMPONENT_CARD_PLACED_OK, MessageType.COMPONENT_CARD_PLACED_KO)
        );
    }
}
