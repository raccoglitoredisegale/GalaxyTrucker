package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleSkipAdventureCard implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        TextUserInterface tui = client.getTUI();
        AdventureCard card = client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("skip")) {
            if ((card.getCardType() == CardType.PIRATES && !client.getClientGameController().getIsDefeated())
                    || (card.getCardType() == CardType.SLAVERS && !client.getClientGameController().getIsDefeated())
                    || (card.getCardType() == CardType.SMUGGLERS && !client.getClientGameController().getIsDefeated())) {
                tui.print("You cannot skip " + card.getCardType().name() + " before the fight!");
                return;
            }
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.SKIP_ADVENTURE_CARD), getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.SKIP_ADVENTURE_CARD_OK) {
                    tui.print("card skipped");
                    tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                } else if (response.getType() == MessageType.ADVENTURE_CARD_COMPLETED) {
                    tui.print("card skipped and completed");
                    tui.setTUIState(TUIState.ADVENTURE_CARD_DRAW);
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("Error skipping the card");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SKIP_ADVENTURE_CARD_OK, MessageType.SKIP_ADVENTURE_CARD_KO, MessageType.ADVENTURE_CARD_COMPLETED)
        );
    }
}
