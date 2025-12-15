package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.View.ModelElementPrinter;

public class HandleShowAdventureCard implements ClientCommand {
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        if (command.equals("card")) {
            if (client.getClientGameController().getCurrentAdventureCard() != null) {
                AdventureCard card = client.getClientGameController().getCurrentAdventureCard();
                ModelElementPrinter printer = new ModelElementPrinter();
                printer.printCard(card);
            } else client.getTUI().print("there isn't an active card");
        } else {
            throw new GenericCommnadException("Invalid syntax");
        }
    }
}
