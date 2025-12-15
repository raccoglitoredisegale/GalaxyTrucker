package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cannon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CannonTest {
    @Test
    public void testCannon() {
       Cannon cannon_2 = new Cannon("11512", false, false, new Connector[]{new Connector(0, true),
               new Connector(0, true), new Connector(1, true), new Connector(0, true),}, false, 1, 0);

        cannon_2.rotation();
        assertEquals(1, cannon_2.getFacingDirectionIndex());
        assertEquals(1, cannon_2.getPower());
        assertEquals(false, cannon_2.isDouble());

    }
}
