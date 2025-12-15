package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ShieldGenerator;

import java.util.ArrayList;

public class LoadShieldGenerator {

    // web155
    private final ArrayList<ShieldGenerator> shieldGenerators;
    /**
     * first number (1) --> coomponent card second and third number (16) --> type of
     * component card last two number (11) --> sequence of component card
     */
    ShieldGenerator shieldGen_1 = new ShieldGenerator("11611", false, false, new Connector[]{new Connector(0, true),
            new Connector(1, true), new Connector(3, true), new Connector(1, true),}, 0); // GT-new_tiles_16_for
    ShieldGenerator shieldGen_2 = new ShieldGenerator("11612", false, false, new Connector[]{new Connector(1, true),
            new Connector(0, true), new Connector(1, true), new Connector(1, true),}, 0); // GT-new_tiles_16_for
    // web149
    ShieldGenerator shieldGen_3 = new ShieldGenerator("11613", false, false, new Connector[]{new Connector(2, true),
            new Connector(1, true), new Connector(2, true), new Connector(1, true),}, 0); // GT-new_tiles_16_for
    // web150
    ShieldGenerator shieldGen_4 = new ShieldGenerator("11614", false, false, new Connector[]{new Connector(0, true),
            new Connector(0, true), new Connector(3, true), new Connector(2, true),}, 0); // GT-new_tiles_16_for
    // web151
    ShieldGenerator shieldGen_5 = new ShieldGenerator("11615", false, false, new Connector[]{new Connector(0, true),
            new Connector(2, true), new Connector(2, true), new Connector(2, true),}, 0); // GT-new_tiles_16_for
    // web152
    ShieldGenerator shieldGen_6 = new ShieldGenerator("11616", false, false, new Connector[]{new Connector(1, true),
            new Connector(2, true), new Connector(1, true), new Connector(2, true),}, 0); // GT-new_tiles_16_for
    // web153
    ShieldGenerator shieldGen_7 = new ShieldGenerator("11617", false, false, new Connector[]{new Connector(0, true),
            new Connector(0, true), new Connector(1, true), new Connector(3, true),}, 0); // GT-new_tiles_16_for
    // web154
    ShieldGenerator shieldGen_8 = new ShieldGenerator("11618", false, false, new Connector[]{new Connector(0, true),
            new Connector(2, true), new Connector(2, true), new Connector(3, true),}, 0); // GT-new_tiles_16_for
    // web156

    public LoadShieldGenerator() {
        shieldGenerators = new ArrayList<>();
        shieldGenerators.add(shieldGen_1);
        shieldGenerators.add(shieldGen_2);
        shieldGenerators.add(shieldGen_3);
        shieldGenerators.add(shieldGen_4);
        shieldGenerators.add(shieldGen_5);
        shieldGenerators.add(shieldGen_6);
        shieldGenerators.add(shieldGen_7);
        shieldGenerators.add(shieldGen_8);
    }

    public ArrayList<ShieldGenerator> getShieldGenerators() {
        return shieldGenerators;
    }
}
