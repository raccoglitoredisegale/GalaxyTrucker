package it.polimi.ingsw.Model.Player;

import java.io.Serializable;

public enum Color implements Serializable {
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public static Color fromString(String colorStr) {
        for (Color c : Color.values()) {
            if (c.color.equalsIgnoreCase(colorStr)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No enum constant for color string: " + colorStr);
    }

    public String getColor() {
        return this.color;
    }
}
