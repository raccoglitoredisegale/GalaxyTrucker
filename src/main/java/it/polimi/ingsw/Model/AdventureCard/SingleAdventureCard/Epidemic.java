package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;

public class Epidemic extends AdventureCard {

    public Epidemic(int id, int level, boolean turnedUp, boolean completed) {
        super(id, level, turnedUp, completed);
        setHasActionOrder(false);
    }

    @Override
    public CardType getCardType() {
        return CardType.EPIDEMIC;
    }

    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> emptyList = new ArrayList<>();
        return emptyList;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) {
        visitor.visit(this, msg); // msg usa ShipBoard
    }

    @Override
    public String getEffectDescription() {
        return """
                Type: Epidemic ☣️
                • A deadly virus spreads through your ship.
                • You lose one crew member for each connected module.
                """;
    }

}
