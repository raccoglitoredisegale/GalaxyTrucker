package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.ComponentCard.Goods;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadSmugglers {

    private final ArrayList<Smugglers> smugglers;
    Smugglers smugglers_1 = new Smugglers(21111, 2, false, false, 1, 8, new ArrayList<Goods>( // missing cargo lost
            Arrays.asList(Goods.RED, Goods.YELLOW, Goods.YELLOW)), 3); // GT-cards_II_IT_012
    Smugglers smugglers_2 = new Smugglers(21112, 1, false, false, 1, 4,
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW, Goods.YELLOW)), 2); // GT-cards_I_IT_012

    public LoadSmugglers() {
        smugglers = new ArrayList<>();
        smugglers.add(smugglers_1);
        smugglers.add(smugglers_2);
    }

    public ArrayList<Smugglers> getSmugglers() {
        return smugglers;
    }
}
