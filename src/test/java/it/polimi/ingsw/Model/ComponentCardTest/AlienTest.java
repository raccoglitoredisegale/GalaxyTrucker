package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.AlienLifeSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlienTest {
    @Test
    public void testAlien() {
        AlienLifeSupport alienLifeSupp_1 = new AlienLifeSupport("11711", false, false, new Connector[]{new Connector(1, true),
                new Connector(1, true), new Connector(0, true), new Connector(1, true),}, false); // GT-new_tiles_16_for
        // web137

        alienLifeSupp_1.setPurpleAlien(true);
        assertEquals(true, alienLifeSupp_1.isPurpleAlien());
    }
}
