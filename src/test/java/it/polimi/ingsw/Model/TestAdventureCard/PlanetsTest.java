package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.CardType;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Planets;
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

public class PlanetsTest {
    Planets planets_1 = new Planets(21138, 1, false, false,
            new ArrayList<ArrayList<Goods>>(Arrays.asList(
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.BLUE, Goods.BLUE, Goods.BLUE)),
                    new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN)))),
            3); // GT-cards_I_IT_0112

    @Test
    public void planetsTest() {

       assertEquals(3, planets_1.getDaysLost());
        assertEquals(CardType.PLANETS, planets_1.getCardType());
        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), planets_1.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), planets_1.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), planets_1.calculatePlayerActionOrder(playerInorder).get(1));

        planets_1.setOccupedPlanets(1);
        assertEquals(planets_1.getOccupedPlanets().get(1), 1);
        assertEquals(planets_1.getOccupedPlanets().get(2), 0);


        ArrayList<Goods> goods = new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.GREEN, Goods.BLUE, Goods.BLUE, Goods.BLUE));
        assertEquals(goods, planets_1.getPlanetsList().get(0));

    }

    @Test
    public void planetsTest2() throws Exception {
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
        msg.MessagePlanets(setup.getPlayer(), setup.getFlightboard(), goodsMap);
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(planets_1, msg);

        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(3));
        assertEquals(2, setup.getHold1().getCurrentCargo());
        assertEquals(1, setup.getHold3().getCurrentCargo());

        assertEquals(Goods.YELLOW, setup.getHold1().getLoad().getFirst() );
        assertEquals(Goods.GREEN, setup.getHold1().getLoad().get(1));
        assertEquals(Goods.RED, setup.getHold3().getLoad().get(0));
    }
}
