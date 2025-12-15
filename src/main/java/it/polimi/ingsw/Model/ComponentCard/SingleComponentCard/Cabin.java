package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.OccupantType;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class Cabin extends ComponentCard implements Serializable {

    private final boolean starting;
    private int OccupantNumber;
    private OccupantType OccupantType;

    public Cabin(String id, boolean isValid, boolean occupation, Connector[] connectors, int OccupantNumber,
                 OccupantType OccupantType, boolean starting) {
        super(id, isValid, occupation, connectors);
        this.OccupantNumber = OccupantNumber;
        this.OccupantType = OccupantType;
        this.starting = starting;
    }

    public void removeCrew() {
        if (OccupantNumber > 0) {
            OccupantNumber--;
        }
    }

    public int getOccupantNumber() {
        return OccupantNumber;
    }

    public OccupantType getOccupantType() {
        return OccupantType;
    }

    public void setOccupantType(OccupantType OccupantType) {
        if (!starting) this.OccupantType = OccupantType;
    }

    public boolean isStarting() {
        return starting;
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToCabinList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToCabinList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "CAB";
    }
}
