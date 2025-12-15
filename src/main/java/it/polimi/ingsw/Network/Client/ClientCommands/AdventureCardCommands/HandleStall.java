package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleStall implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (command.equals("stall")) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.STALL_PLAYER), getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.STALL_PLAYER_OK) {
                    client.getTUI().print("stalled");
                    client.getTUI().setTUIState(TUIState.STALL);
                    client.getTUI().printHelpForCurrentState();
                } else {
                    client.getTUI().print("Error stalling");
                    client.getTUI().printHelpForCurrentState();
                }
            } catch (Exception e) {
                client.getTUI().print("Error stalling");
                client.getTUI().printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.STALL_PLAYER_OK, MessageType.STALL_PLAYER_KO)
        );
    }

}
