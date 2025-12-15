package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedShip;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.AbandonedShipCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbandonedShipTest {
    AbandonedShip abandonedShip_2 = new AbandonedShip(21116, 1, false, false, 2, 1, 3); // GT-cards_I_IT_0117


    @Test
    public void abandonedShipTest() {

        assertEquals(2, abandonedShip_2.getCrewLost());
        assertEquals(1, abandonedShip_2.getDaysLost());
        assertEquals(3, abandonedShip_2.getCreditsGained());
        assertEquals(CardType.ABANDONED_SHIP, abandonedShip_2.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), abandonedShip_2.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), abandonedShip_2.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), abandonedShip_2.calculatePlayerActionOrder(playerInorder).get(1));

    }

    @Test
    public void testAbandonedShipEffect() throws Exception {
        ShipSetupForTest setup = new ShipSetupForTest();
        AbandonedShipCardMessage message = new AbandonedShipCardMessage(MessageType.ASK_SHIP);
        message.setRemovedCrewFrom(2,2);
        message.setRemovedCrewFrom(2,3);

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageAbandonedShip(setup.getPlayer(), setup.getFlightboard(), message.getRemovedCrewFrom());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(abandonedShip_2, msg);

        assertEquals(1, setup.getCabin1().getOccupantNumber());
        assertEquals(1, setup.getStr().getOccupantNumber());
        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(5));
        assertEquals(true, abandonedShip_2.getIsCompleted());


    }
}
