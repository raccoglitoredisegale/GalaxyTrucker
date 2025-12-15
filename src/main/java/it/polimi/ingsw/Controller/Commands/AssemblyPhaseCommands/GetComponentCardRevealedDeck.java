package it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Controller.GameControllers.GameController;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AssemblyPhaseMessage.RevealedCardDeckMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;

public class GetComponentCardRevealedDeck implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) throws Exception {
        SystemController sc = SystemController.getInstance();
        GameController gc = sc.getGameController(handler.getPlayer().getGameID());
        ArrayList<String> copyOfRevealedCard = new ArrayList<>(gc.getComponentCardDeck().getRevealedCard());
        Message response = new RevealedCardDeckMessage(MessageType.GET_COMPONENT_CARD_REVEALED_DECK_OK,
                copyOfRevealedCard
        );
        handler.sendToClient(response);
    }
}
