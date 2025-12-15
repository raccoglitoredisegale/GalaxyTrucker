package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.OccupantType;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CabinTest {
    @Test
    public void testCabin() {

        Cabin cabin = new Cabin("11811", false, false,
                new Connector[]{
                        new Connector(3, true),
                        new Connector(3, true),
                        new Connector(3, true),
                        new Connector(3, true),},
                2, OccupantType.HUMAN, true); // GT-new_tiles_16_for web33

        cabin.setOccupantType(OccupantType.BROWNALIEN);
        cabin.removeCrew();
        assertEquals(1, cabin.getOccupantNumber());
        assertEquals(OccupantType.HUMAN, cabin.getOccupantType());
        assertEquals(true, cabin.isStarting());



    }
}
