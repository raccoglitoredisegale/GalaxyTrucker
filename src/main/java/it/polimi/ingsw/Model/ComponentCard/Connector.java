package it.polimi.ingsw.Model.ComponentCard;

import java.io.Serializable;

public class Connector implements Serializable {

    int tubeNumber;

    boolean exposed;

    public Connector(int tubeNumber, boolean expose) {
        this.tubeNumber = tubeNumber;
        this.exposed = expose;
    }

    public int getTubeNumber() {
        return tubeNumber;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean expose) {
        this.exposed = expose;
    }

}
