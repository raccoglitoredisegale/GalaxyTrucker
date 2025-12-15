package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Controller.Exceptions.DeckException;
import it.polimi.ingsw.Controller.LoadElements.LoadComponentCards;
import it.polimi.ingsw.Model.ComponentCard.ComponentCardDeck;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.Player.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentCardDecTest {

    ComponentCardDeck deck = new ComponentCardDeck();
    LoadComponentCards ldc = new LoadComponentCards();
    ArrayList<String> componentCardIds = ldc.getIdsOfComponentCard();


    ArrayList<Cabin> startingCabins = ldc.getStartingCabins();

    @Test
    public void testCompCardDeck() throws DeckException {
        try {
            String id = deck.getCardID();
            String id2 = id;
            deck.updateRevealedCard(id);

            assertEquals(startingCabins.get(0).getId(), deck.getStartingCabin(Color.BLUE).getId());
            assertEquals(startingCabins.get(1).getId(), deck.getStartingCabin(Color.GREEN).getId());
            assertEquals(startingCabins.get(2).getId(), deck.getStartingCabin(Color.RED).getId());
            assertEquals(startingCabins.get(3).getId(), deck.getStartingCabin(Color.YELLOW).getId());

            assertEquals(id2, deck.getRevealedCard().getFirst());

            assertEquals(id2, deck.getComponentCardFromRevealedDeck(0));
        } catch (DeckException e) {
            System.out.println("exception");
        }
    }

    @Test
    public void testCompCardDeck2() throws DeckException {
        try {
            String id = deck.getCardID();
            String id2 = id;
            deck.updateRevealedCard(id);

            assertEquals(id2, deck.getRevealedCard().getFirst());

            assertEquals(id2, deck.getComponentCardFromRevealedDeck(-1));
        } catch (DeckException e) {
            System.out.println("exception");
        }


    }
}
