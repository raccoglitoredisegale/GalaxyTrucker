package it.polimi.ingsw.Network.Client.ClientCommands.FinalPhase;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.TUIState;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleExit implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (command.equals("exit")) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.EXIT), getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.EXIT_OK) {
                    client.getTUI().print("╔═══════════════════════════════════════╗\n" +
                            "║         \uD83D\uDE80  MISSION COMPLETE \uD83D\uDE80        ║\n" +
                            "║         Exiting the Game...            ║\n" +
                            "║       Safe travels, Captain! \uD83D\uDEF8        ║\n" +
                            "╚═══════════════════════════════════════╝\n");
                    client.getTUI().setTUIState(TUIState.PRE_LOBBY);
                } else {
                    client.getTUI().print("forcing exit from the game");
                    client.getTUI().printHelpForCurrentState();
                }
            } catch (Exception e) {
                client.getTUI().print("Error exiting the game");
                client.getTUI().printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.EXIT_OK, MessageType.EXIT_KO)
        );
    }
}
