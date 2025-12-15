package it.polimi.ingsw.Controller.LoadElements;

import it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition.*;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadAdventureCards {
    private ArrayList<Integer> idsOfAdventureCardL1;
    private ArrayList<Integer> idsOfAdventureCardL2;

    public LoadAdventureCards() {
    }

    public ArrayList<Integer> getIdsOfAdventureCardCardL1() {
        this.idsOfAdventureCardL1 = new ArrayList<>(Arrays.asList(
                // AbandonedShip IDs
                21115, 21116,

                // AbandonedStation
                21119, 21120,

                // MeteorSwarm
                21131, 21132, 21133,

                // OpenSpace
                21124, 21125, 21126, 21127,

                // Pirates
                21136,

                // OpenSpace
                21124, 21125, 21126, 21127,

                // Warzone
                21148, 21149,

                // Planets
                21138, 21139, 21140, 21141,

                // Slavers
                21113,

                // Smugglers
                21112,

                // Stardust
                21146
        ));
        return idsOfAdventureCardL1;
    }

    public ArrayList<Integer> getIdsOfAdventureCardCardL2() {
        this.idsOfAdventureCardL2 = new ArrayList<>(Arrays.asList(
                // AbandonedShip
                21117, 21118,

                // AbandonedStation
                21121, 21122,

                // Epidemic
                21123,

                // MeteorSwarm
                21134, 21135, 21136,

                // OpenSpace
                21128, 21129, 21130,

                // Pirates
                21137,

                // OpenSpace
                21128, 21129, 21130,

                // Planets
                21142, 21143, 21144, 21145,

                // Slavers
                21114,

                // Smugglers
                21111,

                // Stardust
                21147
        ));
        return idsOfAdventureCardL2;
    }

    public ArrayList<AbandonedShip> getAbandonedShips() {
        return new LoadAbandonedShip().getAbandonedShips();
    }

    public ArrayList<AbandonedStation> getAbandonedStations() {
        return new LoadAbandonedStation().getAbandonedStations();
    }

    public ArrayList<Epidemic> getEpidemics() {
        return new LoadEpidemic().getEpidemics();
    }

    public ArrayList<MeteorSwarm> getMeteorSwarms() {
        return new LoadMeteorSwarm().getMeteorSwarms();
    }

    public ArrayList<OpenSpace> getOpenSpaces() {
        return new LoadOpenSpace().getOpenSpaces();
    }

    public ArrayList<Pirates> getPirates() {
        return new LoadPirates().getPirates();
    }

    public ArrayList<Planets> getPlanets() {
        return new LoadPlanets().getPlanets();
    }

    public ArrayList<Slavers> getSlavers() {
        return new LoadSlavers().getSlavers();
    }

    public ArrayList<Smugglers> getSmugglers() {
        return new LoadSmugglers().getSmugglers();
    }

    public ArrayList<Stardust> getStardusts() {
        return new LoadStardust().getStardusts();
    }

    public ArrayList<Warzone> getWarzones() {
        return new LoadWarzone().getWarzones();
    }
}
