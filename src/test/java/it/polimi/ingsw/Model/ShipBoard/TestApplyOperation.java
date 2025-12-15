package it.polimi.ingsw.Model.ShipBoard;

import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApplyOperation {
    @Test
    public void testApplyOperation() throws Exception{
        ShipSetupForTest ship = new ShipSetupForTest();
        OperationVisitor op = new ApplyOperation();
        assertEquals(6, ship.getShipboard().acceptCalculateCrew(op));
        assertEquals(4, ship.getShipboard().acceptCalculateBattery(op));
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
        ship.getShipboard().acceptManageGoods(op, goodsMap);
        assertEquals(9, ship.getShipboard().acceptCalculateGoods(op));

    }
}
