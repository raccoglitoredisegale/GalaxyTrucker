package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Dim_Dir;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Epidemic;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpidemicTest {
    Epidemic epidemic_1 = new Epidemic(21123, 2, false, false); // GT-cards_II_IT_015

    @Test
    public void epidemicTest() {
        assertEquals(CardType.EPIDEMIC, epidemic_1.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        ArrayList<Player> emptyList = new ArrayList<>();
        assertEquals(emptyList.size(), epidemic_1.calculatePlayerActionOrder(playerInorder).size());
        assertEquals("""
				Type: Epidemic ☣️
				• A deadly virus spreads through your ship.
				• You lose one crew member for each connected module.
				""", epidemic_1.getEffectDescription());

        Dim_Dir dimDir = new Dim_Dir("B", "N");
        assertEquals("B", dimDir.getDimension());
        assertEquals("N", dimDir.getDirection());

    }

    @Test
    public void epidemicTest2() throws Exception {
        ShipSetupForTest setup = new ShipSetupForTest();



        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageEpidemic(setup.getPlayer());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(epidemic_1, msg);

        assertEquals(1, setup.getStr().getOccupantNumber());
        assertEquals(1, setup.getCabin1().getOccupantNumber());
        assertEquals(1, setup.getCabin2().getOccupantNumber());
        assertEquals(true, epidemic_1.getIsCompleted());



    }


}
