package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class AlienLifeSupport extends ComponentCard implements Serializable {

    private boolean purpleAlien;

    public AlienLifeSupport(String id, boolean isValid, boolean occupation, Connector[] connectors, boolean purpleAlien) {
        super(id, isValid, occupation, connectors);
        this.purpleAlien = purpleAlien;
    }

    public boolean isPurpleAlien() {
        return purpleAlien;
    }

    public void setPurpleAlien(boolean purpleAlien) {
        this.purpleAlien = purpleAlien;
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToAlienLifeSupportList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToAlienLifeSupportList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "ALS";
    }
}
