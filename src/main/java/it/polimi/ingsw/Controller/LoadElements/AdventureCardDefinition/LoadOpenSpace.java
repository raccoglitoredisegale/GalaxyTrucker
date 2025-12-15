package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.OpenSpace;

import java.util.ArrayList;

public class LoadOpenSpace {

    private final ArrayList<OpenSpace> openSpaces;
    OpenSpace openSpace_1 = new OpenSpace(21124, 1, false, false); // GT-cards_I_IT_015
    OpenSpace openSpace_2 = new OpenSpace(21125, 1, false, false); // GT-cards_I_IT_016
    OpenSpace openSpace_3 = new OpenSpace(21126, 1, false, false); // GT-cards_I_IT_017
    OpenSpace openSpace_4 = new OpenSpace(21127, 1, false, false); // GT-cards_I_IT_018
    OpenSpace openSpace_5 = new OpenSpace(21128, 2, false, false); // GT-cards_II_IT_016
    OpenSpace openSpace_6 = new OpenSpace(21129, 2, false, false); // GT-cards_II_IT_017
    OpenSpace openSpace_7 = new OpenSpace(21130, 2, false, false); // GT-cards_II_IT_018

    public LoadOpenSpace() {
        openSpaces = new ArrayList<>();
        openSpaces.add(openSpace_1);
        openSpaces.add(openSpace_2);
        openSpaces.add(openSpace_3);
        openSpaces.add(openSpace_4);
        openSpaces.add(openSpace_5);
        openSpaces.add(openSpace_6);
        openSpaces.add(openSpace_7);
    }

    public ArrayList<OpenSpace> getOpenSpaces() {
        return openSpaces;
    }
}
