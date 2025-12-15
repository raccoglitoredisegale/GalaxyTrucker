package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Hold;

import java.util.ArrayList;

public class LoadHold {

    private final ArrayList<Hold> holds;
    /**
     * first number (1) --> component card second and third number (12) --> type of
     * component card, hold last two number (11) --> sequence of component card
     */

    Hold hold_1 = new Hold("11211", false, false, new Connector[]{
            new Connector(2, true), new Connector(1, true),
            new Connector(3, true), new Connector(0, true),}, 2, false); // GT-new_tiles_16_for web18
    Hold hold_2 = new Hold("11212", false, false, new Connector[]{new Connector(2, true), new Connector(1, true),
            new Connector(3, true), new Connector(1, true),}, 2, false); // GT-new_tiles_16_for web19
    Hold hold_3 = new Hold("11213", false, false, new Connector[]{new Connector(2, true), new Connector(3, true),
            new Connector(0, true), new Connector(1, true),}, 2, false); // GT-new_tiles_16_for web20
    Hold hold_4 = new Hold("11214", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web21
    Hold hold_5 = new Hold("11215", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web22
    Hold hold_6 = new Hold("11216", false, false, new Connector[]{new Connector(0, true), new Connector(1, true),
            new Connector(0, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web23
    Hold hold_7 = new Hold("11217", false, false, new Connector[]{new Connector(0, true), new Connector(2, true),
            new Connector(0, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web24
    Hold hold_8 = new Hold("11218", false, false, new Connector[]{new Connector(2, true), new Connector(1, true),
            new Connector(2, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web25
    Hold hold_9 = new Hold("11219", false, false, new Connector[]{new Connector(3, true), new Connector(0, true),
            new Connector(0, true), new Connector(3, true),}, 2, false); // GT-new_tiles_16_for web26
    Hold hold_10 = new Hold("11220", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(1, true),}, 3, false); // GT-new_tiles_16_for web27
    Hold hold_11 = new Hold("11221", false, false, new Connector[]{new Connector(1, true), new Connector(0, true),
            new Connector(1, true), new Connector(0, true),}, 3, false); // GT-new_tiles_16_for web28
    Hold hold_12 = new Hold("11222", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(2, true),}, 3, false); // GT-new_tiles_16_for web29
    Hold hold_13 = new Hold("11223", false, false, new Connector[]{new Connector(2, true), new Connector(0, true),
            new Connector(0, true), new Connector(2, true),}, 3, false); // GT-new_tiles_16_for web30
    Hold hold_14 = new Hold("11224", false, false, new Connector[]{new Connector(1, true), new Connector(0, true),
            new Connector(2, true), new Connector(1, true),}, 3, false); // GT-new_tiles_16_for web31
    Hold hold_15 = new Hold("11225", false, false, new Connector[]{new Connector(2, true), new Connector(0, true),
            new Connector(1, true), new Connector(2, true),}, 3, false); // GT-new_tiles_16_for web32
    Hold hold_16 = new Hold("11226", false, false, new Connector[]{new Connector(0, true), new Connector(2, true),
            new Connector(1, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web62
    Hold hold_17 = new Hold("11227", false, false, new Connector[]{new Connector(0, true), new Connector(3, true),
            new Connector(0, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web63
    Hold hold_18 = new Hold("11228", false, false, new Connector[]{new Connector(1, true), new Connector(1, true),
            new Connector(1, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web64
    Hold hold_19 = new Hold("11229", false, false, new Connector[]{new Connector(2, true), new Connector(1, true),
            new Connector(0, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web65
    Hold hold_20 = new Hold("11230", false, false, new Connector[]{new Connector(2, true), new Connector(2, true),
            new Connector(2, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web66
    Hold hold_21 = new Hold("11231", false, false, new Connector[]{new Connector(3, true), new Connector(0, true),
            new Connector(0, true), new Connector(3, true),}, 1, true); // GT-new_tiles_16_for web67
    Hold hold_22 = new Hold("11232", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(1, true),}, 2, true); // GT-new_tiles_16_for web68
    Hold hold_23 = new Hold("11233", false, false, new Connector[]{new Connector(0, true), new Connector(2, true),
            new Connector(0, true), new Connector(1, true),}, 2, true); // GT-new_tiles_16_for web69
    Hold hold_24 = new Hold("11234", false, false, new Connector[]{new Connector(0, true), new Connector(0, true),
            new Connector(0, true), new Connector(2, true),}, 2, true); // GT-new_tiles_16_for web70


    public LoadHold() {
        this.holds = new ArrayList<>();
        holds.add(hold_1);
        holds.add(hold_2);
        holds.add(hold_3);
        holds.add(hold_4);
        holds.add(hold_5);
        holds.add(hold_6);
        holds.add(hold_7);
        holds.add(hold_8);
        holds.add(hold_9);
        holds.add(hold_10);
        holds.add(hold_11);
        holds.add(hold_12);
        holds.add(hold_13);
        holds.add(hold_14);
        holds.add(hold_15);
        holds.add(hold_16);
        holds.add(hold_17);
        holds.add(hold_18);
        holds.add(hold_19);
        holds.add(hold_20);
        holds.add(hold_21);
        holds.add(hold_22);
        holds.add(hold_23);
        holds.add(hold_24);
    }

    public ArrayList<Hold> getHolds() {
        return this.holds;
    }
}
