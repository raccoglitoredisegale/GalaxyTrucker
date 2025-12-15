package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Exception.GenericCommnadException;

public interface ClientCommand {

    void execute(Client client, String command) throws GenericCommnadException;

}
