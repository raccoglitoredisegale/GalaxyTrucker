package it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands;

import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.ChooseColorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HandleChooseColor implements ClientCommand {

    /**
     * command syntax --> color <type of color>
     *
     * @param client
     * @param command
     */
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        try {
            String[] parts = command.split("\\s+");
            if (parts.length != 2) {
                throw new GenericCommnadException("Invalid syntax");
            }
            String colorStr = parts[1];
            if (!Objects.equals(colorStr, "red") && !Objects.equals(colorStr, "green") && !Objects.equals(colorStr, "blue") && !Objects.equals(colorStr, "yellow")) {
                throw new GenericCommnadException("Invalid color");
            }
            Color color = Color.fromString(colorStr);
            Message message = new ChooseColorMessage(MessageType.CHOOSE_COLOR, color);
            Message response = client.sendMessageAndWaitResponse(message, getExpectedResponseMessageTypes());
            if (response.getType() == MessageType.CHOOSE_COLOR_OK) {
                client.getShipboard().setColor(color);
                client.instanceClientGameController();
                client.getShipboard().addComponent(
                        client.getClientGameController().getStartingCabin(client.getShipboard().getColor()), 2, 3
                );
                System.out.println("Successfully assigned color!");
            } else {
                System.out.println("The color '" + color.getColor() + "' is already taken!");
            }
        } catch (Exception e) {
            client.getTUI().print("Failed to choose color");
        }
    }

    public ArrayList<MessageType> getExpectedResponseMessageTypes() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.CHOOSE_COLOR_OK, MessageType.CHOOSE_COLOR_KO)
        );
    }
}
