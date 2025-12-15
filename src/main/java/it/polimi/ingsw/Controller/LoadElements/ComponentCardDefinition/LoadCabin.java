package it.polimi.ingsw.Controller.LoadElements.ComponentCardDefinition;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.OccupantType;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;

import java.util.ArrayList;

public class LoadCabin {

    private final ArrayList<Cabin> cabins;

    private final ArrayList<Cabin> startingCabins;

    /**
     * first number (1) --> component card second and third number (18) --> type of
     * component card last two number (11) --> sequence of component card
     */

    // STARTING CABIN
    Cabin cabin_1 = new Cabin("11811", false, false,
            new Connector[]{
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),},
            2, OccupantType.HUMAN, true); // GT-new_tiles_16_for web33

    // STARTING CABIN
    Cabin cabin_2 = new Cabin("11812", false, false,
            new Connector[]{
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, true); // GT-new_tiles_16_for web34

    // STARTING CABIN
    Cabin cabin_20 = new Cabin("11830", false, false,
            new Connector[]{
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, true); // GT-new_tiles_16_for web52

    // STARTING CABIN
    Cabin cabin_21 = new Cabin("11831", false, false,
            new Connector[]{
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, true); // GT-new_tiles_16_for web61

    Cabin cabin_3 = new Cabin("11813", false, false,
            new Connector[]{
                    new Connector(0, true),
                    new Connector(0, true),
                    new Connector(3, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web35

    Cabin cabin_4 = new Cabin("11814", false, false,
            new Connector[]{
                    new Connector(1, true),
                    new Connector(2, true),
                    new Connector(1, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web36

    Cabin cabin_5 = new Cabin("11815", false, false,
            new Connector[]{
                    new Connector(1, true),
                    new Connector(2, true),
                    new Connector(2, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web37

    Cabin cabin_6 = new Cabin("11816", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(0, true),
                    new Connector(0, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web38

    Cabin cabin_7 = new Cabin("11817", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(1, true),
                    new Connector(0, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web39

    Cabin cabin_8 = new Cabin("11818", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(1, true),
                    new Connector(2, true),
                    new Connector(1, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web40

    Cabin cabin_9 = new Cabin("11819", false, false,
            new Connector[]{
                    new Connector(0, true),
                    new Connector(1, true),
                    new Connector(3, true),
                    new Connector(2, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web41

    Cabin cabin_10 = new Cabin("11820", false, false,
            new Connector[]{
                    new Connector(1, true),
                    new Connector(2, true),
                    new Connector(0, true),
                    new Connector(2, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web42

    Cabin cabin_11 = new Cabin("11821", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(1, true),
                    new Connector(2, true),
                    new Connector(2, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web43

    Cabin cabin_12 = new Cabin("11822", false, false,
            new Connector[]{
                    new Connector(3, true),
                    new Connector(0, true),
                    new Connector(0, true),
                    new Connector(2, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web44

    Cabin cabin_13 = new Cabin("11823", false, false,
            new Connector[]{
                    new Connector(0, true),
                    new Connector(1, true),
                    new Connector(0, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web45

    Cabin cabin_14 = new Cabin("11824", false, false,
            new Connector[]{
                    new Connector(0, true),
                    new Connector(1, true),
                    new Connector(1, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web46

    Cabin cabin_15 = new Cabin("11825", false, false,
            new Connector[]{
                    new Connector(0, true),
                    new Connector(2, true),
                    new Connector(0, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web47

    Cabin cabin_16 = new Cabin("11826", false, false,
            new Connector[]{
                    new Connector(1, true),
                    new Connector(0, true),
                    new Connector(1, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web48

    Cabin cabin_17 = new Cabin("11827", false, false,
            new Connector[]{
                    new Connector(1, true),
                    new Connector(0, true),
                    new Connector(2, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web49

    Cabin cabin_18 = new Cabin("11828", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(0, true),
                    new Connector(2, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web50

    Cabin cabin_19 = new Cabin("11829", false, false,
            new Connector[]{
                    new Connector(2, true),
                    new Connector(2, true),
                    new Connector(0, true),
                    new Connector(3, true),
            }, 2, OccupantType.HUMAN, false); // GT-new_tiles_16_for web51


    public LoadCabin() {
        this.cabins = new ArrayList<>();
        this.startingCabins = new ArrayList<>();

        startingCabins.add(cabin_1); // BLUE
        startingCabins.add(cabin_2); // GREEN
        startingCabins.add(cabin_20); // RED
        startingCabins.add(cabin_21); // YELLOW

        cabins.add(cabin_3);
        cabins.add(cabin_4);
        cabins.add(cabin_5);
        cabins.add(cabin_6);
        cabins.add(cabin_7);
        cabins.add(cabin_8);
        cabins.add(cabin_9);
        cabins.add(cabin_10);
        cabins.add(cabin_11);
        cabins.add(cabin_12);
        cabins.add(cabin_13);
        cabins.add(cabin_14);
        cabins.add(cabin_15);
        cabins.add(cabin_16);
        cabins.add(cabin_17);
        cabins.add(cabin_18);
        cabins.add(cabin_19);
    }

    public ArrayList<Cabin> getCabins() {
        return this.cabins;
    }

    public ArrayList<Cabin> getStartingCabins() {
        return this.startingCabins;
    }
}
