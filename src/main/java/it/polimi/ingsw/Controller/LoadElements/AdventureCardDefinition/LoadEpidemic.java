package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Epidemic;

import java.util.ArrayList;

public class LoadEpidemic {

    public ArrayList<Epidemic> epidemics;

    Epidemic epidemic_1 = new Epidemic(21123, 2, false, false); // GT-cards_II_IT_015

    public LoadEpidemic() {
        epidemics = new ArrayList<>();
        epidemics.add(epidemic_1);
    }

    public ArrayList<Epidemic> getEpidemics() {
        return epidemics;
    }
}
