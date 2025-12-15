package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Stardust;

import java.util.ArrayList;

public class LoadStardust {

    private final ArrayList<Stardust> stardusts;
    Stardust stardust_1 = new Stardust(21146, 1, false, false); // GT-cards_I_IT_014
    Stardust stardust_2 = new Stardust(21147, 2, false, false); // GT-cards_II_IT_014

    public LoadStardust() {
        stardusts = new ArrayList<>();
        stardusts.add(stardust_1);
        stardusts.add(stardust_2);
    }

    public ArrayList<Stardust> getStardusts() {
        return stardusts;
    }
}
