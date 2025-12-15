package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Dim_Dir;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.SmugglersLostCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmugglersTest {

    Smugglers smugglers_1 = new Smugglers(21111, 2, false, false, 1, 8, new ArrayList<Goods>( // missing cargo lost
            Arrays.asList(Goods.RED, Goods.YELLOW, Goods.YELLOW)), 3); // GT-cards_II_IT_012


    @Test
    public void smugglersTest() {

        assertEquals(1, smugglers_1.getDaysLost());
        assertEquals(8, smugglers_1.getCannonsPower());
        assertEquals(3, smugglers_1.getGoodsLost());
        smugglers_1.setControlled(true);
        assertEquals(true, smugglers_1.isControlled());

        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), smugglers_1.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), smugglers_1.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), smugglers_1.calculatePlayerActionOrder(playerInorder).get(1));

        ArrayList<Dim_Dir> currentShots = new ArrayList<>();
        currentShots.add(new Dim_Dir("S", "S"));
        currentShots.add(new Dim_Dir("S", "S"));
        currentShots.add(new Dim_Dir("B", "S"));
        ArrayList<Goods> goods = new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW, Goods.YELLOW));
        assertEquals(goods, smugglers_1.getGoodsList());


    }

    @Test
    public void smugglersTest2() throws Exception {
        ShipSetupForTest setup = new ShipSetupForTest();
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<>();
        ArrayList<Integer> coords1 = new ArrayList<>();
        ArrayList<Integer> coords2 = new ArrayList<>();
        coords1.add(1);
        coords1.add(4);
        coords2.add(3);
        coords2.add(4);
        ArrayList<Goods> goods1 = new ArrayList<>();
        ArrayList<Goods> goods2 = new ArrayList<>();
        goods1.add(Goods.YELLOW);
        goods1.add(Goods.GREEN);
        goods2.add(Goods.RED);
        goodsMap.put(coords1, goods1);
        goodsMap.put(coords2, goods2);

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageSmugglers(true,setup.getPlayer(), setup.getFlightboard(), goodsMap, null);
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(smugglers_1, msg);

        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(5));
        assertEquals(2, setup.getHold1().getCurrentCargo());
        assertEquals(1, setup.getHold3().getCurrentCargo());

        assertEquals(Goods.YELLOW, setup.getHold1().getLoad().getFirst() );
        assertEquals(Goods.GREEN, setup.getHold1().getLoad().get(1));
        assertEquals(Goods.RED, setup.getHold3().getLoad().get(0));

        SmugglersLostCardMessage message = new SmugglersLostCardMessage(MessageType.ASK_SHIP);
        message.setRemovedBatteryFrom(1,2);
        message.setRemovedBatteryFrom(3,2);


        AdventureCardVisitorMessage msg2 = new AdventureCardVisitorMessage();
        msg2.MessageSmugglers(false,setup.getPlayer(), setup.getFlightboard(), goodsMap, message.getRemovedBatteryFrom());
        applyEffect.visit(smugglers_1, msg2);

        assertEquals(1, setup.getBatteryCompartment_1().getBatteriesAvailable());
        assertEquals(1, setup.getBatteryCompartment_2().getBatteriesAvailable());
        assertEquals(0, setup.getHold1().getCurrentCargo());
        assertEquals(0, setup.getHold3().getCurrentCargo());
    }
}
