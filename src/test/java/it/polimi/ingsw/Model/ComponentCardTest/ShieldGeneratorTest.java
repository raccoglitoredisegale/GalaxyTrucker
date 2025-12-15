package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ShieldGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShieldGeneratorTest {
    @Test
    public void testShieldGenerator() {
        ShieldGenerator shieldGen_1 = new ShieldGenerator("11611", false, false, new Connector[]{new Connector(0, true),
                new Connector(1, true), new Connector(3, true), new Connector(1, true),}, 0); // GT-new_tiles_16_for

        shieldGen_1.rotation();
        assertEquals('E', shieldGen_1.getCoveredShipSides()[0]);
        assertEquals('S', shieldGen_1.getCoveredShipSides()[1]);
    }
}
