package it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands;

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

public class HandleStartCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        TextUserInterface tui = client.getTUI();
        if (command.equals("start")) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.START_GAME), getExpectedResponseMessageTypes());
                if (response.getType() == MessageType.START_GAME_OK) {
                    tui.print("Game started");
                    tui.setTUIState(TUIState.ASSEMBLY_PHASE);
                    tui.printHelpForCurrentState();
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("Error starting game");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.START_GAME_OK, MessageType.START_GAME_KO)
        );
    }
}
