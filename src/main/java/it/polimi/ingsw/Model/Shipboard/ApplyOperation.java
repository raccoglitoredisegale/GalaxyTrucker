package it.polimi.ingsw.Model.Shipboard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.BatteryCompartment;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Hold;

import java.util.ArrayList;
import java.util.Map;

public class ApplyOperation implements OperationVisitor {

    @Override
    public int visitCalculateCrew(Shipboard shipBoard) {
        Cabin cabin;
        int crew = 0;
        for (Integer[] tile : shipBoard.getCabinList()) { // cabinList contains the coordinates of each cabin in the shipBoard
            cabin = (Cabin) shipBoard.getShipComponent(tile[0],
                    tile[1]); //returns the Cabin type tile in order to calculate the occupants number
            crew = crew + cabin.getOccupantNumber();
        }
        return crew;
    }


    @Override
    public int visitCalculateBattery(Shipboard shipBoard) {
        int totalBatteries = 0;
        BatteryCompartment batteryCompartment;
        for (Integer[] tile : shipBoard.getBatteryList()) {
            batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(tile[0], tile[1]);
            totalBatteries = totalBatteries + batteryCompartment.getBatteriesAvailable();
        }
        return totalBatteries;
    }

    @Override
    public void visitRemoveBattery(Shipboard shipboard, ArrayList<ArrayList<Integer>> batteriesList) {
        for (ArrayList<Integer> battery : batteriesList) {
            BatteryCompartment batteryCompartment = (BatteryCompartment) shipboard.getShipComponent(battery.get(0), battery.get(1));
            batteryCompartment.consumeBattery();
        }
    }

    @Override
    public void visitRemoveCrew(Shipboard shipBoard, ArrayList<ArrayList<Integer>> removedCrewFrom) {
        for (ArrayList<Integer> crew : removedCrewFrom) {
            Cabin cabin = (Cabin) shipBoard.getShipComponent(crew.get(0), crew.get(1));
            cabin.removeCrew();
        }
    }

    @Override
    public int visitCalculateGoods(Shipboard shipboard) {
        int goods = 0;
        for (Integer[] tile : shipboard.getStorageList()) {
            Hold hold = (Hold) shipboard.getShipComponent(tile[0], tile[1]);
            for (Goods good : hold.getLoad()) {
                switch (good) {
                    case Goods.RED -> goods = goods + 4;
                    case Goods.YELLOW -> goods = goods + 3;
                    case Goods.GREEN -> goods = goods + 2;
                    case Goods.BLUE -> goods = goods + 1;
                }
            }
            //goods = goods + hold.getCurrentCargo();
        }
        return goods;
    }

    @Override
    public void visitManageGoods(Shipboard shipboard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        for (ArrayList<Integer> holdCoordinates : goodsMap.keySet()) {
            Hold hold = (Hold) shipboard.getShipComponent(holdCoordinates.get(0), holdCoordinates.get(1));
            hold.clearLoad();
            for (Goods goods : goodsMap.get(holdCoordinates)) {
                hold.goodsLoad(goods);
            }
        }
    }

    @Override
    public int visitCalculateExposedConnectors(Shipboard shipBoard) {
        int exposedConnectors = 0;
        for (ComponentCard[] tempCompCard1 : shipBoard.getShip()) {
            for (ComponentCard tempCompCard2 : tempCompCard1) {
                if (tempCompCard2 != null) {
                    for (Connector tempConnector : tempCompCard2.getConnectors()) {
                        if (tempConnector.isExposed()) {
                            exposedConnectors++;
                        }
                    }
                }
            }
        }
        return exposedConnectors;
    }

    @Override
    public void visitRemoveGoods(Shipboard shipBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        for (ArrayList<Integer> holdCoordinates : goodsMap.keySet()) {
            Hold hold = (Hold) shipBoard.getShipComponent(holdCoordinates.get(0), holdCoordinates.get(1));
            for (Goods good : goodsMap.get(holdCoordinates)) {
                hold.removeGood(good);
            }

        }
    }

}
