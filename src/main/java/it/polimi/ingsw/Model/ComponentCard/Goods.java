package it.polimi.ingsw.Model.ComponentCard;

public enum Goods {
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m");

    private static final String RESET = "\u001B[0m";
    private final String colorCode;

    Goods(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return colorCode + name() + RESET;
    }
}

