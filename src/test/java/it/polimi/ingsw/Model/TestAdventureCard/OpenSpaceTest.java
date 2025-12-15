package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.OpenSpace;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.OpenSpaceCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenSpaceTest {
    OpenSpace openSpace_6 = new OpenSpace(21129, 2, false, false); // GT-cards_II_IT_017


    @Test
    public void OpenSpaceTest() {
        assertEquals(CardType.OPEN_SPACE, openSpace_6.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), openSpace_6.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), openSpace_6.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), openSpace_6.calculatePlayerActionOrder(playerInorder).get(1));

    }

    @Test
    public void OpenSpaceTest2() throws Exception{
        ShipSetupForTest setup = new ShipSetupForTest();
        OpenSpaceCardMessage message = new OpenSpaceCardMessage(MessageType.ASK_SHIP);
        message.setRemovedBatteryFrom(1,2);
        message.setRemovedBatteryFrom(3,2);
        message.setEnginePower(4);

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageOpenSpace(setup.getFlightboard(),setup.getPlayer(), message.getEnginePower(), message.getRemovedBatteryFrom());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(openSpace_6, msg);

        assertEquals(1, setup.getBatteryCompartment_1().getBatteriesAvailable());
        assertEquals(1, setup.getBatteryCompartment_2().getBatteriesAvailable());
        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(10));

    }
}
