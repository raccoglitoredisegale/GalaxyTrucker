package it.polimi.ingsw.Controller.LoadElements;

import it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition.*;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadComponentCards {
    private ArrayList<String> idsOfComponetCard;

    public LoadComponentCards() {
    }

    /**
     * IDs Legend:
     * 11 ==> Battery Compartment
     * 12 ==> Hold
     * 13 ==> Structural Module
     * 14 ==> Engine
     * 15 ==> Cannon
     * 16 ==> Shield Generator
     * 17 ==> Alien Life
     * 18 ==> Cabin
     */
    public ArrayList<String> getIdsOfComponentCard() {
        this.idsOfComponetCard = new ArrayList<>(Arrays.asList(
                // AlienLifeSupport IDs
                "11711", "11712", "11713", "11714", "11715", "11716", "11717", "11718", "11719", "11720", "11721", "11722",

                // BatteryCompartment IDs
                "11111", "11112", "11113", "11114", "11115", "11116", "11117", "11118", "11119", "11120",
                "11121", "11122", "11123", "11124", "11125", "11126", "11127",

                // Cabin IDs
                "11811", "11812", "11813", "11814", "11815", "11816", "11817", "11818", "11819", "11820", "11821", "11822",
                "11823", "11824", "11825", "11826", "11827", "11828", "11829", "11830", "11831",

                // Cannon IDs
                "11511", "11512", "11513", "11514", "11515", "11516", "11517", "11518", "11519", "11520", "11521", "11522",
                "11523", "11524", "11525", "11526", "11527", "11528", "11529", "11530", "11531", "11532", "11533", "11534",
                "11535", "11536", "11537", "11538", "11539", "11540", "11541", "11542", "11543", "11544", "11545", "11546",

                // Engine IDs
                "11411", "11412", "11413", "11414", "11415", "11416", "11417", "11418", "11419", "11420", "11421", "11422",
                "11423", "11424", "11425", "11426", "11427", "11428", "11429", "11430", "11431", "11432", "11433", "11434",
                "11435", "11436", "11437", "11438", "11439", "11440",

                // Hold IDs
                "11211", "11212", "11213", "11214", "11215", "11216", "11217", "11218", "11219", "11220", "11221", "11222",
                "11223", "11224", "11225", "11226", "11227", "11228", "11229", "11230", "11231", "11232", "11233", "11234",

                // ShieldGenerator IDs
                "11611", "11612", "11613", "11614", "11615", "11616", "11617", "11618",

                // StructuralModules IDs
                "11311", "11312", "11313", "11314", "11315", "11316", "11317", "11318"
        ));
        return idsOfComponetCard;
    }

    public ArrayList<ShieldGenerator> getShieldGenerators() {
        return new LoadShieldGenerator().getShieldGenerators();
    }

    public ArrayList<AlienLifeSupport> getAlienLifeSupports() {
        return new LoadAlienLifeSupport().getAlienLifeSupports();
    }

    public ArrayList<BatteryCompartment> getBatteryCompartments() {
        return new LoadBatteryCompartment().getBatteryCompartments();
    }

    public ArrayList<Cabin> getCabins() {
        return new LoadCabin().getCabins();
    }

    public ArrayList<Cabin> getStartingCabins() {
        return new LoadCabin().getStartingCabins();
    }

    public ArrayList<Cannon> getCannons() {
        return new LoadCannon().getCannons();
    }

    public ArrayList<Engine> getEngines() {
        return new LoadEngine().getEngines();
    }

    public ArrayList<Hold> getHolds() {
        return new LoadHold().getHolds();
    }

    public ArrayList<StructuralModule> getStructuralModules() {
        return new LoadStructuralModule().getStructuralModules();
    }

}
