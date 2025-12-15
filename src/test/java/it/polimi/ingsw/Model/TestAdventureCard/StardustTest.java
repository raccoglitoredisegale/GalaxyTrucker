package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Stardust;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StardustTest {

    Stardust stardust_2 = new Stardust(21147, 2, false, false); // GT-cards_II_IT_014


    @Test
    public void stardustTest() {
        assertEquals(CardType.STARDUST, stardust_2.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), stardust_2.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(1), stardust_2.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(0), stardust_2.calculatePlayerActionOrder(playerInorder).get(1));

    }

    @Test
    public void stardustTest2() throws Exception{
        ShipSetupForTest setup = new ShipSetupForTest();

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageStarDust(setup.getPlayer(),setup.getFlightboard());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(stardust_2, msg);
        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(2));
    }
}
