package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import java.io.Serializable;

public class Dim_Dir implements Serializable {
    private final String dimension;
    private final String direction;

    public Dim_Dir(String dimension, String direction) {
        this.dimension = dimension;
        this.direction = direction;
    }

    public String getDimension() {
        return dimension;
    }

    public String getDirection() {
        return direction;
    }
}
