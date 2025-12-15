package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Dim_Dir;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Pirates;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageBlast;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.BlastMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Random;

public class DiceBlast implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        CardType type = gc.getCurrentAdventureCard().getCardType();

        switch (type) {
            case PIRATES: {
                Pirates pirates = (Pirates) gc.getCurrentAdventureCard();

                try {
                    Random rand = new Random();
                    int dice1 = rand.nextInt(6) + 1;
                    int dice2 = rand.nextInt(6) + 1;
                    int dice3 = dice1 + dice2;
                    String dice = String.valueOf(dice3);

                    Dim_Dir blast = pirates.getCurrentShots().getFirst();
                    pirates.removeFirstShot();
                    handler.sendToClient(new Message(MessageType.THROW_DICE_OK));
                    BlastMessage serverResponse = new BlastMessage(MessageType.BLAST_INFO, blast.getDimension(), blast.getDirection(), dice);
                    gc.sendBroadCastMessage(new BroadcastMessageBlast(MessageType.BROADCAST, MessageType.BLAST_INFO, serverResponse),
                            new ArrayList<Player>());
                } catch (Exception e) {
                    Message koResponse = new Message(MessageType.THROW_DICE_KO);
                    handler.sendToClient(koResponse);
                }
                break;
            }

            case WARZONE: {
                Warzone warzone = (Warzone) gc.getCurrentAdventureCard();

                try {
                    Random rand = new Random();
                    int dice1 = rand.nextInt(6) + 1;
                    int dice2 = rand.nextInt(6) + 1;
                    int dice3 = dice1 + dice2;
                    String dice = String.valueOf(dice3);

                    ArrayList<String> blast = warzone.getCurrentBlastArray().getFirst();
                    warzone.getCurrentBlastArray().removeFirst();
                    handler.sendToClient(new Message(MessageType.THROW_DICE_OK));

                    BlastMessage serverResponse = new BlastMessage(MessageType.BLAST_INFO, blast.get(0), blast.get(1), dice);
                    gc.sendBroadCastMessage(new BroadcastMessageBlast(MessageType.BROADCAST, MessageType.BLAST_INFO, serverResponse),
                            new ArrayList<Player>());
                } catch (Exception e) {
                    Message koResponse = new Message(MessageType.THROW_DICE_KO);
                    handler.sendToClient(koResponse);
                }
                break;
            }
        }
    }
}
