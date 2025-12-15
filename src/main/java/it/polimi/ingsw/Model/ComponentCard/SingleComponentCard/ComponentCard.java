package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class ComponentCard implements Serializable {

    private final Connector[] connectors; // e' una lista che per un componente non ancora ruotato sarà così formata
    private final String id;
    private boolean isValid;
    private boolean occupation;
    // ['N', 'E', 'S', 'W'], quindi l'indice 0 corrisponde al nord

    public ComponentCard(String id, boolean isValid, boolean occupation, Connector[] connectors) {
        this.id = id;
        this.isValid = isValid;
        this.occupation = occupation;
        this.connectors = connectors;
    }

    public void rotation() {
        if (connectors != null && connectors.length == 4) {
            Connector temp = connectors[3];
            for (int i = 3; i > 0; i--) {
                connectors[i] = connectors[i - 1];
            }
            connectors[0] = temp;
        }
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    public String getId() {
        return id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isOccupation() {
        return occupation;
    }

    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }

    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
    }

    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
    }

    public String getShortName() {
        return "???"; // default
    }

    public String getFacingSymbol() {
        return "";
    }


}
