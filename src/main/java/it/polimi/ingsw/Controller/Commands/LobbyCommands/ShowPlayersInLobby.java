package it.polimi.ingsw.Controller.Commands.LobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.LobbyMessage.ShowPlayers;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class ShowPlayersInLobby implements ServerControllerCommand {

    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        try {
            SystemController sc = SystemController.getInstance();
            GameController gc = sc.getGameController(handler.getPlayer().getGameID());
            Message serverResponse = new ShowPlayers(MessageType.SHOW_PLAYERS_IN_LOBBY_OK, gc.getPlayerListToString());
            handler.sendToClient(serverResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Message serverResponse = new Message(MessageType.SHOW_PLAYERS_IN_LOBBY_KO);
            handler.sendToClient(serverResponse);
        }
    }
}
