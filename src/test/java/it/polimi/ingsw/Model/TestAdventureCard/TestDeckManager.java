package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Controller.LoadElements.LoadAdventureCards;
import it.polimi.ingsw.Model.AdventureCard.DeckManager;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeckManager {


    DeckManager deckManager = new DeckManager();



    @Test
    public void testDeck() {
        ArrayList<Integer> mainDeck = deckManager.getMainDeck();
        ArrayList<Integer> maindeck2 = new ArrayList<>(mainDeck);
        AdventureCard card = deckManager.drawCard();

        assertEquals(maindeck2.getFirst(), card.getID());
        assertEquals(maindeck2.size()-1, mainDeck.size());


    }
}
