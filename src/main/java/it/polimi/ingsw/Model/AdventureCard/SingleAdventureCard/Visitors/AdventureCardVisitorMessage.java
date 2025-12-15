package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors;


import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventureCardVisitorMessage {

    protected Player player;

    protected Flightboard flightBoard;

    protected List<Player> players;

    protected int enginePower;

    protected ArrayList<Integer> removedComponentFrom;
    protected ArrayList<ArrayList<Integer>> removedBatteryFrom;
    protected ArrayList<ArrayList<Integer>> removedCrewFrom;

    protected boolean canDefend;

    protected boolean win;

    protected int penalty;

    protected Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<ArrayList<Integer>, ArrayList<Goods>>();

    public AdventureCardVisitorMessage MessageEpidemic(Player player) {
        this.player = player;
        return this;
    }

    public AdventureCardVisitorMessage MessageAbandonedShip(Player player, Flightboard flightBoard, ArrayList<ArrayList<Integer>> removedCrewFrom) {
        this.player = player;
        this.removedCrewFrom = removedCrewFrom;
        this.flightBoard = flightBoard;
        return this;
    }

    public AdventureCardVisitorMessage MessageAbandonedStation(Player player, Flightboard flightBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.goodsMap = goodsMap;
        return this;
    }

    public AdventureCardVisitorMessage MessagePlanets(Player player, Flightboard flightBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.goodsMap = goodsMap;
        return this;
    }

    public AdventureCardVisitorMessage MessageStarDust(Player player, Flightboard flightboard) {
        this.player = player;
        this.flightBoard = flightboard;
        return this;
    }

    public AdventureCardVisitorMessage MessageSlavers(Player player, Flightboard flightBoard) {
        this.player = player;
        this.flightBoard = flightBoard;
        return this;
    }

    public AdventureCardVisitorMessage MessagePirates(boolean win, Player player, Flightboard flightBoard, boolean canDefend, ArrayList<Integer> removedComponentFrom, ArrayList<ArrayList<Integer>> removedBatteryFrom) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.canDefend = canDefend;
        this.removedComponentFrom = removedComponentFrom;
        this.removedBatteryFrom = removedBatteryFrom;
        this.win = win;
        return this;
    }

    public AdventureCardVisitorMessage MessageOpenSpace(Flightboard flightBoard, Player player, int enginePower, ArrayList<ArrayList<Integer>> removedBatteryFrom) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.removedBatteryFrom = removedBatteryFrom;
        this.enginePower = enginePower;
        return this;
    }

    public AdventureCardVisitorMessage MessageWarzone(Player player, Flightboard flightBoard, ArrayList<ArrayList<Integer>> removedCrewFrom, boolean canDefend, ArrayList<Integer> removedComponentFrom, ArrayList<ArrayList<Integer>> removedBatteryFrom, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap, int penalty) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.removedCrewFrom = removedCrewFrom;
        this.canDefend = canDefend;
        this.removedComponentFrom = removedComponentFrom;
        this.removedBatteryFrom = removedBatteryFrom;
        this.penalty = penalty;
        this.goodsMap = goodsMap;
        return this;
    }

    public AdventureCardVisitorMessage MessageSmugglers(boolean win, Player player, Flightboard flightBoard, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap, ArrayList<ArrayList<Integer>> removedBatteriesFrom) {
        this.player = player;
        this.flightBoard = flightBoard;
        this.goodsMap = goodsMap;
        this.removedBatteryFrom = removedBatteriesFrom;
        this.win = win;
        return this;
    }

    public AdventureCardVisitorMessage MessageMeteorSwarm(Player player, boolean canDefend, ArrayList<Integer> removedComponentFrom, ArrayList<ArrayList<Integer>> removedBatteryFrom) {
        this.player = player;
        this.canDefend = canDefend;
        this.removedComponentFrom = removedComponentFrom;
        this.removedBatteryFrom = removedBatteryFrom;
        return this;
    }
}
