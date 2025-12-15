package it.polimi.ingsw.Controller.Commands.LobbyCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Collections;

public class StartGame implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        try {
            GameController gc = sc.getGameController(handler.getPlayer().getGameID());
            if (gc.canStartGame(handler)) {
                handler.sendToClient(new Message(MessageType.START_GAME_OK));

                for (Player player : gc.getPlayers()) {
                    player.setShip(new Shipboard(2));
                    player.getShip().addComponent(
                            gc.getComponentCardDeck().getStartingCabin(player.getColor()), 2, 3
                    );
                }
                gc.sendBroadCastMessage(new BroadcastMessage(MessageType.BROADCAST, MessageType.GAME_STARTED),
                        new ArrayList<Player>(Collections.singletonList(handler.getPlayer())));
            }
        } catch (Exception e) {
            handler.sendToClient(new ErrorMessage(MessageType.START_GAME_KO, e.getMessage()));
        }
    }
}
