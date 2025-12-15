package it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Exception.ServerCommunicationError;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.JoinLobbyMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleJoinLobby implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        try {
            String[] parts = command.split("\\s+");
            if (parts.length != 2) {
                throw new GenericCommnadException("Syntax error");
            }
            Message message = new JoinLobbyMessage(MessageType.JOIN_LOBBY, parts[1]);
            Message serverResponse = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (serverResponse.getType() == MessageType.JOIN_LOBBY_OK) {
                client.getTUI().print("Lobby " + parts[1] + " joined");
                client.getTUI().setTUIState(TUIState.LOBBY);
                client.setShipboard();
            } else {
                client.getTUI().print("Cannot join lobby.");
                client.getTUI().printHelpForCurrentState();
            }
        } catch (ServerCommunicationError e) {
            e.printStackTrace();
            System.out.println("Error joining the lobby.");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.JOIN_LOBBY_OK, MessageType.JOIN_LOBBY_KO)
        );
    }
}
