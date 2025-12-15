package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedShip;

import java.util.ArrayList;

public class LoadAbandonedShip {

    private final ArrayList<AbandonedShip> abandonedShips;

    AbandonedShip abandonedShip_1 = new AbandonedShip(21115, 1, false, false, 3, 1, 4); // GT-cards_I_IT_0118
    AbandonedShip abandonedShip_2 = new AbandonedShip(21116, 1, false, false, 2, 1, 3); // GT-cards_I_IT_0117
    AbandonedShip abandonedShip_3 = new AbandonedShip(21117, 2, false, false, 4, 1, 6); // GT-cards_II_IT_0117
    AbandonedShip abandonedShip_4 = new AbandonedShip(21118, 2, false, false, 5, 2, 8); // GT-cards_II_IT_0118

    public LoadAbandonedShip() {
        abandonedShips = new ArrayList<>();
        abandonedShips.add(abandonedShip_1);
        abandonedShips.add(abandonedShip_2);
        abandonedShips.add(abandonedShip_3);
        abandonedShips.add(abandonedShip_4);
    }

    public ArrayList<AbandonedShip> getAbandonedShips() {
        return abandonedShips;
    }
}
