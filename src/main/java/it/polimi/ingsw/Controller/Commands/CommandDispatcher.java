package it.polimi.ingsw.Controller.Commands;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.Map;

public class CommandDispatcher {

    private final Map<MessageType, ServerControllerCommand> commandMap = CommandRegistry.getCommands();

    public void dispatch(Message message, ClientHandler handler) throws Exception {
        ServerControllerCommand command = commandMap.get(message.getType());

        if (command != null) {
            command.execute(handler, message); // esegui il comando con gli argomenti args
        } else {
            System.out.println("Unknown command: " + message.getType()); // comando non riconosciuto
        }
    }

}
