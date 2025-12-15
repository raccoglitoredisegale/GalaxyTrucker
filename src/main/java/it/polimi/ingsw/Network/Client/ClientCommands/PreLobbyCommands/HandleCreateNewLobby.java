package it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.CreateNewLobbyMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.List;

public class HandleCreateNewLobby implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        String[] parts = command.split("\\s+");

        if (parts.length != 3) {
            client.getTUI().print("Invalid arguments\n" +
                    "Type 'help' to see available commands.");
            return;
        } else if (parts.length == 3 && Integer.parseInt(parts[2]) < 2 || Integer.parseInt(parts[2]) > 4) {
            client.getTUI().print("Invalid syntax\n" +
                    "Type 'help' to see available commands.");
            return;
        }

        String lobbyName = parts[1];
        int maxPlayers = Integer.parseInt(parts[2]);

        try {
            Message message = new CreateNewLobbyMessage(MessageType.CREATE_NEW_LOBBY, maxPlayers, lobbyName);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());

            if (response.getType() == MessageType.LOBBY_CREATED_OK) {
                client.getTUI().print("Lobby created successfully: " + lobbyName);
                client.getTUI().setTUIState(TUIState.LOBBY);
                client.setShipboard();
            } else {
                System.out.println("Failed to create lobby. Type 'help' to see available commands.");
            }
        } catch (Exception e) {
            System.out.println("HandleCreateNewLobby failed.");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                List.of(MessageType.LOBBY_CREATED_OK)
        );
    }
}
