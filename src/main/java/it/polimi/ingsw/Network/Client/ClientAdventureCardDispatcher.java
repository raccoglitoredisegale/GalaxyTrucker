package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

public class ClientAdventureCardDispatcher {
    private final Client client;
    private final TextUserInterface TUI;

    public ClientAdventureCardDispatcher(Client client, TextUserInterface TUI) {
        this.client = client;
        this.TUI = TUI;
    }

    public void dispatch(AdventureCard adventureCard) {
        switch (adventureCard.getCardType()) {
            case ABANDONED_SHIP -> TUI.setTUIState(TUIState.ADVENTURE_CARD_ABANDONED_SHIP);
            case ABANDONED_STATION -> TUI.setTUIState(TUIState.ADVENTURE_CARD_ABANDONED_STATION);
            case EPIDEMIC -> TUI.setTUIState(TUIState.ADVENTURE_CARD_EPIDEMIC);
            case METEOR_SWARM -> TUI.setTUIState(TUIState.ADVENTURE_CARD_METEOR_SWARM);
            case OPEN_SPACE -> TUI.setTUIState(TUIState.ADVENTURE_CARD_OPEN_SPACE);
            case PIRATES -> TUI.setTUIState(TUIState.ADVENTURE_CARD_PIRATES);
            case PLANETS -> TUI.setTUIState(TUIState.ADVENTURE_CARD_PLANETS);
            case SLAVERS -> TUI.setTUIState(TUIState.ADVENTURE_CARD_SLAVERS);
            case SMUGGLERS -> TUI.setTUIState(TUIState.ADVENTURE_CARD_SMUGGLERS);
            case STARDUST -> TUI.setTUIState(TUIState.ADVENTURE_CARD_STARDUST);
            case WARZONE -> TUI.setTUIState(TUIState.ADVENTURE_CARD_WARZONE);
        }
    }
}
