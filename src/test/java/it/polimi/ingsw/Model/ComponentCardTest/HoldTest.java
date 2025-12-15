package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Hold;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HoldTest {

    @Test
    public void testHold() {
        Hold hold_1 = new Hold("11211", false, false, new Connector[]{
                new Connector(2, true), new Connector(1, true),
                new Connector(3, true), new Connector(0, true),}, 2, false); // GT-new_tiles_16_for web18

        hold_1.goodsLoad(Goods.RED);
        hold_1.goodsLoad(Goods.YELLOW);
        hold_1.goodsLoad(Goods.GREEN);
        hold_1.goodsLoad(Goods.BLUE);
        hold_1.removeGood(Goods.YELLOW);

        List<Goods> goods = Arrays.asList(Goods.GREEN);

        assertEquals(2, hold_1.getCapacity());
        assertEquals(1, hold_1.getCurrentCargo());
        assertEquals(false, hold_1.isSpecial());
        assertEquals(goods.get(0), hold_1.getLoad().get(0));
        assertEquals(1, hold_1.getLoad().size());


    }
}
