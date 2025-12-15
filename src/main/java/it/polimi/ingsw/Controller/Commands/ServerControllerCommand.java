package it.polimi.ingsw.Controller.Commands;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Server.ClientHandler;

public interface ServerControllerCommand {

    void execute(ClientHandler handler, Message message) throws Exception;
}

// possono essere sia i comandi di comunicazione, chat e gioco
// possiamo usare più dispatcher, es
