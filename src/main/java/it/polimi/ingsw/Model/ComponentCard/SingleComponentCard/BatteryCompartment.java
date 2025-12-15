package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class BatteryCompartment extends ComponentCard implements Serializable {

    private final int capacity;

    private int batteriesAvailable;

    public BatteryCompartment(String id, boolean IsValid, boolean occupation, Connector[] connectors, int capacity) {
        super(id, IsValid, occupation, connectors);
        this.capacity = capacity;
        this.batteriesAvailable = capacity;
    }

    public void consumeBattery() {
        if (batteriesAvailable > 0) {
            batteriesAvailable--;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBatteriesAvailable() {
        return batteriesAvailable;
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToBatteryList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToBatteryList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "BCO ";
    }
}
