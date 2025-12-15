package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.BatteryCompartment;

import java.util.ArrayList;

public class LoadBatteryCompartment {
    private final ArrayList<BatteryCompartment> batteryCompartments;

    /**
     * first number (1) --> coomponent card
     * second and third number (11) --> type of component card
     * last two number (12) --> sequence of component card
     */

    BatteryCompartment batteryCompartment_1 = new BatteryCompartment("11111", false, false, new Connector[]{
            new Connector(3, true), new Connector(1, true), new Connector(2, true), new Connector(0, true),}, 2); // GT-new_tiles_16_for
    // web

    BatteryCompartment batteryCompartment_2 = new BatteryCompartment("11112", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(2, true), // est
                    new Connector(0, true), // sud
                    new Connector(0, true) // ovest
            }, 2); // GT-new_tiles_16_for web2

    BatteryCompartment batteryCompartment_3 = new BatteryCompartment("11113", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(2, true), // est
                    new Connector(1, true), // sud
                    new Connector(0, true) // ovest
            }, 2); // GT-new_tiles_16_for web3

    BatteryCompartment batteryCompartment_4 = new BatteryCompartment("11114", false, false,
            new Connector[]{new Connector(2, true), // nord
                    new Connector(1, true), // est
                    new Connector(2, true), // sud
                    new Connector(1, true) // ovest
            }, 2); // GT-new_tiles_16_for web4

    BatteryCompartment batteryCompartment_5 = new BatteryCompartment("11115", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(1, true) // ovest
            }, 2); // GT-new_tiles_16_for web5

    BatteryCompartment batteryCompartment_6 = new BatteryCompartment("11116", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(1, true), // est
                    new Connector(1, true), // sud
                    new Connector(1, true) // ovest
            }, 2); // GT-new_tiles_16_for web6

    BatteryCompartment batteryCompartment_7 = new BatteryCompartment("11117", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(2, true), // est
                    new Connector(2, true), // sud
                    new Connector(2, true) // ovest
            }, 2); // GT-new_tiles_16_for web7

    BatteryCompartment batteryCompartment_8 = new BatteryCompartment("11118", false, false,
            new Connector[]{new Connector(0, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(3, true) // ovest
            }, 2); // GT-new_tiles_16_for web8

    BatteryCompartment batteryCompartment_9 = new BatteryCompartment("11119", false, false,
            new Connector[]{new Connector(0, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(3, true) // ovest
            }, 2); // GT-new_tiles_16_for web9

    BatteryCompartment batteryCompartment_10 = new BatteryCompartment("11120", false, false,
            new Connector[]{new Connector(0, true), // nord
                    new Connector(3, true), // est
                    new Connector(0, true), // sud
                    new Connector(3, true) // ovest
            }, 2); // GT-new_tiles_16_for web10

    BatteryCompartment batteryCompartment_11 = new BatteryCompartment("11121", false, false,
            new Connector[]{new Connector(3, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(3, true) // ovest
            }, 2); // GT-new_tiles_16_for web11

    BatteryCompartment batteryCompartment_12 = new BatteryCompartment("11122", false, false,
            new Connector[]{new Connector(1, true), // nord
                    new Connector(0, true), // est
                    new Connector(2, true), // sud
                    new Connector(0, true) // ovest
            }, 3); // GT-new_tiles_16_for web12

    BatteryCompartment batteryCompartment_13 = new BatteryCompartment("11123", false, false,
            new Connector[]{new Connector(1, true), // nord
                    new Connector(1, true), // est
                    new Connector(2, true), // sud
                    new Connector(0, true) // ovest
            }, 3); // GT-new_tiles_16_for web13

    BatteryCompartment batteryCompartment_14 = new BatteryCompartment("11124", false, false,
            new Connector[]{new Connector(2, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(0, true) // ovest
            }, 3); // GT-new_tiles_16_for web14

    BatteryCompartment batteryCompartment_15 = new BatteryCompartment("11125", false, false,
            new Connector[]{new Connector(2, true), // nord
                    new Connector(1, true), // est
                    new Connector(0, true), // sud
                    new Connector(0, true) // ovest
            }, 3); // GT-new_tiles_16_for web15

    BatteryCompartment batteryCompartment_16 = new BatteryCompartment("11126", false, false,
            new Connector[]{new Connector(2, true), // nord
                    new Connector(2, true), // est
                    new Connector(1, true), // sud
                    new Connector(0, true) // ovest
            }, 3); // GT-new_tiles_16_for web16

    BatteryCompartment batteryCompartment_17 = new BatteryCompartment("11127", false, false,
            new Connector[]{new Connector(0, true), // nord
                    new Connector(0, true), // est
                    new Connector(0, true), // sud
                    new Connector(1, true) // ovest
            }, 3); // GT-new_tiles_16_for web17

    public LoadBatteryCompartment() {
        this.batteryCompartments = new ArrayList();
        batteryCompartments.add(batteryCompartment_1);
        batteryCompartments.add(batteryCompartment_2);
        batteryCompartments.add(batteryCompartment_3);
        batteryCompartments.add(batteryCompartment_4);
        batteryCompartments.add(batteryCompartment_5);
        batteryCompartments.add(batteryCompartment_6);
        batteryCompartments.add(batteryCompartment_7);
        batteryCompartments.add(batteryCompartment_8);
        batteryCompartments.add(batteryCompartment_9);
        batteryCompartments.add(batteryCompartment_10);
        batteryCompartments.add(batteryCompartment_11);
        batteryCompartments.add(batteryCompartment_12);
        batteryCompartments.add(batteryCompartment_13);
        batteryCompartments.add(batteryCompartment_14);
        batteryCompartments.add(batteryCompartment_15);
        batteryCompartments.add(batteryCompartment_16);
        batteryCompartments.add(batteryCompartment_17);
    }

    public ArrayList<BatteryCompartment> getBatteryCompartments() {
        return this.batteryCompartments;
    }
}
