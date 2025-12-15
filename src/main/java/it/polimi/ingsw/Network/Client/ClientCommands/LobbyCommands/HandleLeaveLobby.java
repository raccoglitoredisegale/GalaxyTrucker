package it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Exception.ServerCommunicationError;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleLeaveLobby implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (command.split("\\s+").length > 1) {
            throw new GenericCommnadException("Invalid syntax");
        }
        try {
            Message message = new Message(MessageType.LEAVE_GAME);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());

            if (response.getType() == MessageType.LEAVE_GAME_OK) {
                System.out.println("LOBBY LEAVED !!!\n\t");
                client.getTUI().setTUIState(TUIState.PRE_LOBBY);
            } else if (response.getType() == MessageType.LEAVE_GAME_KO) {
                throw new GenericCommnadException(((ErrorMessage) message).getErrorSpecifiy());
            }
        } catch (ServerCommunicationError e) {
            e.printStackTrace();
            System.out.println("Failed command 'lobbies'");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.LEAVE_GAME_OK, MessageType.LEAVE_GAME_KO)
        );
    }
}
