package it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Exception.ServerCommunicationError;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.PreLobbyMessage.ShowLobby;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleShowLobbies implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (command.split("\\s+").length > 1) {
            throw new GenericCommnadException("Invalid syntax");
        }
        try {
            Message message = new Message(MessageType.SHOW_LOBBY);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());

            if (response.getType() == MessageType.SHOW_LOBBY_OK) {
                System.out.println(((ShowLobby) response).getLobbies());
            } else if (response.getType() == MessageType.SHOW_LOBBY_KO) {
                throw new GenericCommnadException(((ErrorMessage) message).getErrorSpecifiy());
            }
        } catch (ServerCommunicationError e) {
            e.printStackTrace();
            System.out.println("Failed command 'lobbies'");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SHOW_LOBBY_OK, MessageType.SHOW_LOBBY_KO)
        );
    }
}
