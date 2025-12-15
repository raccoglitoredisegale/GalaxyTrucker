package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class AbandonedShip extends AdventureCard {

    int crewLost;

    int daysLost;

    int creditsGained;

    public AbandonedShip(int id, int level, boolean turnedUp, boolean completed, int crewLost, int daysLost,
                         int creditsGained) {
        super(id, level, turnedUp, completed);
        this.crewLost = crewLost;
        this.daysLost = daysLost;
        this.creditsGained = creditsGained;
        setHasActionOrder(true);
    }

    public int getCrewLost() {
        return crewLost;
    }

    public int getDaysLost() {
        return daysLost;
    }

    @Override
    public CardType getCardType() {
        return CardType.ABANDONED_SHIP;
    }

    public int getCreditsGained() {
        return creditsGained;
    }

    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
        visitor.visit(this, msg);
    } // msg contains player and flightboard

    @Override
    public String getEffectDescription() {
        String sb = "Type: Abandoned Ship 🛰️\n" +
                "• Lost crew members: " + crewLost + "\n" +
                "• Days lost: " + daysLost + "\n" +
                "• Credits gained: " + creditsGained + "\n";
        return sb;
    }

}
