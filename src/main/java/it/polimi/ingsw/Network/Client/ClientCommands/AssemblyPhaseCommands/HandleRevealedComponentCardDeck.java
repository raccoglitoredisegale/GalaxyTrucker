package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.RevealedCardDeckMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleRevealedComponentCardDeck implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("revealed")) {
            throw new GenericCommnadException("Invalid syntax!");
        }
        try {
            Message message = new Message(MessageType.GET_COMPONENT_CARD_REVEALED_DECK);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.GET_COMPONENT_CARD_REVEALED_DECK_KO) {
                throw new GenericCommnadException(((ErrorMessage) response).getErrorSpecifiy());
            } else {
                // TODO: da sistemare il print nel ModelElementPrinter in moodo tale che io possa passargli un array di stringhe
                ArrayList<ComponentCard> tempCompCard = new ArrayList<>();
                for (String card : ((RevealedCardDeckMessage) response).getComponentCards()) {
                    tempCompCard.add(client.getClientGameController().getComponentCard(card));
                }
                client.getTUI().getModelElementPrinter().printCardList(tempCompCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.GET_COMPONENT_CARD_REVEALED_DECK_KO, MessageType.GET_COMPONENT_CARD_REVEALED_DECK_OK)
        );
    }
}
