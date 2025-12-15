package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;

public class Planets extends AdventureCard {

    private final ArrayList<ArrayList<Goods>> planetsList = new ArrayList<>();

    private final ArrayList<Integer> occupedPlanets = new ArrayList<>();

    private final int daysLost;

    public Planets(int id, int level, boolean turnedUp, boolean completed, ArrayList<ArrayList<Goods>> planetsList,
                   int daysLost) {
        super(id, level, turnedUp, completed);
        this.planetsList.addAll(planetsList);
        this.daysLost = daysLost;
        for (int i = 0; i < planetsList.size(); i++) {
            this.occupedPlanets.add(0); // [0,0,0,0] avrò tanti zeri quanti sono i pianeti all'interno di planetsList
            // lo 0 indica che il pianeta non è stato occupato
        }
        setHasActionOrder(true);
    }

    public ArrayList<ArrayList<Goods>> getPlanetsList() {
        return planetsList;
    }

    public ArrayList<Integer> getOccupedPlanets() {
        return occupedPlanets;
    }

    public void setOccupedPlanets(int idx) {
        this.occupedPlanets.set(idx, 1);
    }

    public int getDaysLost() {
        return daysLost;
    }

    @Override
    public CardType getCardType() {
        return CardType.PLANETS;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) {
        visitor.visit(this, msg);
    }

    /**
     * @return lista di pianeti con ciò che contengono
     */
    @Override
    public String toString() {
        StringBuilder planets = new StringBuilder();
        for (int i = 0; i < planetsList.size(); i++) {
            planets.append("Pianeta ").append(i).append(": ").append(planetsList.get(i));
        }
        return planets.toString();
    }

    @Override
    public String getEffectDescription() {
        StringBuilder description = new StringBuilder();
        description.append("Type: Planets 🪐\n");
        description.append("• You discover multiple planets, each offering different goods.\n");
        description.append("• Each player may choose one planet to land on and collect its goods.\n");
        description.append("• Landing costs time: you lose ").append(daysLost).append(" days of travel.\n\n");

        description.append("Planets discovered:\n");
        for (int i = 0; i < planetsList.size(); i++) {
            description.append("  - Planet ").append(i + 1).append(" contains: ");
            if (planetsList.get(i).isEmpty()) {
                description.append("no goods");
            } else {
                for (int j = 0; j < planetsList.get(i).size(); j++) {
                    description.append(planetsList.get(i).get(j));
                    if (j != planetsList.get(i).size() - 1) {
                        description.append(", ");
                    }
                }
            }
            if (occupedPlanets.get(i) == 1) {
                description.append(" [already occupied]");
            }
            description.append("\n");
        }

        return description.toString();
    }

}
