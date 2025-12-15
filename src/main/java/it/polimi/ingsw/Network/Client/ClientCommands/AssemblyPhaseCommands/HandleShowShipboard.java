package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;

public class HandleShowShipboard implements ClientCommand {


    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (!command.equals("shipboard")) {
            throw new GenericCommnadException("Invalid syntax!");
        }
        client.getTUI().getModelElementPrinter().printShipboard(client.getShipboard());
    }
}
