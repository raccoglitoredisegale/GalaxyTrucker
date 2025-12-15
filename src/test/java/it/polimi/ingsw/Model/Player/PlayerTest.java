package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class PlayerTest {

    Player player = new Player("carlo");
    Color color = Color.RED;
    Shipboard ship;

    @Test
    public void testPlayer() {
      player.setShip(ship);
      player.addCompletedLaps();
      player.addCompletedLaps();
      player.removeCompletedLaps();
      player.setColor(this.color);
      player.gainCredits(25);
      player.setGameID("khgj");


      assertEquals(ship, player.getShip());
      assertEquals(Color.RED, player.getColor());
      assertEquals(25, player.getCredits());
      assertEquals("khgj", player.getGameID());
      assertEquals("carlo", player.getUsername());
      assertEquals(1, player.getCompletedLaps());
      assertEquals("red", color.getColor());
      assertEquals(Color.GREEN, color.fromString("green"));
    }
}
