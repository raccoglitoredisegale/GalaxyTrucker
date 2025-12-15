package it.polimi.ingsw.Model.TestFlightboard;

import it.polimi.ingsw.Model.FlightBoard.CircularDoubleLinkedList;
import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightboardTest {
    Flightboard flightboard1 = new Flightboard(1);
    Flightboard flightboard2 = new Flightboard(2);
    CircularDoubleLinkedList track1 = new CircularDoubleLinkedList(18);
    CircularDoubleLinkedList track2 = new CircularDoubleLinkedList(25);
    Player p1 = new Player("gio");
    Player p2 = new Player("elio");

    @Test
    public void testFlightboard() {
        track1.insertValue(p1.getId(), 6); //2
        track1.insertValue(p2.getId(), 4); //2
        track2.insertValue(p1.getId(), 9); //3
        track2.insertValue(p2.getId(), 4); //1

        flightboard1.insertPlayer(p1, 1);
        flightboard1.insertPlayer(p2, 1);
        flightboard2.insertPlayer(p1, 2);
        flightboard2.insertPlayer(p2, 2);
        ArrayList<Player> ps1 = new ArrayList<>();
        ArrayList<Player> ps2 = new ArrayList<>();
        ps1.add(p1);
        ps1.add(p2);
        ps2.add(p1);
        ps2.add(p2);
        flightboard1.calculatePlayersOrder();
        flightboard2.calculatePlayersOrder();
        assertEquals(ps1, flightboard1.getPlayersInOrder());
        assertEquals(ps2, flightboard2.getPlayersInOrder());
        assertEquals(p1, flightboard1.getLeader());
        assertEquals(p1, flightboard2.getLeader());

        flightboard1.gainFlightDays(p1, 2);
        flightboard1.gainFlightDays(p2, 2);

        flightboard2.gainFlightDays(p1, 3);
        flightboard2.gainFlightDays(p2, 1);

        assertEquals(track1.getValue(6), flightboard1.getTrack().getValue(6));
        assertEquals(track1.getValue(4), flightboard1.getTrack().getValue(4));
        assertEquals(track2.getValue(9), flightboard2.getTrack().getValue(9));
        assertEquals(track2.getValue(4), flightboard2.getTrack().getValue(4));

        flightboard1.gainFlightDays(p2, 2);
        flightboard2.gainFlightDays(p2, 5);
        track1.insertValue(0, 4);
        track1.insertValue(p2.getId(), 7); //2+2
        track2.insertValue(0, 4);
        track2.insertValue(p2.getId(), 10); //1+5
        assertEquals(track1.getValue(7), flightboard1.getTrack().getValue(7));
        assertEquals(track2.getValue(10), flightboard2.getTrack().getValue(10));
        assertEquals(track2.getValue(4), flightboard2.getTrack().getValue(4));
        assertEquals(track1.getValue(4), flightboard1.getTrack().getValue(4));
        assertEquals(p2, flightboard1.getLeader());
        assertEquals(p2, flightboard2.getLeader());

        flightboard1.gainFlightDays(p2, 30);
        ArrayList<Player> st3 = new ArrayList<>();
        st3.add(p1);
        assertEquals(p1, flightboard1.getStalledPlayers().get(0));
        assertEquals(p2, flightboard1.getPlayersInOrder().get(0));
        assertEquals(p2, flightboard1.getLeader());
        assertEquals(18, track1.getLength());

    }

    @Test
    public void testFlightboard2() {
        flightboard1.insertPlayer(p1, 1);
        flightboard1.insertPlayer(p2, 1);
        track1.insertValue(p1.getId(), 1); //2
        track1.insertValue(p2.getId(), 17); //2
        flightboard1.loseFlightDays(p1, 2);
        flightboard1.loseFlightDays(p2, 2);
        assertEquals(track1.getValue(1), flightboard1.getTrack().getValue(1));
        assertEquals(track1.getValue(17), flightboard1.getTrack().getValue(17));

        flightboard1.stallPlayer(p1);
        flightboard2.stallPlayer(p2);
        ArrayList<Player> st1 = new ArrayList<>();
        ArrayList<Player> st2 = new ArrayList<>();
        st1.add(p1);
        st2.add(p2);
        assertEquals(st1, flightboard1.getStalledPlayers());
        assertEquals(st2, flightboard2.getStalledPlayers());










    }


}
