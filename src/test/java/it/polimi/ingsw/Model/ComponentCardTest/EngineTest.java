package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Engine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineTest {

    @Test
    public void testEngine() {

        Engine engine_1 = new Engine("11411", false, false, new Connector[]{new Connector(0, true),
                new Connector(3, true), new Connector(0, true), new Connector(0, true),}, false, 2); // GT-new_tiles_16_for

        engine_1.rotation();
        assertEquals(3, engine_1.getFacingDirectionIndex());
        assertEquals(false, engine_1.isDouble());

    }
}
