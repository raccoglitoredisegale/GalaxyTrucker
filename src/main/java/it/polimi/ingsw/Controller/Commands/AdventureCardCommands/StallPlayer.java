package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageStall;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.StallMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Collections;

public class StallPlayer implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        try {
            if (gc.getCurrentAdventureCard() == null) {
                gc.addStalledPlayer(handler.getPlayer());
                handler.sendToClient(new Message(MessageType.STALL_PLAYER_OK));
                StallMessage serverResponse = new StallMessage(MessageType.STALL_PLAYER_OK, handler.getPlayer().getUsername());
                gc.sendBroadCastMessage(new BroadcastMessageStall(MessageType.BROADCAST, MessageType.STALL_PLAYER_OK, serverResponse),
                        new ArrayList<Player>(Collections.singletonList(handler.getPlayer())));
                gc.checkGameEnded();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            handler.sendToClient(new Message(MessageType.STALL_PLAYER_KO));

        }

    }
}
