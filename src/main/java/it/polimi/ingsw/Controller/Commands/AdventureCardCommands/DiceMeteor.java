package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.MeteorSwarm;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageMeteor;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.MeteorMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Random;

public class DiceMeteor implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        MeteorSwarm meteorSwarm = (MeteorSwarm) gc.getCurrentAdventureCard();

        if (gc.canThrowDice(handler.getPlayer())) {
            Random rand = new Random();
            int dice1 = rand.nextInt(6) + 1;
            int dice2 = rand.nextInt(6) + 1;
            int dice3 = dice1 + dice2;
            String dice = String.valueOf(dice3);

            ArrayList<String> meteor = meteorSwarm.getCurrentMeteors().getFirst();
            meteorSwarm.getCurrentMeteors().removeFirst();
            gc.clearAffectedPlayers();
            handler.sendToClient(new Message(MessageType.THROW_DICE_OK));

            MeteorMessage serverResponse = new MeteorMessage(MessageType.METEOR_INFO, meteor.getFirst(), meteor.getLast(), dice);
            gc.sendBroadCastMessage(new BroadcastMessageMeteor(MessageType.BROADCAST, MessageType.METEOR_INFO, serverResponse),
                    new ArrayList<Player>());
        } else {
            Message koResponse = new Message(MessageType.THROW_DICE_KO);
            handler.sendToClient(koResponse);
        }

    }

}


