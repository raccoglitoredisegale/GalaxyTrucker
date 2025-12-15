package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Slavers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlaversTest {
    Slavers slavers_2 = new Slavers(21114, 2, false, false, 7, 4, 2, 8); // GT-cards_II_IT_01

    @Test
    public void slavers_test() {
        assertEquals(2, slavers_2.getDaysLost());
        assertEquals(7, slavers_2.getSlaversCannonPower());
        assertEquals(8, slavers_2.getCreditsGained());
        assertEquals(4, slavers_2.getCrewLost());

        slavers_2.setControlled(true);
        assertEquals(true, slavers_2.isControlled());

        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), slavers_2.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), slavers_2.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), slavers_2.calculatePlayerActionOrder(playerInorder).get(1));
    }

    @Test
    public void slavers_test2() throws Exception{
        ShipSetupForTest setup = new ShipSetupForTest();


        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageSlavers(setup.getPlayer(), setup.getFlightboard());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(slavers_2, msg);

        assertEquals(8, setup.getPlayer().getCredits());
        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(4));

    }

}
