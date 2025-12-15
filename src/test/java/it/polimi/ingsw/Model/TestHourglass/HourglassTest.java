package it.polimi.ingsw.Model.TestHourglass;

import it.polimi.ingsw.Model.Game.Hourglass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HourglassTest {
    @Test
    public void testHourglass() {
        Hourglass hg = new Hourglass();
        long currentTime = System.currentTimeMillis();
        assertTrue(hg.getStartTime() <= currentTime);
        assertEquals(0, hg.getTimesTurned());

        hg.incrementTimesTurned();
        assertEquals(1, hg.getTimesTurned());

        hg.incrementTimesTurned();
        assertEquals(2, hg.getTimesTurned());

    }
}
