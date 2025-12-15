package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedStation;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbandonedStationTest {

    AbandonedStation abandoned_station4 = new AbandonedStation(21122, 2, false, false, 8, 2,
            new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.YELLOW, Goods.GREEN))); // GT-cards_II_IT_0120


    @Test
    public void abandonedStationTest() {

        assertEquals(8, abandoned_station4.getCrewNeeded());
        assertEquals(2, abandoned_station4.getDaysLost());
        assertEquals(CardType.ABANDONED_STATION, abandoned_station4.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), abandoned_station4.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), abandoned_station4.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), abandoned_station4.calculatePlayerActionOrder(playerInorder).get(1));

       ArrayList<Goods> goods = new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.YELLOW, Goods.GREEN));
       assertEquals(goods.getFirst(), abandoned_station4.getGoodsList().get(0));

    }

    @Test
    public void abandonedStationTest2() throws Exception {
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
        msg.MessageAbandonedStation(setup.getPlayer(), setup.getFlightboard(), goodsMap);
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(abandoned_station4, msg);

        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(4));
        assertEquals(true, abandoned_station4.getIsCompleted());
        assertEquals(2, setup.getHold1().getCurrentCargo());
        assertEquals(1, setup.getHold3().getCurrentCargo());

        assertEquals(Goods.YELLOW, setup.getHold1().getLoad().getFirst() );
        assertEquals(Goods.GREEN, setup.getHold1().getLoad().get(1));
        assertEquals(Goods.RED, setup.getHold3().getLoad().get(0));
    }
}

