package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Slavers;

import java.util.ArrayList;

public class LoadSlavers {

    private final ArrayList<Slavers> slavers;
    Slavers slavers_1 = new Slavers(21113, 1, false, false, 6, 3, 1, 5); // GT-cards_I_IT_01
    Slavers slavers_2 = new Slavers(21114, 2, false, false, 7, 4, 2, 8); // GT-cards_II_IT_01

    public LoadSlavers() {
        slavers = new ArrayList<>();
        slavers.add(slavers_1);
        slavers.add(slavers_2);
    }

    public ArrayList<Slavers> getSlavers() {
        return slavers;
    }
}
