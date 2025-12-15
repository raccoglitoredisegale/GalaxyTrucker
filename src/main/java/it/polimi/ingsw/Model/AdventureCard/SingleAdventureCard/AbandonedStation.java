package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class AbandonedStation extends AdventureCard {

    private final int crewNeeded;

    private final int daysLost;

    private final ArrayList<Goods> goodsList = new ArrayList<Goods>();

    public AbandonedStation(int id, int level, boolean turnedUp, boolean completed, int crewNeeded, int daysLost,
                            ArrayList<Goods> goodsList) {
        super(id, level, turnedUp, completed);
        this.crewNeeded = crewNeeded;
        this.daysLost = daysLost;
        this.goodsList.addAll(goodsList);
        setHasActionOrder(true);
    }

    public int getCrewNeeded() {
        return this.crewNeeded;
    }

    public int getDaysLost() {
        return this.daysLost;
    }

    @Override
    public CardType getCardType() {
        return CardType.ABANDONED_STATION;
    }

    public ArrayList<Goods> getGoodsList() {
        ArrayList<Goods> copyOfGoodsList = new ArrayList<Goods>();
        copyOfGoodsList.addAll(this.goodsList);
        return copyOfGoodsList;
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
        return "Numero di merci che puoi acquisire: " + goodsList.size() + "Numero di giorni di volo che perdi: "
                + this.daysLost;
    }

    @Override
    public String getEffectDescription() {
        String sb = "Type: Abandoned Station 🏚️\n" +
                "• Crew needed to explore: " + crewNeeded + "\n" +
                "• Days lost: " + daysLost + "\n" +
                "• Goods acquired: " + goodsList.size() + "\n";
        return sb;
    }

}
