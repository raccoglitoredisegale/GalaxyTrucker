package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Slavers extends AdventureCard {

    private final int slaversCannonPower;

    private final int crewLost;

    private final int daysLost;

    private final int creditsGained;

    private boolean Controlled;

    public Slavers(int id, int level, boolean turnedUp, boolean completed, int slaversCannonPower, int crewLost,
                   int daysLost, int creditsGained) {
        super(id, level, turnedUp, completed);
        this.slaversCannonPower = slaversCannonPower;
        this.crewLost = crewLost;
        this.daysLost = daysLost;
        this.creditsGained = creditsGained;
        setHasActionOrder(true);
        this.Controlled = false;
    }

    public int getSlaversCannonPower() {
        return slaversCannonPower;
    }

    public int getCrewLost() {
        return crewLost;
    }

    public int getDaysLost() {
        return daysLost;
    }

    public int getCreditsGained() {
        return creditsGained;
    }

    public boolean isControlled() {
        return Controlled;
    }

    public void setControlled(boolean controlled) {
        this.Controlled = controlled;
    }

    @Override
    public CardType getCardType() {
        return CardType.SLAVERS;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
        visitor.visit(this, msg);
    }

    @Override
    public String toString() {
        return "Numero di crediti che puoi guadagnare: " + this.creditsGained
                + "\n Numero di giorni di volo che perdi: " + this.daysLost;
    }

    @Override
    public String getEffectDescription() {
        String sb = "Type: Slavers 🚨\n" +
                "• You are intercepted by space slavers!\n" +
                "• Their attack power is: " + this.slaversCannonPower + "\n" +
                "• If your cannon power is equal or higher:\n" +
                "    → You defeat them and gain " + this.creditsGained + " credits.\n" +
                "• If their cannon power is higher:\n" +
                "    → You lose " + this.crewLost + " crew member" + (this.crewLost != 1 ? "s" : "") +
                " and " + this.daysLost + " day" + (this.daysLost != 1 ? "s" : "") +
                " of travel.\n" +
                "[Outcome depends on your ship's total cannon power.]\n";
        return sb;
    }
}
