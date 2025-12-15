package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {

    private final String username;
    private final int id;
    private Shipboard ship;
    private Color color;
    private int credits;

    private int completedLaps;

    private String gameID;

    public Player(String username) {
        this.username = username;
        this.ship = null;
        this.credits = 0;
        this.completedLaps = 0;
        this.color = null;
        Random r = new Random();
        this.id = r.nextInt(10000 - 1 + 1) + 1;
    }

    public String getUsername() {
        return this.username;
    }

    public Shipboard getShip() {
        return this.ship;
    }

    public void setShip(Shipboard ship) {
        this.ship = ship;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getCompletedLaps() {
        return this.completedLaps;
    }

    public void addCompletedLaps() {
        this.completedLaps++;
    }

    public void removeCompletedLaps() {
        this.completedLaps--;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void gainCredits(int credits) {
        this.credits += credits;
    }

    public int getId() {
        return id;
    }

    public String getGameID() {
        return this.gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
