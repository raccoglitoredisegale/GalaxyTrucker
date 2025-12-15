package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.Shipboard.Shipboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hold extends ComponentCard implements Serializable {

    private final int capacity;
    private final boolean special;
    private final List<Goods> load;
    private int currentCargo;

    public Hold(String id, boolean IsValid, boolean occupation, Connector[] connectors, int capacity, boolean special) {
        super(id, IsValid, occupation, connectors);
        this.capacity = capacity;
        this.currentCargo = 0;
        this.special = special;
        this.load = new ArrayList<>();
    }

    public void goodsLoad(Goods goods) {
        if (goods == Goods.RED && !special) {
            return;
        }
        if (capacity > 0 && load.size() < capacity) {
            load.add(goods);
            currentCargo++;
        }
    }

    public void clearLoad() {
        load.clear();
        currentCargo = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCargo() {
        return currentCargo;
    }

    public boolean isSpecial() {
        return special;
    }

    public List<Goods> getLoad() {
        return load;
    }

    public void removeGood(Goods goods) {
        load.remove(goods);
        currentCargo--;
    }

    @Override
    public void getTypeAdd(Shipboard ship, Integer[] toAdd) {
        ship.addToStorageList(this, toAdd);
    }

    @Override
    public void getTypeRemove(Shipboard ship, Integer[] toRemove) {
        ship.removeToStorageList(this, toRemove);
    }

    @Override
    public String getShortName() {
        return "HLD";
    }
}
