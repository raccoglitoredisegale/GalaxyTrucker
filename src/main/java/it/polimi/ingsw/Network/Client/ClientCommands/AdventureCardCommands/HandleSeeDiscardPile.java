package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;

public class HandleSeeDiscardPile implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("pile")) {
            throw new GenericCommnadException("The command is correct, but the syntax is wrong!" +
                    "\nType 'help' to see available commands.");
        }
        if (client.getShipboard().getDiscardedComponentCards().isEmpty()) {
            throw new GenericCommnadException("You don't have any discarded cards!");
        }
        client.getTUI().getModelElementPrinter().printCardList(client.getShipboard().getDiscardedComponentCards());
    }
}
