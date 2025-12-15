package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Stardust extends AdventureCard {

    public Stardust(int id, int level, boolean turnedUp, boolean completed) {
        super(id, level, turnedUp, completed);
        setHasActionOrder(true);
    }

    @Override
    public CardType getCardType() {
        return CardType.STARDUST;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        Collections.reverse(playerActionOrder); // to start from the end
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException { // msg contains List<Player> &
        // FlightBoard
        visitor.visit(this, msg);
    }

    @Override
    public String getEffectDescription() {
        String sb = "Type: Stardust ✨\n" +
                "• You have encountered a field of Stardust!\n" +
                "• This event temporarily boosts your ship’s speed and performance.\n" +
                "• For this turn, you gain an advantage in movement or resource gathering.\n" +
                "• Your ship’s abilities are enhanced for a limited time.\n" +
                "[Exact effects depend on the game mechanics and rules in place for Stardust.]\n";
        return sb;
    }

}
