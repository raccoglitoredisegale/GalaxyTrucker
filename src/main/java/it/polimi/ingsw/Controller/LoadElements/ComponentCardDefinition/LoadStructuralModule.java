package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.StructuralModule;

import java.util.ArrayList;

public class LoadStructuralModule {

    // web59
    private final ArrayList<StructuralModule> structuralModules;
    /**
     * first number (1) --> component card second and third number (13) --> type of
     * component card, hold last two number (11) --> sequence of component card
     */
    StructuralModule structuralModule_1 = new StructuralModule("11311", false, false, new Connector[]{
            new Connector(3, true),
            new Connector(3, true),
            new Connector(0, true),
            new Connector(1, true),}); // GT-new_tiles_16_for
    StructuralModule structuralModule_2 = new StructuralModule("11312", false, false, new Connector[]{
            new Connector(1, true),
            new Connector(3, true),
            new Connector(0, true),
            new Connector(3, true),}); // GT-new_tiles_16_for
    // web53
    StructuralModule structuralModule_3 = new StructuralModule("11313", false, false, new Connector[]{
            new Connector(1, true),
            new Connector(3, true),
            new Connector(1, true),
            new Connector(3, true),}); // GT-new_tiles_16_for
    // web54
    StructuralModule structuralModule_4 = new StructuralModule("11314", false, false, new Connector[]{
            new Connector(1, true),
            new Connector(3, true),
            new Connector(2, true),
            new Connector(3, true),}); // GT-new_tiles_16_for
    // web55
    StructuralModule structuralModule_5 = new StructuralModule("11315", false, false, new Connector[]{
            new Connector(2, true),
            new Connector(3, true),
            new Connector(0, true),
            new Connector(3, true),}); // GT-new_tiles_16_for
    // web56
    StructuralModule structuralModule_6 = new StructuralModule("11316", false, false, new Connector[]{
            new Connector(3, true),
            new Connector(1, true),
            new Connector(2, true),
            new Connector(3, true),}); // GT-new_tiles_16_for
    // web57
    StructuralModule structuralModule_7 = new StructuralModule("11317", false, false, new Connector[]{
            new Connector(3, true), new Connector(2, true), new Connector(0, true), new Connector(3, true),}); // GT-new_tiles_16_for
    // web58
    StructuralModule structuralModule_8 = new StructuralModule("11318", false, false, new Connector[]{
            new Connector(3, true), new Connector(2, true), new Connector(2, true), new Connector(3, true),}); // GT-new_tiles_16_for
    // web60

    public LoadStructuralModule() {
        structuralModules = new ArrayList<>();
        structuralModules.add(structuralModule_1);
        structuralModules.add(structuralModule_2);
        structuralModules.add(structuralModule_3);
        structuralModules.add(structuralModule_4);
        structuralModules.add(structuralModule_5);
        structuralModules.add(structuralModule_6);
        structuralModules.add(structuralModule_7);
        structuralModules.add(structuralModule_8);
    }

    public ArrayList<StructuralModule> getStructuralModules() {
        return this.structuralModules;
    }
}
