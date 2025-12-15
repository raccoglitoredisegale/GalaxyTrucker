package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AdventureCard implements Serializable {

    private final int id;

    private final int level;

    private final boolean turnedUp;

    private boolean completed; // set true when the card is concluded

    private boolean hasActionOrder;

    private boolean alreadyShown;

    public AdventureCard(int id, int level, boolean turnedUp, boolean completed) {
        this.id = id;
        this.level = level;
        this.turnedUp = false;
        this.completed = false;
        this.alreadyShown = false;
    }

    public boolean getIsCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean activated) {
        this.completed = activated;
    }

    public boolean getHasActionOrder() {
        return this.hasActionOrder;
    }

    protected void setHasActionOrder(boolean hasActionOrder) {
        this.hasActionOrder = hasActionOrder;
    }

    public abstract CardType getCardType();

    @Override
    public String toString() {
        return "AdventureCard{" + "id=" + id + ", level=" + level + ", turnedUp=" + turnedUp + ", activated="
                + completed + '}';
    }

    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
    }

    public abstract ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder);

    public abstract String getEffectDescription();

    public int getLevel() {
        return level;
    }

    public int getID() {
        return id;
    }

    public boolean getAlreadyShown() {
        return alreadyShown;
    }

    public void setAlreadyShown(boolean alreadyShown) {
        this.alreadyShown = alreadyShown;
    }

}
