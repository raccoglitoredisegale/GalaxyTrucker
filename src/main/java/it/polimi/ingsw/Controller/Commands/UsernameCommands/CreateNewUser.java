package it.polimi.ingsw.Controller.Commands.UsernameCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.NewUserMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class CreateNewUser implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) {
        try {
            String username = ((NewUserMessage) message).getUsername();
            SystemController sc = SystemController.getInstance();
            if (sc.checkUsername(username)) {
                handler.sendToClient(new Message(MessageType.USERNAME_KO));
            } else {
                sc.addUsername(username);
                Player player = new Player(username);
                handler.setPlayer(player);
                handler.sendToClient(new Message(MessageType.USERNAME_OK));
                System.out.println(username + " is now connected to the server!");
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("Create new user error");
        }
    }
}
