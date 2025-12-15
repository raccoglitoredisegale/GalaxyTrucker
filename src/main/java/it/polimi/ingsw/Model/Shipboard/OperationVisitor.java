package it.polimi.ingsw.Model.Shipboard;

import it.polimi.ingsw.Model.ComponentCard.Goods;

import java.util.ArrayList;
import java.util.Map;

public interface OperationVisitor {

    int visitCalculateCrew(Shipboard shipBoard);

    int visitCalculateBattery(Shipboard shipBoard);

    void visitRemoveBattery(Shipboard shipBoard, ArrayList<ArrayList<Integer>> batteriesList);

    void visitRemoveCrew(Shipboard shipBoard, ArrayList<ArrayList<Integer>> removedCrewFrom);

    int visitCalculateGoods(Shipboard shipBoard);

    void visitManageGoods(Shipboard shipBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap);

    int visitCalculateExposedConnectors(Shipboard shipBoard);

    void visitRemoveGoods(Shipboard shipBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap);
}
