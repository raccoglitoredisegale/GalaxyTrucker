package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ShowFlightBoardMessage;
import it.polimi.ingsw.View.ModelElementPrinter;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleFlightBoard implements ClientCommand {

    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        TextUserInterface tui = client.getTUI();
        // Flightboard flightBoard;
        if (command.equals("flightboard")) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.SHOW_FLIGHT_BOARD), getCreateLobbyTypesMessage());
                if (response.getType() == MessageType.SHOW_FLIGHT_BOARD_OK) {
                    Flightboard flightBoard = ((ShowFlightBoardMessage) response).getFlightBoard();
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.displayFlightboard(flightBoard);
                    tui.print("\n\n\n\n");
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("Error printing flightboard");
                tui.printHelpForCurrentState();
            }
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.SHOW_FLIGHT_BOARD_OK, MessageType.SHOW_FLIGHT_BOARD_KO)
        );
    }
}
