package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Dim_Dir;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Pirates;

import java.util.ArrayList;

public class LoadPirates {

    private final ArrayList<Pirates> pirates;

    public LoadPirates() {
        ArrayList<Dim_Dir> shots_1 = new ArrayList<>();
        ArrayList<Dim_Dir> shots_2 = new ArrayList<>();
        shots_1.add(new Dim_Dir("S", "S"));
        shots_1.add(new Dim_Dir("B", "S"));
        shots_1.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("B", "S"));

        Pirates pirates_1 = new Pirates(21136, 1, false, false, 5, 1, shots_1, 4);
        Pirates pirates_2 = new Pirates(21137, 2, false, false, 6, 2, shots_2, 7);

        pirates = new ArrayList<>();
        pirates.add(pirates_1);
        pirates.add(pirates_2);
    }

    public ArrayList<Pirates> getPirates() {
        return pirates;
    }
}
