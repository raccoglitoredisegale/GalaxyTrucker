package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.TakeComponentCardFromRevealedDeckMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.ComponentCardMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleTakeComponentCardFromRevealedDeck implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        String[] parts = command.split("\\s+");
        if (parts.length != 2) {
            throw new GenericCommnadException("Invalid syntax!");
        }
        Message message = new TakeComponentCardFromRevealedDeckMessage(MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK,
                Integer.parseInt(parts[1]));
        TextUserInterface tui = client.getTUI();
        try {
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK_OK) {
                String componentCardId = ((ComponentCardMessage) response).getComponentCardId();
                tui.setTUIState(TUIState.WELD_COMPONENT_CARD);
                tui.print("id card: " + componentCardId);
                tui.componentCardDecision(client.getClientGameController().getComponentCard(componentCardId));
                tui.setTUIState(TUIState.ASSEMBLY_PHASE);
            } else {
                tui.print(((ErrorMessage) response).getErrorSpecifiy());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK_OK, MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK_KO)
        );
    }
}
