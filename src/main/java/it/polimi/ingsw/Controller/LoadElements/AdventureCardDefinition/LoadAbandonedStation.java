package it.polimi.ingsw.Controller.LoadElements.AdventureCardDefinition;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AbandonedStation;
import it.polimi.ingsw.Model.ComponentCard.Goods;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadAbandonedStation {

    private final ArrayList<AbandonedStation> abandonedStations;
    AbandonedStation abandoned_station1 = new AbandonedStation(21119, 1, false, false, 5, 1,
            new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.GREEN))); // GT-cards_I_IT_0119
    AbandonedStation abandoned_station2 = new AbandonedStation(21120, 1, false, false, 6, 1,
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.RED))); // GT-cards_I_IT_0120
    AbandonedStation abandoned_station3 = new AbandonedStation(21121, 2, false, false, 7, 1,
            new ArrayList<Goods>(Arrays.asList(Goods.RED, Goods.YELLOW))); // GT-cards_II_IT_0119
    AbandonedStation abandoned_station4 = new AbandonedStation(21122, 2, false, false, 8, 2,
            new ArrayList<Goods>(Arrays.asList(Goods.YELLOW, Goods.YELLOW, Goods.GREEN))); // GT-cards_II_IT_0120

    public LoadAbandonedStation() {
        this.abandonedStations = new ArrayList<>();
        this.abandonedStations.add(abandoned_station1);
        this.abandonedStations.add(abandoned_station2);
        this.abandonedStations.add(abandoned_station3);
        this.abandonedStations.add(abandoned_station4);
    }

    public ArrayList<AbandonedStation> getAbandonedStations() {
        return abandonedStations;
    }
}
