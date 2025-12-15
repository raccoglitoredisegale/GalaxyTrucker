package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadWarzone {

    private final ArrayList<Warzone> warzones;
    Warzone warzone_1 = new Warzone(21148, 1, false, false, new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('T', 'D')),
            new ArrayList<Character>(Arrays.asList('E', 'T')),
            new ArrayList<Character>(Arrays.asList('C', 'C'))))
            , 3, 2, 0, new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("S", "S")),
            new ArrayList<String>(Arrays.asList("S", "S"))))); // GT-cards_I_IT_0116
    Warzone warzone_2 = new Warzone(21149, 1, false, false, new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('C', 'D')),
            new ArrayList<Character>(Arrays.asList('E', 'G')),
            new ArrayList<Character>(Arrays.asList('T', 'C')))), 4, 0, 3, new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("S", "N")),
            new ArrayList<String>(Arrays.asList("S", "W")), new ArrayList<String>(Arrays.asList("S", "E")),
            new ArrayList<String>(Arrays.asList("B", "S"))))); // GT-cards_II_IT_0116

    public LoadWarzone() {
        warzones = new ArrayList<>();
        warzones.add(warzone_1);
        warzones.add(warzone_2);
    }

    public ArrayList<Warzone> getWarzones() {
        return warzones;
    }
}

