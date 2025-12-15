package it.polimi.ingsw.Model.AdventureCard;

import it.polimi.ingsw.Controller.LoadElements.LoadAdventureCards;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;

import java.util.ArrayList;
import java.util.Collections;

public class DeckManager {
    private final ArrayList<Integer> deck1;
    private final ArrayList<Integer> deck2;
    private final ArrayList<Integer> deck3;
    private final ArrayList<Integer> deck4;

    private final ArrayList<Integer> mainDeck;

    private final ArrayList<AdventureCard> allAdvCards;

    public DeckManager() {
        deck1 = new ArrayList<>();
        deck2 = new ArrayList<>();
        deck3 = new ArrayList<>();
        deck4 = new ArrayList<>();

        LoadAdventureCards lac = new LoadAdventureCards();
        ArrayList<Integer> l1 = lac.getIdsOfAdventureCardCardL1();
        ArrayList<Integer> l2 = lac.getIdsOfAdventureCardCardL2();
        Collections.shuffle(l1);
        Collections.shuffle(l2);

        deck1.add(l2.get(0));
        deck1.add(l2.get(1));
        deck2.add(l2.get(2));
        deck2.add(l2.get(3));
        deck3.add(l2.get(4));
        deck3.add(l2.get(5));
        deck4.add(l2.get(6));
        deck4.add(l2.get(7));

        deck1.add(l1.get(0));
        deck2.add(l1.get(1));
        deck3.add(l1.get(2));
        deck4.add(l1.get(3));

        mainDeck = new ArrayList<>();
        mainDeck.addAll(deck1);
        mainDeck.addAll(deck2);
        mainDeck.addAll(deck3);
        mainDeck.addAll(deck4);
        Collections.shuffle(mainDeck);

        allAdvCards = new ArrayList<>();
        allAdvCards.addAll(lac.getAbandonedShips());
        allAdvCards.addAll(lac.getAbandonedStations());
        allAdvCards.addAll(lac.getEpidemics());
        allAdvCards.addAll(lac.getMeteorSwarms());
        allAdvCards.addAll(lac.getOpenSpaces());
        allAdvCards.addAll(lac.getPirates());
        allAdvCards.addAll(lac.getPlanets());
        allAdvCards.addAll(lac.getSlavers());
        allAdvCards.addAll(lac.getSmugglers());
        allAdvCards.addAll(lac.getStardusts());
        allAdvCards.addAll(lac.getWarzones());
    }

    public ArrayList<Integer> getCheckDeck1() {
        return deck1;
    }

    public ArrayList<Integer> getCheckDeck2() {
        return deck2;
    }

    public ArrayList<Integer> getCheckDeck3() {
        return deck3;
    }

    public ArrayList<Integer> getMainDeck() {
        return mainDeck;
    }

    public AdventureCard drawCard() {
        int id = mainDeck.getFirst();
        for (AdventureCard advCard : allAdvCards) {
            if (advCard.getID() == id) {
                mainDeck.remove(Integer.valueOf(advCard.getID()));
                return advCard;
            }
        }
        return null;

        /// ONLY FOR TEST ///
        /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        Planets planets_1 = new Planets(21138, 1, false, false,
//                new ArrayList<ArrayList<Goods>>(Arrays.asList(
//                        new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
//                        new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW, Goods.BLUE)),
//                        new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
//                        new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN)))),
//                3); // GT-cards_I_IT_0112
//        return planets_1;
    }

}
