package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class OpenSpace extends AdventureCard {

    public OpenSpace(int id, int level, boolean turnedUp, boolean completed) {
        super(id, level, turnedUp, completed);
        setHasActionOrder(true);
    }

    @Override
    public CardType getCardType() {
        return CardType.OPEN_SPACE;
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
    public String getEffectDescription() {
        return "Type: Open Space 🌌\n" + "• You have entered the vastness of open space, where you can travel freely.\n"
                + "• No immediate threats or obstacles lie ahead.\n"
                + "• You may use this opportunity to advance or explore at your own pace.\n"
                + "• Stay cautious, though, as danger can appear at any moment in deep space!";
    }

}
