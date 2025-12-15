package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class MeteorSwarm extends AdventureCard {

    private final ArrayList<ArrayList<Character>> meteorArray = new ArrayList<>(); // ["B","n"],["S","w"]
    // big meteor from nord, small meteor from west
    private final ArrayList<ArrayList<String>> currentMeteors = new ArrayList<>();


    public MeteorSwarm(int id, int level, boolean turnedUp, boolean completed,
                       ArrayList<ArrayList<Character>> meteorArray) {
        super(id, level, turnedUp, completed);
        this.meteorArray.addAll(meteorArray);
        for (ArrayList<Character> innerList : meteorArray) {
            ArrayList<String> convertedList = new ArrayList<>();
            for (Character c : innerList) {
                convertedList.add(String.valueOf(c));
            }
            currentMeteors.add(convertedList);
        }
        setHasActionOrder(false);
    }

    public ArrayList<ArrayList<String>> getCurrentMeteors() {
        return currentMeteors;
    }


    @Override
    public CardType getCardType() {
        return CardType.METEOR_SWARM;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> emptyList = new ArrayList<>();
        return emptyList;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
        visitor.visit(this, msg);
    }

    @Override
    public String getEffectDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: Meteor Swarm 🌠\n");
        sb.append("• A dangerous meteor swarm is headed your way! ⚡\n");
        sb.append("• The meteors are coming from different directions, with varying sizes:\n");

        // Dettaglia ogni meteorite e la sua direzione
        for (ArrayList<Character> meteor : meteorArray) {
            String size = meteor.get(0) == 'B' ? "Big" : "Small";
            String direction = meteor.get(1) == 'n'
                    ? "North"
                    : meteor.get(1) == 's' ? "South" : meteor.get(1) == 'e' ? "East" : "West";

            sb.append("   - " + size + " meteor from the " + direction + ".\n");
        }

        sb.append("• Brace yourself for the impact! The damage could be devastating!\n");

        return sb.toString();
    }

}
