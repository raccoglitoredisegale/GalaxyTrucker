package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleDrawAdventureCard implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        TextUserInterface tui = client.getTUI();
        if (command.equals("draw")) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.DRAW_ADVENTURE_CARD), getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.DRAW_ADVENTURE_CARD_OK) {
                    tui.print("Card drawn successfully");
                } else if (response.getType() == MessageType.DRAW_ADVENTURE_CARD_KO) {
                    tui.print("Can't draw the card");
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("Error occured while drawing card");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.DRAW_ADVENTURE_CARD_OK, MessageType.DRAW_ADVENTURE_CARD_KO)
        );
    }
}
