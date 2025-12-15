package it.polimi.ingsw.Model.Game;

public class Hourglass {
    private long startTime;
    private int timesTurned;

    public Hourglass() {
        this.startTime = System.currentTimeMillis();
        this.timesTurned = 0;
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= 90000;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public int getTimesTurned() {
        return this.timesTurned;
    }

    public void incrementTimesTurned() {
        this.startTime = System.currentTimeMillis();
        this.timesTurned++;
    }
}
