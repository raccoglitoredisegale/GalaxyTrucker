package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class Cannon extends ComponentCard implements Serializable {

    private final int power;

    private final boolean isDouble;

    private int facingDirectionIndex; //0=nord, 1=est, 2=sud, 3=ovest

    public Cannon(String id, boolean isValid, boolean occupation, Connector[] connectors, boolean isDouble, int power,
                  int facingDirectionIndex) {
        super(id, isValid, occupation, connectors);
        this.power = power;
        this.isDouble = isDouble;
        this.facingDirectionIndex = facingDirectionIndex % 4; // se il cannone punta verso nord l'index è zero
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

    public int getPower() {
        return power;
    }

    public boolean isDouble() {
        return isDouble;
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToCannonList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToCannonList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "CAN";
    }


}
