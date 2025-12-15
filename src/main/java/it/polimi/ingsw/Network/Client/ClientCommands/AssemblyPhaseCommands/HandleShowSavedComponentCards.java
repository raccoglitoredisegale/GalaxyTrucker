package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;

public class HandleShowSavedComponentCards implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("saved")) {
            throw new GenericCommnadException("The command is correct, but the syntax is wrong!" +
                    "\nType 'help' to see available commands.");
        }
        if (client.getShipboard().getSavedComponentCards().isEmpty()) {
            throw new GenericCommnadException("You don't have any saved cards!");
        }
        client.getTUI().getModelElementPrinter().printCardList(client.getShipboard().getSavedComponentCards());
    }
}
