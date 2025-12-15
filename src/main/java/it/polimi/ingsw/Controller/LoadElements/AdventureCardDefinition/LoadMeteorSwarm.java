package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.MeteorSwarm;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadMeteorSwarm {

    private final ArrayList<MeteorSwarm> meteorSwarms;
    MeteorSwarm meteorSwarm_1 = new MeteorSwarm(21131, 1, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('B', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'W')),
                    new ArrayList<Character>(Arrays.asList('S', 'E'))))); // GT-cards_I_IT_019
    MeteorSwarm meteorSwarm_2 = new MeteorSwarm(21132, 1, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'W')),
                    new ArrayList<Character>(Arrays.asList('S', 'E')),
                    new ArrayList<Character>(Arrays.asList('S', 'S'))))); // GT-cards_I_IT_0110
    MeteorSwarm meteorSwarm_3 = new MeteorSwarm(21133, 1, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('B', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('B', 'N'))))); // GT-cards_I_IT_0111
    MeteorSwarm meteorSwarm_4 = new MeteorSwarm(21134, 2, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('B', 'W')),
                    new ArrayList<Character>(Arrays.asList('S', 'W')),
                    new ArrayList<Character>(Arrays.asList('S', 'W'))))); // GT-cards_II_IT_019
    MeteorSwarm meteorSwarm_5 = new MeteorSwarm(21135, 2, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('B', 'N')),
                    new ArrayList<Character>(Arrays.asList('B', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'S')),
                    new ArrayList<Character>(Arrays.asList('S', 'S'))))); // GT-cards_II_IT_0110
    MeteorSwarm meteorSwarm_6 = new MeteorSwarm(21136, 2, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('B', 'E')),
                    new ArrayList<Character>(Arrays.asList('S', 'E')),
                    new ArrayList<Character>(Arrays.asList('S', 'E'))))); // GT-cards_II_IT_0111

    public LoadMeteorSwarm() {
        this.meteorSwarms = new ArrayList<>();
        this.meteorSwarms.add(meteorSwarm_1);
        this.meteorSwarms.add(meteorSwarm_2);
        this.meteorSwarms.add(meteorSwarm_3);
        this.meteorSwarms.add(meteorSwarm_4);
        this.meteorSwarms.add(meteorSwarm_5);
        this.meteorSwarms.add(meteorSwarm_6);
    }

    public ArrayList<MeteorSwarm> getMeteorSwarms() {
        return meteorSwarms;
    }
}
