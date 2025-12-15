package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Planets;
import it.polimi.ingsw.Model.ComponentCard.Goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadPlanets {

    private final ArrayList<Planets> planets;
    Planets planets_1 = new Planets(21138, 1, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN)))),
            3); // GT-cards_I_IT_0112
    Planets planets_2 = new Planets(21139, 1, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.RED)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(List.of(Goods.YELLOW)))),
            2); // GT-cards_I_IT_0113
    Planets planets_3 = new Planets(21140, 1, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(
                    new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.GREEN, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.YELLOW)))),
            3); // GT-cards_I_IT_0114
    Planets planets_4 = new Planets(21141, 1, false, false,
            new ArrayList<ArrayList<Goods>>(
                    Arrays.asList(new ArrayList<Goods>(Arrays.asList(Goods.GREEN, Goods.GREEN)),
                            new ArrayList<Goods>(List.of(Goods.YELLOW)),
                            new ArrayList<Goods>(Arrays.asList(Goods.BLUE, Goods.BLUE, Goods.BLUE)))),
            1); // GT-cards_I_IT_0115
    Planets planets_5 = new Planets(21142, 2, false, false, new ArrayList<ArrayList<Goods>>(Arrays.asList(
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.RED, Goods.RED, Goods.YELLOW)),
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.RED, Goods.GREEN, Goods.GREEN)),
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.BLUE, Goods.BLUE, Goods.BLUE, Goods.BLUE)))), 4); // GT-cards_II_IT_0112
    Planets planets_6 = new Planets(21143, 2, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.RED)),
                    new ArrayList<Goods>(Arrays.asList(Goods.GREEN, Goods.GREEN, Goods.GREEN, Goods.GREEN)))),
            3); // GT-cards_II_IT_0113
    Planets planets_7 = new Planets(21144, 2, false, false,
            new ArrayList<ArrayList<Goods>>(
                    Arrays.asList(new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW)),
                            new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.GREEN, Goods.BLUE)),
                            new ArrayList<Goods>(Arrays.asList(Goods.GREEN, Goods.GREEN)),
                            new ArrayList<Goods>(List.of(Goods.YELLOW)))),
            2); // GT-cards_II_IT_0114
    Planets planets_8 = new Planets(21145, 2, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(
                    new ArrayList<Goods>(Arrays.asList(Goods.GREEN, Goods.GREEN, Goods.GREEN, Goods.GREEN)),
                    new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.YELLOW)),
                    new ArrayList<Goods>(Arrays.asList(Goods.BLUE, Goods.BLUE, Goods.BLUE, Goods.BLUE)))),
            3); // GT-cards_II_IT_0115

    public LoadPlanets() {
        planets = new ArrayList<>();
        planets.add(planets_1);
        planets.add(planets_2);
        planets.add(planets_3);
        planets.add(planets_4);
        planets.add(planets_5);
        planets.add(planets_6);
        planets.add(planets_7);
        planets.add(planets_8);
    }

    public ArrayList<Planets> getPlanets() {
        return planets;
    }
}
