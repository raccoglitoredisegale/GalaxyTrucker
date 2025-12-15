package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.AlienLifeSupport;

import java.util.ArrayList;

public class LoadAlienLifeSupport {
    private final ArrayList<AlienLifeSupport> alienLifeSupports;


    /**
     * first number (1) --> component card second and third number (17) --> type of
     * component card last two number (11) --> sequence of component card
     */

    AlienLifeSupport alienLifeSupp_1 = new AlienLifeSupport("11711", false, false, new Connector[]{new Connector(1, true),
            new Connector(1, true), new Connector(0, true), new Connector(1, true),}, false); // GT-new_tiles_16_for
    // web137

    AlienLifeSupport alienLifeSupp_2 = new AlienLifeSupport("11712", false, false, new Connector[]{new Connector(2, true),
            new Connector(1, true), new Connector(0, true), new Connector(1, true),}, false); // GT-new_tiles_16_for
    // web138

    AlienLifeSupport alienLifeSupp_3 = new AlienLifeSupport("11713", false, false, new Connector[]{new Connector(0, true),
            new Connector(0, true), new Connector(0, true), new Connector(3, true),}, false); // GT-new_tiles_16_for
    // web139

    AlienLifeSupport alienLifeSupp_4 = new AlienLifeSupport("11714", false, false, new Connector[]{new Connector(0, true),
            new Connector(0, true), new Connector(1, true), new Connector(3, true),}, false); // GT-new_tiles_16_for
    // web140

    AlienLifeSupport alienLifeSupp_5 = new AlienLifeSupport("11715", false, false, new Connector[]{new Connector(0, true),
            new Connector(2, true), new Connector(0, true), new Connector(3, true),}, false); // GT-new_tiles_16_for
    // web141

    AlienLifeSupport alienLifeSupp_6 = new AlienLifeSupport("11716", false, false, new Connector[]{new Connector(1, true),
            new Connector(0, true), new Connector(0, true), new Connector(3, true),}, false); // GT-new_tiles_16_for
    // web142

    AlienLifeSupport alienLifeSupp_7 = new AlienLifeSupport("11717", false, false, new Connector[]{new Connector(1, true),
            new Connector(2, true), new Connector(0, true), new Connector(2, true),}, true); // GT-new_tiles_16_for
    // web143

    AlienLifeSupport alienLifeSupp_8 = new AlienLifeSupport("11718", false, false, new Connector[]{new Connector(2, true),
            new Connector(2, true), new Connector(0, true), new Connector(2, true),}, true); // GT-new_tiles_16_for
    // web144

    AlienLifeSupport alienLifeSupp_9 = new AlienLifeSupport("11719", false, false, new Connector[]{new Connector(0, true),
            new Connector(0, true), new Connector(0, true), new Connector(3, true),}, true); // GT-new_tiles_16_for
    // web145

    AlienLifeSupport alienLifeSupp_10 = new AlienLifeSupport("11720", false, false, new Connector[]{
            new Connector(0, true), new Connector(0, true), new Connector(2, true), new Connector(3, true),}, true); // GT-new_tiles_16_for
    // web146

    AlienLifeSupport alienLifeSupp_11 = new AlienLifeSupport("11721", false, false, new Connector[]{
            new Connector(0, true), new Connector(1, true), new Connector(0, true), new Connector(3, true),}, true); // GT-new_tiles_16_for
    // web147

    AlienLifeSupport alienLifeSupp_12 = new AlienLifeSupport("11722", false, false, new Connector[]{
            new Connector(2, true), new Connector(0, true), new Connector(0, true), new Connector(3, true),}, true); // GT-new_tiles_16_for
    // web148

    public LoadAlienLifeSupport() {
        this.alienLifeSupports = new ArrayList<>();
        alienLifeSupports.add(alienLifeSupp_1);
        alienLifeSupports.add(alienLifeSupp_2);
        alienLifeSupports.add(alienLifeSupp_3);
        alienLifeSupports.add(alienLifeSupp_4);
        alienLifeSupports.add(alienLifeSupp_5);
        alienLifeSupports.add(alienLifeSupp_6);
        alienLifeSupports.add(alienLifeSupp_7);
        alienLifeSupports.add(alienLifeSupp_8);
        alienLifeSupports.add(alienLifeSupp_9);
        alienLifeSupports.add(alienLifeSupp_10);
        alienLifeSupports.add(alienLifeSupp_11);
        alienLifeSupports.add(alienLifeSupp_12);
    }

    public ArrayList<AlienLifeSupport> getAlienLifeSupports() {
        return this.alienLifeSupports;
    }
}
