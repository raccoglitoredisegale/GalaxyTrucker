package it.polimi.ingsw.Model.FlightBoard;


import it.polimi.ingsw.Model.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Flightboard implements Serializable {

    private final int trackLenght1 = 18;
    private final int trackLenght2 = 25;
    private final CircularDoubleLinkedList track;
    private final ArrayList<Player> playersInOrder;
    private final int maxCompletedLaps;
    private ArrayList<Player> stalledPlayers = new ArrayList<>();
    private Player leader;

    //DeckManager deckManager;

    public Flightboard(int gameLevel) {
        if (gameLevel == 1) {
            this.track = new CircularDoubleLinkedList(trackLenght1);
        } else {
            this.track = new CircularDoubleLinkedList(trackLenght2);
        }
        this.stalledPlayers = new ArrayList<>();
        this.maxCompletedLaps = 0;
        this.playersInOrder = new ArrayList<>();
    }

    public CircularDoubleLinkedList getTrack() {
        return track;
    }


    // Inserts the player without knowing the position in the completion order of the ship. It checks if the highest index of the board is free or until the first already assigned, among the assignable ones. If free, assigns the position, otherwise assigns the previous one
    public void insertPlayer(Player player, int level) {
        if (level == 1) {
            for (int i = 4; i > -1; i--) {
                if (track.getValue(i) == 0 && (i == 4)) {
                    track.insertValue(player.getId(), i);
                    this.leader = player;
                    playersInOrder.add(player);
                    calculatePlayersOrder();
                    return;
                } else if (track.getValue(i) == 0 && (i == 2 || i == 1 || i == 0)) {
                    track.insertValue(player.getId(), i);
                    playersInOrder.add(player);
                    calculatePlayersOrder();
                    return;
                }
            }
        } else {
            for (int i = 6; i > -1; i--) {
                if (track.getValue(i) == 0 && (i == 6)) {
                    track.insertValue(player.getId(), i);
                    this.leader = player;
                    playersInOrder.add(player);
                    calculatePlayersOrder();
                    return;
                }
                if (track.getValue(i) == 0 && (i == 3 || i == 1 || i == 0)) {
                    track.insertValue(player.getId(), i);
                    playersInOrder.add(player);
                    calculatePlayersOrder();
                    return;
                }
            }
        }
    }

    public synchronized void removePlayerFromTrack(Player player) {
        for (int i = 0; i < trackLenght2; i++) {
            if (track.getValue(i) == player.getId()) {
                track.insertValue(0, i);
                if (!playersInOrder.isEmpty() && playersInOrder.get(0).equals(player)) {
                    if (playersInOrder.size() >= 2) {
                        leader = playersInOrder.get(1);
                    } else leader = null;
                }
                playersInOrder.remove(player);
                return;
            }
        }
    }

/*	public void calculatePlayersOrder() {
		ArrayList<Player> players = new ArrayList<>(playersInOrder);
		playersInOrder.clear();
		for (int j = maxCompletedLaps; j >= 0; j--) {
			for (int i = track.getLength(); i > 0; i--) {
				for (Player player : players) {
					if (track.getValue(i) == player.getId() && player.getCompletedLaps() == j
							&& !playersInOrder.contains(player)) {
						playersInOrder.add(player);
					}
				}
			}
		}
		if(!playersInOrder.isEmpty()){leader = playersInOrder.getFirst();}
	} */

    public synchronized void calculatePlayersOrder() {
        playersInOrder.sort(Comparator
                .comparingInt(Player::getCompletedLaps).reversed()
                .thenComparingInt(player -> -track.getIndex(player.getId())));
        if (!playersInOrder.isEmpty()) {
            leader = playersInOrder.getFirst();
        }
    }


    public void gainFlightDays(Player player, int days) {
        for (int day = 0; day < days; day++) {
            boolean newLap = track.moveForward(player.getId());
            if (newLap) {
                player.addCompletedLaps();
            }
        }
        checkStalledPlayer(player, playersInOrder);
        calculatePlayersOrder();
    }

    public void loseFlightDays(Player player, int days) {
        for (int day = 0; day < days; day++) {
            boolean newLap = track.moveBackward(player.getId());
            if (newLap) {
                player.removeCompletedLaps();
            }
        }
        checkStalledPlayer(player, playersInOrder);
        calculatePlayersOrder();
    }


    public synchronized void checkStalledPlayer(Player playerMoved, ArrayList<Player> players) {
        ArrayList<Player> toRemove = new ArrayList<>();

        for (Player player : players) {
            if (playerMoved.getCompletedLaps() == (player.getCompletedLaps() + 1)
                    && track.getIndex(playerMoved.getId()) > track.getIndex(player.getId())
                    || (playerMoved.getCompletedLaps() >= (player.getCompletedLaps() + 2))) {
                stalledPlayers.add(player);
                toRemove.add(player);
            }
        }
        for (Player player : toRemove) {
            removePlayerFromTrack(player);
        }
    }

    public synchronized void stallPlayer(Player player) {
        stalledPlayers.add(player);
        removePlayerFromTrack(player);
    }

    public synchronized ArrayList<Player> getStalledPlayers() {
        return stalledPlayers;
    }

    public synchronized ArrayList<Player> getPlayersInOrder() {
        return playersInOrder;
    }

    public Player getLeader() {
        return leader;
    }

}

