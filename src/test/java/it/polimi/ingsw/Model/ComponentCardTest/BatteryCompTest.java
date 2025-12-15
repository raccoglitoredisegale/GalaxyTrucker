package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.BatteryCompartment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatteryCompTest {
    @Test
    public void testBatteryComp() {
        BatteryCompartment batteryCompartment = new BatteryCompartment("11112", false, false,
                new Connector[]{new Connector(3, true), // nord
                        new Connector(2, true), // est
                        new Connector(0, true), // sud
                        new Connector(0, true) // ovest
                }, 2); // GT-new_tiles_16_for web2

        batteryCompartment.consumeBattery();
        assertEquals(1, batteryCompartment.getBatteriesAvailable());
        assertEquals(2, batteryCompartment.getCapacity());

    }
}
