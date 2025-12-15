package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdventureCardtest {
    AdventureCard adventureCard = new AdventureCard(1, 1, false, false) {
        @Override
        public CardType getCardType() {
            return null;
        }

        @Override
        public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
            return null;
        }

        @Override
        public String getEffectDescription() {
            return "";
        }
    };

    @Test
    public void AdventureCardTest() {
        adventureCard.setCompleted(true);
        adventureCard.setAlreadyShown(true);
        assertEquals(true, adventureCard.getIsCompleted());
        assertEquals(true, adventureCard.getAlreadyShown());
        assertEquals(false, adventureCard.getHasActionOrder());
        assertEquals(1, adventureCard.getID());
        assertEquals(1, adventureCard.getLevel());

    }
}
