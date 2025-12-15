package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;

public class ShieldGenerator extends ComponentCard implements Serializable {

    private int shieldSides;

    public ShieldGenerator(String id, boolean IsValid, boolean occupation, Connector[] connectors, int shieldSides) {
        super(id, IsValid, occupation, connectors);
        this.shieldSides = shieldSides % 4;
    }

    @Override
    public void rotation() {
        super.rotation();
        rotateShieldSides();
    }

    public void rotateShieldSides() {
        shieldSides = (shieldSides + 1) % 4;
    }

    /**
     * @return un array di due caratteri che corrisponde alle direzioni coperte nella nave da parte dello scudo
     */
    public Character[] getCoveredShipSides() {
        return switch (this.shieldSides) {
            case 0 -> new Character[]{'N', 'E'};
            case 1 -> new Character[]{'E', 'S'};
            case 2 -> new Character[]{'S', 'W'};
            case 3 -> new Character[]{'W', 'N'};
            default -> null;
        };
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToShieldList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToShieldList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "SHG";
    }

    @Override
    public String getFacingSymbol() {
        return switch (shieldSides) {
            case 0 -> "▲";
            case 1 -> "▶";
            case 2 -> "▼";
            case 3 -> "◀";
            default -> "?";
        };
    }
}

