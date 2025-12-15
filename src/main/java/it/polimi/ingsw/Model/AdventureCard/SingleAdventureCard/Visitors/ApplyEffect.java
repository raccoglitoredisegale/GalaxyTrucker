package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.*;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplyEffect implements EffectVisitor {

    @Override
    public void visit(Slavers slavers, AdventureCardVisitorMessage msg) throws IOException {
        msg.player.gainCredits(slavers.getCreditsGained());
        msg.flightBoard.loseFlightDays(msg.player, slavers.getDaysLost());
    }

    @Override
    public void visit(AbandonedStation abandonedStation, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        msg.flightBoard.loseFlightDays(msg.player, abandonedStation.getDaysLost());
        msg.player.getShip().acceptManageGoods(applyOperation, msg.goodsMap);
        abandonedStation.setCompleted(true);
    }

    @Override
    public void visit(Pirates pirates, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        if (msg.win) {
            msg.player.gainCredits(pirates.getCreditsGained());
            msg.flightBoard.loseFlightDays(msg.player, pirates.getDaysLost());
        } else {
            if (msg.canDefend && !msg.removedBatteryFrom.isEmpty()) {
                ArrayList<ArrayList<Integer>> batteriesList = msg.removedBatteryFrom;
                msg.player.getShip().acceptRemoveBattery(applyOperation, batteriesList);
            } else if (!msg.canDefend) {
                msg.player.getShip().removeComponent(msg.removedComponentFrom.get(0), msg.removedComponentFrom.get(1));
            }
        }
    }

    @Override
    public void visit(Epidemic epidemic, AdventureCardVisitorMessage msg) {
        List<Integer[]> cabinList = msg.player.getShip().getCabinList();
        List<Integer[]> alienLifeSupportList = msg.player.getShip().getAlienLifeSupportList();
        for (Integer[] cabinTemp1 : cabinList) {
            int x = cabinTemp1[0];
            int y = cabinTemp1[1];
            boolean checkTwice = false;
            for (Integer[] cabinTemp2 : cabinList) {
                if ((cabinTemp2[0] == x - 1 && cabinTemp2[1] == y || cabinTemp2[0] == x && cabinTemp2[1] == y - 1 || cabinTemp2[0] == x + 1 && cabinTemp2[1] == y || cabinTemp2[0] == x && cabinTemp2[1] == y + 1) && !checkTwice) {
                    //chiamata metodo per togliere un equipaggio in cabin
                    Cabin cabin = (Cabin) msg.player.getShip().getShipComponent(cabinTemp1[0], cabinTemp1[1]);
                    cabin.removeCrew();
                    checkTwice = true;
                    break;
                }
            }
            for (Integer[] alienLifeSupport : alienLifeSupportList) {
                if ((alienLifeSupport[0] == x - 1 && alienLifeSupport[1] == y || alienLifeSupport[0] == x && alienLifeSupport[1] == y - 1 || alienLifeSupport[0] == x + 1 && alienLifeSupport[1] == y || alienLifeSupport[0] == x && alienLifeSupport[1] == y + 1) && !checkTwice) {
                    checkTwice = true;
                    //chiamata metodo per togliere un equipaggio
                    Cabin cabin = (Cabin) msg.player.getShip().getShipComponent(cabinTemp1[0], cabinTemp1[1]);
                    cabin.removeCrew();
                    break;
                }
            }
        }
        for (Integer[] alienLifeSupport : alienLifeSupportList) {
            int x = alienLifeSupport[0];
            int y = alienLifeSupport[1];
            boolean checkTwice = false;
            for (Integer[] alienLifeSupportTemp : alienLifeSupportList) {
                if ((alienLifeSupportTemp[0] == x - 1 && alienLifeSupportTemp[1] == y || alienLifeSupportTemp[0] == x && alienLifeSupportTemp[1] == y - 1 || alienLifeSupportTemp[0] == x + 1 && alienLifeSupportTemp[1] == y || alienLifeSupportTemp[0] == x && alienLifeSupportTemp[1] == y + 1) && !checkTwice) {
                    //chiamata metodo per togliere un equipaggio in cabin
                    Cabin cabin = (Cabin) msg.player.getShip()
                            .getShipComponent(alienLifeSupportTemp[0], alienLifeSupportTemp[1]);
                    cabin.removeCrew();
                    checkTwice = true;
                    break;
                }
            }
            for (Integer[] cabinTemp : cabinList) {
                if ((cabinTemp[0] == x - 1 && cabinTemp[1] == y || cabinTemp[0] == x && cabinTemp[1] == y - 1 || cabinTemp[0] == x + 1 && cabinTemp[1] == y || cabinTemp[0] == x && cabinTemp[1] == y + 1) && !checkTwice) {
                    checkTwice = true;
                    Cabin cabin = (Cabin) msg.player.getShip().getShipComponent(cabinTemp[0], cabinTemp[1]);
                    cabin.removeCrew();
                    break;
                }
            }
        }
        epidemic.setCompleted(true);
    }

    @Override
    public void visit(AbandonedShip abandonedShip, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        msg.player.getShip().acceptRemoveCrew(applyOperation, msg.removedCrewFrom);
        msg.player.gainCredits(abandonedShip.getCreditsGained());
        msg.flightBoard.loseFlightDays(msg.player, abandonedShip.getDaysLost());
        abandonedShip.setCompleted(true);
    }

    @Override
    public void visit(MeteorSwarm meteorSwarm, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        if (msg.canDefend && !msg.removedBatteryFrom.isEmpty()) {
            ArrayList<ArrayList<Integer>> batteriesList = msg.removedBatteryFrom;
            msg.player.getShip().acceptRemoveBattery(applyOperation, batteriesList);
        } else if (!msg.canDefend) {
            msg.player.getShip().removeComponent(msg.removedComponentFrom.get(0), msg.removedComponentFrom.get(1));
        }
    }

    @Override
    public void visit(Warzone warzone, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        if (msg.penalty == 1) {
            msg.flightBoard.loseFlightDays(msg.player, warzone.getDaysLost());
        } else if (msg.penalty == 2) {
            msg.player.getShip().acceptRemoveCrew(applyOperation, msg.removedCrewFrom);
        } else if (msg.penalty == 3) {
            if (msg.canDefend && !msg.removedBatteryFrom.isEmpty()) {
                ArrayList<ArrayList<Integer>> batteriesList = msg.removedBatteryFrom;
                msg.player.getShip().acceptRemoveBattery(applyOperation, batteriesList);
            } else if (!msg.canDefend) {
                msg.player.getShip().removeComponent(msg.removedComponentFrom.get(0), msg.removedComponentFrom.get(1));
            }
        } else if (msg.penalty == 4) {
            msg.player.getShip().acceptRemoveGoods(applyOperation, msg.goodsMap);
        }


    }

    @Override
    public void visit(Planets planets, AdventureCardVisitorMessage msg) {
        OperationVisitor applyOperation = new ApplyOperation();
        msg.player.getShip().acceptManageGoods(applyOperation, msg.goodsMap);
        msg.flightBoard.loseFlightDays(msg.player, planets.getDaysLost());
    }

    @Override
    public void visit(OpenSpace openSpace, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        msg.player.getShip().acceptRemoveBattery(applyOperation, msg.removedBatteryFrom);
        msg.flightBoard.gainFlightDays(msg.player, msg.enginePower);
    }

    @Override
    public void visit(Stardust stardust, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        int playerExposedConnectors;
        playerExposedConnectors = msg.player.getShip().acceptCalculateExposedConnectors(applyOperation);
        msg.flightBoard.loseFlightDays(msg.player, playerExposedConnectors);
    }

    @Override //
    public void visit(Smugglers smugglers, AdventureCardVisitorMessage msg) throws IOException {
        OperationVisitor applyOperation = new ApplyOperation();
        if (msg.win) {
            msg.flightBoard.loseFlightDays(msg.player, smugglers.getDaysLost());
            msg.player.getShip().acceptManageGoods(applyOperation, msg.goodsMap);
        } else {
            msg.player.getShip().acceptRemoveBattery(applyOperation, msg.removedBatteryFrom);
            msg.player.getShip().acceptRemoveGoods(applyOperation, msg.goodsMap);

        }
    }
}
