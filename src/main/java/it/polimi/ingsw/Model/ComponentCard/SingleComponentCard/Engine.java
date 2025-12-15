package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class Engine extends ComponentCard implements Serializable {


    private final boolean isDouble;

    private int facingDirectionIndex; //0=nord, 1=est, 2=sud, 3=ovest

    public Engine(String id, boolean isValid, boolean occupation, Connector[] connectors, boolean isDouble,
                  int facingDirectionIndex) {
        super(id, isValid, occupation, connectors);
        this.isDouble = isDouble;
        this.facingDirectionIndex = facingDirectionIndex % 4;
    }

    @Override
    public void rotation() {
        super.rotation();
        rotateFacingClockwise();
    }

    public void rotateFacingClockwise() {
        facingDirectionIndex = (facingDirectionIndex + 1) % 4;
    }


    public int getFacingDirectionIndex() {
        return facingDirectionIndex;
    }


    public boolean isDouble() {
        return isDouble;
    }


    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToEngineList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToEngineList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "ENG";
    }
}
