package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Smugglers extends AdventureCard {

    private final int cannonsPower;

    private final int daysLost;

    private final ArrayList<Goods> goodsList;

    private final int goodsLost;

    private boolean Controlled;


    public Smugglers(int id, int level, boolean turnedUp, boolean completed, int daysLost, int cannonsPower,
                     ArrayList<Goods> goodsList, int goodsLost) {
        super(id, level, turnedUp, completed);
        this.cannonsPower = cannonsPower;
        this.daysLost = daysLost;
        this.goodsList = new ArrayList<>(goodsList);
        this.goodsLost = goodsLost;
        setHasActionOrder(true);
        this.Controlled = false;
    }

    public int getCannonsPower() {
        return this.cannonsPower;
    }

    public int getDaysLost() {
        return this.daysLost;
    }

    public int getGoodsLost() {
        return this.goodsLost;
    }

    public ArrayList<Goods> getGoodsList() {
        ArrayList<Goods> copyOfGoodsList = new ArrayList<Goods>();
        copyOfGoodsList.addAll(this.goodsList);
        return copyOfGoodsList;
    }

    public boolean isControlled() {
        return Controlled;
    }

    public void setControlled(boolean controlled) {
        this.Controlled = controlled;
    }

    @Override
    public CardType getCardType() {
        return CardType.SMUGGLERS;
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
        String sb = "Type: Smugglers 💰\n" +
                "• You encounter a group of smugglers!\n" +
                "• Their cannon power is: " + this.cannonsPower + "\n" +
                "• If your cannon power is equal or higher:\n" +
                "    → You seize their goods: " + goodsList.size() + " item" +
                (goodsList.size() != 1 ? "s" : "") + ".\n" +
                "• If their cannon power is higher:\n" +
                "    → You lose " + this.daysLost + " day" + (this.daysLost != 1 ? "s" : "") +
                " of travel.\n" +
                "[Outcome depends on your ship's total cannon power.]\n";
        return sb;
    }

}
