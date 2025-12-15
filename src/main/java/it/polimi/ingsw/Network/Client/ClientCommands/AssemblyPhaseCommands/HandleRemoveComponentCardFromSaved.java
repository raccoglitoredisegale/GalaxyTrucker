package it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;

public class HandleRemoveComponentCardFromSaved implements ClientCommand {

    /**
     * The remove command is handled like the draw command,
     * in fact the client picks up the card and decides what to do,
     * which is the same dynamic as taking a card from the deck in the center of the table
     */
    @Override
    public void execute(Client client, String command) throws GenericCommnadException {
        String[] parts = command.split("\\s+");
        ArrayList<ComponentCard> savedCards = client.getShipboard().getSavedComponentCards();

        int index = Integer.parseInt(parts[1]);
        if (parts.length != 2) {
            throw new GenericCommnadException("The command is correct, but the syntax is wrong!" +
                    "\nType 'help' to see available commands.");
        } else if (index < 0 || index >= savedCards.size() || savedCards.get(index) == null) {
            throw new GenericCommnadException("You have no component cards in that position!" +
                    "\nType 'saved' to see your saved component cards.");
        }
        try {
            ComponentCard componentCard = savedCards.get(index);

            TextUserInterface tui = client.getTUI();
            tui.setTUIState(TUIState.WELD_COMPONENT_CARD);
            tui.componentCardDecision(componentCard);
            savedCards.remove(index);
            tui.setTUIState(TUIState.ASSEMBLY_PHASE);

        } catch (Exception e) {
            throw new GenericCommnadException(e.getMessage());
        }
    }
}
