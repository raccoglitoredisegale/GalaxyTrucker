package it.polimi.ingsw.Model.Shipboard;

import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.*;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Network.Client.Exception.InputException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Shipboard implements Serializable {

    private final int level;

    private final int rowLength;

    private final int columnLength;
    private final ComponentCard[][] ship;
    private final boolean[][] validPosition;
    private final ArrayList<Integer[]> cannonList;
    private final ArrayList<Integer[]> engineList;
    private final ArrayList<Integer[]> storageList;
    private final ArrayList<Integer[]> batteryList;
    private final ArrayList<Integer[]> cabinList;
    private final ArrayList<Integer[]> shieldList;
    private final ArrayList<Integer[]> alienLifeSupportList;
    private final ArrayList<ComponentCard> savedComponentCards;
    private final ArrayList<ComponentCard> discardPile;
    private Color color;

    public Shipboard(int level) {
        this.level = level;
        this.rowLength = getRowLength(level);
        this.columnLength = getColumnLength(level);

        this.validPosition = new boolean[this.columnLength][this.rowLength];

        if (level == 0 || level == 1) {
            validPosition[0][3] = true;

            validPosition[1][2] = true;
            validPosition[1][3] = true;
            validPosition[1][4] = true;

            validPosition[2][1] = true;
            validPosition[2][2] = true;
            validPosition[2][3] = true;
            validPosition[2][4] = true;
            validPosition[2][5] = true;

            validPosition[3][1] = true;
            validPosition[3][2] = true;
            validPosition[3][3] = true;
            validPosition[3][4] = true;
            validPosition[3][5] = true;

            validPosition[4][1] = true;
            validPosition[4][2] = true;
            validPosition[4][4] = true;
            validPosition[4][5] = true;
        } else if (level == 2) {
            validPosition[0][2] = true;
            validPosition[0][4] = true;

            validPosition[1][1] = true;
            validPosition[1][2] = true;
            validPosition[1][3] = true;
            validPosition[1][4] = true;
            validPosition[1][5] = true;

            validPosition[2][0] = true;
            validPosition[2][1] = true;
            validPosition[2][2] = true;
            validPosition[2][3] = true;
            validPosition[2][4] = true;
            validPosition[2][5] = true;
            validPosition[2][6] = true;

            validPosition[3][0] = true;
            validPosition[3][1] = true;
            validPosition[3][2] = true;
            validPosition[3][3] = true;
            validPosition[3][4] = true;
            validPosition[3][5] = true;
            validPosition[3][6] = true;

            validPosition[4][0] = true;
            validPosition[4][1] = true;
            validPosition[4][2] = true;
            validPosition[4][4] = true;
            validPosition[4][5] = true;
            validPosition[4][6] = true;
        }

        this.ship = new ComponentCard[columnLength][rowLength];

        this.cannonList = new ArrayList<Integer[]>();
        this.engineList = new ArrayList<Integer[]>();
        this.storageList = new ArrayList<Integer[]>();
        this.batteryList = new ArrayList<Integer[]>();
        this.cabinList = new ArrayList<Integer[]>();
        this.shieldList = new ArrayList<Integer[]>();
        this.alienLifeSupportList = new ArrayList<Integer[]>();
        this.savedComponentCards = new ArrayList<>();
        this.discardPile = new ArrayList<>();

    }

    public void addToSavedComponentCards(ComponentCard componentCard) {
        this.savedComponentCards.add(componentCard);
    }

    public ArrayList<ComponentCard> getSavedComponentCards() {
        return this.savedComponentCards;
    }

    public ComponentCard[][] getShip() {
        return ship;
    }

    public ComponentCard getShipComponent(int col, int row) {
        return ship[col][row];
    }

    public int getLevel() {
        return level;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRowLength(int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level must be greater than 0");
        }
        if (level == 2) {
            return 7;
        }
        throw new IllegalArgumentException("level must be at most 2");
    }

    public int getColumnLength(int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level must be greater than 0");
        }
        if (level == 2) {
            return 5;
        }
        throw new IllegalArgumentException("level must be at most 2");
    }

    /**
     * validPosition[x][y]
     * <p>
     * [[vp0], [vp1], [vp2], [vp3], [vp4]]
     * <p>
     * [vp0] = [false, false, true, false, true, false, false]
     * [vp1] = [false, true, true, true, true, true, false]
     * [vp2] = [true, true, true, true, true, true, true]
     * [vp3] = [true, true, true, true, true, true, true]
     * [vp4] = [true, true, true, false, true, true, true]
     */
    public void addComponent(ComponentCard component, int x, int y) throws Exception {
        if (x >= 0 && x < columnLength && y >= 0 && y < rowLength && validPosition[x][y]) {
            if (ship[x][y] != null) {
                throw new InputException("Your shipboard is already occupied at that position");
            }
            ship[x][y] = component;
            validateComponent(component, x, y);
            Integer[] toAdd = new Integer[2];
            toAdd[0] = x;
            toAdd[1] = y;
            component.getTypeAdd(this, toAdd);

        } else {
            throw new InputException("Invalid position (x;y)!");
        }
    }

    public void removeComponent(int x, int y) {
        if (x >= 0 && x < columnLength && y >= 0 && y < rowLength && ship[x][y] != null) {
            ComponentCard component = ship[x][y];
            ship[x][y] = null;
            Integer[] toRemove = new Integer[2];
            toRemove[0] = x;
            toRemove[1] = y;
            component.getTypeRemove(this, toRemove);
            if (x - 1 >= 0 && ship[x - 1][y] != null && ship[x - 1][y].isValid()) {
                validateComponent(getShipComponent(x - 1, y), x - 1, y);
            }
            if (x + 1 < 5 && ship[x + 1][y] != null && ship[x + 1][y].isValid()) {
                validateComponent(getShipComponent(x + 1, y), x + 1, y);
            }
            if (y - 1 >= 0 && ship[x][y - 1] != null && ship[x][y - 1].isValid()) {
                validateComponent(getShipComponent(x, y - 1), x, y - 1);
            }
            if (y + 1 < 7 && ship[x][y + 1] != null && ship[x][y + 1].isValid()) {
                validateComponent(getShipComponent(x, y + 1), x, y + 1);
            }
            validateComponentAfterAssemblyPhase();

        } else {
            throw new RuntimeException("invalid index");
        }
    }


    private void validateComponent(ComponentCard component, int x, int y) {
        // you take a dx and dy to set a direction when using for below.  for example dir = 1 you have x + 1 and y that means you are pointing E that is correct since int the connectors array is 0->N, 1->E, 2->S, 3->W
        int[] dy = {0, 1, 0, -1};
        int[] dx = {-1, 0, 1, 0};
        boolean isValid = true;
        boolean alone = false;

        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // out of bounds you consider valid
            if (nx < 0 || ny < 0 || nx >= columnLength || ny >= rowLength) continue; // column = 5 and row = 7

            ComponentCard neighbor = getShipComponent(nx, ny);
            if (neighbor == null) continue;

            int myTube = component.getConnectors()[dir].getTubeNumber();
            int neighborTube = neighbor.getConnectors()[(dir + 2) % 4].getTubeNumber(); //neighbor faces the opposite direction

            if (myTube == 3 || neighborTube == 3) continue;

            if (myTube != neighborTube) {
                isValid = false;
                break;
            }
        }
        component.setValid(isValid);
    }

    private void validateEngine(int x, int y) {
        Engine engine = (Engine) getShipComponent(x, y); // dont touch x e y inverted
        if (engine.getFacingDirectionIndex() != 2) {
            engine.setValid(false);
        }
    }

    private void validateCannon(int x, int y) {
        Cannon cannon = (Cannon) getShipComponent(x, y);
        int dir = cannon.getFacingDirectionIndex();

        int[] dy = {0, 1, 0, -1};
        int[] dx = {-1, 0, 1, 0};

        int nx = x + dx[dir];
        int ny = y + dy[dir];

        if (nx < 0 || ny < 0 || nx >= columnLength || ny >= rowLength) return;

        ComponentCard inFront = getShipComponent(nx, ny);

        if (inFront != null) {
            cannon.setValid(false);
        }
    }


    public void validateComponentAfterAssemblyPhase() {
        for (int row = 0; row < this.ship.length; row++) {
            for (int col = 0; col < this.ship[row].length; col++) {
                if (this.ship[row][col] != null) {
                    if (!this.ship[row][col].isValid()) {
                        discardPile.add(this.ship[row][col]);
                        removeComponent(row, col);
                    }
                    boolean[][] visited = new boolean[this.ship.length][this.ship[0].length];
                    if (this.ship[row][col] != null && !findPath(row, col, visited)) {
                        discardPile.add(this.ship[row][col]);
                        removeComponent(row, col);
                        validateComponentAfterAssemblyPhase();
                        return;
                    }
                }
            }
        }
    }

    /**
     * the basic idea is to find a valid 'way' (checking all connections) that can take me to the center of the ship's dashboard
     */
    private boolean findPath(int row, int col, boolean[][] visited) {
        // Base case: reached the center
        if (row == 2 && col == 3) {
            return true;
        }

        // Base case: out of bounds or already visited
        if (row < 0 || row >= this.ship.length ||
                col < 0 || col >= this.ship[0].length ||
                visited[row][col] || this.ship[row][col] == null) {
            return false;
        }

        // Mark as visited
        visited[row][col] = true;

        ComponentCard currComponent = this.ship[row][col];

        // Check DOWN (row+1)
        if (row < this.ship.length - 1 &&
                this.ship[row + 1][col] != null &&
                checkNeighbourConnection(2, row, col)) { // Note: [2] for opposite direction
            if (findPath(row + 1, col, visited)) {
                return true;
            }
        }

        // Check RIGHT (col+1)
        if (col < this.ship[0].length - 1 &&
                this.ship[row][col + 1] != null &&
                checkNeighbourConnection(1, row, col)) { // Note: [3] for opposite direction
            if (findPath(row, col + 1, visited)) {
                return true;
            }
        }

        // Check UP (row-1)
        if (row > 0 &&
                this.ship[row - 1][col] != null &&
                checkNeighbourConnection(0, row, col)) {
            if (findPath(row - 1, col, visited)) {
                return true;
            }
        }

        // Check LEFT (col-1)
        if (col > 0 &&
                this.ship[row][col - 1] != null &&
                checkNeighbourConnection(3, row, col)) {
            return findPath(row, col - 1, visited);
        }

        return false;
    }

    /**
     * 0 = nord
     * 1 = dx
     * 2 = sud
     * 3 = sx
     */
    public boolean checkNeighbourConnection(int dir, int row, int col) {
        switch (dir) {
            case 0:
                // CHECK UP
                // [ROW-1][COL]
                //      ^
                //      |
                // [ROW][COL]
                if (this.ship[row - 1][col].getConnectors()[2].getTubeNumber() == 3 && this.ship[row][col].getConnectors()[0].getTubeNumber() > 0
                        || this.ship[row - 1][col].getConnectors()[2].getTubeNumber() > 0 && this.ship[row][col].getConnectors()[0].getTubeNumber() == 3) {
                    return true;
                } else if (this.ship[row - 1][col].getConnectors()[2].getTubeNumber() == 2 && this.ship[row][col].getConnectors()[0].getTubeNumber() == 2) {
                    return true;
                } else if (this.ship[row - 1][col].getConnectors()[2].getTubeNumber() == 1 && this.ship[row][col].getConnectors()[0].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 1:
                // CHECK RIGHT
                // [ROW][COL] --> [ROW][COL+1]
                if (this.ship[row][col + 1].getConnectors()[3].getTubeNumber() == 3 && this.ship[row][col].getConnectors()[1].getTubeNumber() > 0
                        || this.ship[row][col + 1].getConnectors()[3].getTubeNumber() > 0 && this.ship[row][col].getConnectors()[1].getTubeNumber() == 3) {
                    return true;
                } else if (this.ship[row][col + 1].getConnectors()[3].getTubeNumber() == 2 && this.ship[row][col].getConnectors()[1].getTubeNumber() == 2) {
                    return true;
                } else if (this.ship[row][col + 1].getConnectors()[3].getTubeNumber() == 1 && this.ship[row][col].getConnectors()[1].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 2:
                // CHECK DOWN
                // [ROW][COL]
                //      |
                //      |
                // [ROW+1][COL]
                if (this.ship[row + 1][col].getConnectors()[3].getTubeNumber() == 3 && this.ship[row][col].getConnectors()[2].getTubeNumber() > 0
                        || this.ship[row + 1][col].getConnectors()[3].getTubeNumber() > 0 && this.ship[row][col].getConnectors()[2].getTubeNumber() == 3) {
                    return true;
                } else if (this.ship[row + 1][col].getConnectors()[3].getTubeNumber() == 2 && this.ship[row][col].getConnectors()[2].getTubeNumber() == 2) {
                    return true;
                } else if (this.ship[row + 1][col].getConnectors()[3].getTubeNumber() == 1 && this.ship[row][col].getConnectors()[2].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 3:
                // CHECK RIGHT
                // [ROW][COL-1] <-- [ROW][COL]
                if (this.ship[row][col - 1].getConnectors()[1].getTubeNumber() == 3 && this.ship[row][col].getConnectors()[3].getTubeNumber() > 0
                        || this.ship[row][col - 1].getConnectors()[1].getTubeNumber() > 0 && this.ship[row][col].getConnectors()[3].getTubeNumber() == 3) {
                    return true;
                } else if (this.ship[row][col - 1].getConnectors()[1].getTubeNumber() == 2 && this.ship[row][col].getConnectors()[3].getTubeNumber() == 2) {
                    return true;
                } else if (this.ship[row][col - 1].getConnectors()[1].getTubeNumber() == 1 && this.ship[row][col].getConnectors()[3].getTubeNumber() == 1) {
                    return true;
                }
                break;
        }
        return false;
    }

    public ArrayList<ComponentCard> getDiscardedComponentCards() {
        return this.discardPile;
    }

    public void addToShieldList(ShieldGenerator shieldGenerator, Integer[] toAdd) {
        shieldList.add(toAdd);
    }

    public void addToCannonList(Cannon cannon, Integer[] toAdd) {
        cannonList.add(toAdd);
        validateCannon(toAdd[0], toAdd[1]);
    }

    public void addToEngineList(Engine engine, Integer[] toAdd) {
        engineList.add(toAdd);
        validateEngine(toAdd[0], toAdd[1]);
    }

    public void addToStorageList(Hold hold, Integer[] toAdd) {
        storageList.add(toAdd);
    }

    public void addToBatteryList(BatteryCompartment batteryCompartment, Integer[] toAdd) {
        batteryList.add(toAdd);
    }

    public void addToCabinList(Cabin cabin, Integer[] toAdd) {
        cabinList.add(toAdd);
    }

    public void addToAlienLifeSupportList(AlienLifeSupport alienLifeSupport, Integer[] toAdd) {
        alienLifeSupportList.add(toAdd);
    }


    public void removeToShieldList(ShieldGenerator shieldGenerator, Integer[] toRemove) {
        removeArrayFromList(shieldList, toRemove);
    }

    public void removeToCabinList(Cabin cabin, Integer[] toRemove) {
        removeArrayFromList(cabinList, toRemove);
    }

    public void removeToEngineList(Engine engine, Integer[] toRemove) {
        removeArrayFromList(engineList, toRemove);
    }

    public void removeToStorageList(Hold hold, Integer[] toRemove) {
        removeArrayFromList(storageList, toRemove);
    }

    public void removeToBatteryList(BatteryCompartment batteryCompartment, Integer[] toRemove) {
        removeArrayFromList(batteryList, toRemove);
    }

    public void removeToCannonList(Cannon cannon, Integer[] toRemove) {
        removeArrayFromList(cannonList, toRemove);
    }

    public void removeToAlienLifeSupportList(AlienLifeSupport alienLifeSupport, Integer[] toRemove) {
        removeArrayFromList(alienLifeSupportList, toRemove);
    }

    public void removeArrayFromList(List<Integer[]> list, Integer[] toRemove) {
        list.removeIf(arr -> arr.length == toRemove.length &&
                arr[0].equals(toRemove[0]) &&
                arr[1].equals(toRemove[1]));
    }

    public ArrayList<Integer[]> getCannonList() {
        return cannonList;
    }

    public ArrayList<Integer[]> getEngineList() {
        return engineList;
    }

    public ArrayList<Integer[]> getStorageList() {
        return storageList;
    }

    public ArrayList<Integer[]> getBatteryList() {
        return batteryList;
    }

    public ArrayList<Integer[]> getCabinList() {
        return cabinList;
    }

    public ArrayList<Integer[]> getShieldList() {
        return shieldList;
    }

    public ArrayList<Integer[]> getAlienLifeSupportList() {
        return alienLifeSupportList;
    }


    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < getRowLength(level) && y >= 0 && y < getColumnLength(level);
    }

    public void updateExposedConnectors() {
        for (int x = 0; x < rowLength; x++) {
            for (int y = 0; y < columnLength; y++) {
                ComponentCard compCard = getShipComponent(x, y);
                if (compCard != null) {
                    if (y != 0 && getShipComponent(x, y - 1) == null && !compCard.getConnectors()[0].isExposed() && compCard.getConnectors()[0].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[0].setExposed(true);
                    }
                    if (y != 0 && getShipComponent(x, y - 1) != null && compCard.getConnectors()[0].isExposed() && compCard.getConnectors()[0].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[0].setExposed(false);
                    }
                    if (y == 0 && !compCard.getConnectors()[0].isExposed() && compCard.getConnectors()[0].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[0].setExposed(true);
                    }

                    if (x != 6 && getShipComponent(x + 1, y) == null && !compCard.getConnectors()[1].isExposed() && compCard.getConnectors()[1].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[1].setExposed(true);
                    }
                    if (x != 6 && getShipComponent(x + 1, y) != null && compCard.getConnectors()[1].isExposed() && compCard.getConnectors()[1].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[1].setExposed(false);
                    }
                    if (x == 6 && !compCard.getConnectors()[1].isExposed() && compCard.getConnectors()[1].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[1].setExposed(true);
                    }

                    if (y != 4 && getShipComponent(x, y + 1) == null && !compCard.getConnectors()[2].isExposed() && compCard.getConnectors()[2].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[2].setExposed(true);
                    }
                    if (y != 4 && getShipComponent(x, y + 1) != null && compCard.getConnectors()[2].isExposed() && compCard.getConnectors()[2].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[2].setExposed(false);
                    }
                    if (y == 4 && !compCard.getConnectors()[2].isExposed() && compCard.getConnectors()[2].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[2].setExposed(true);
                    }

                    if (x != 0 && getShipComponent(x - 1, y) == null && !compCard.getConnectors()[3].isExposed() && compCard.getConnectors()[3].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[3].setExposed(true);
                    }
                    if (x != 0 && getShipComponent(x - 1, y) != null && compCard.getConnectors()[3].isExposed() && compCard.getConnectors()[3].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[3].setExposed(false);
                    }
                    if (x == 0 && !compCard.getConnectors()[3].isExposed() && compCard.getConnectors()[3].getTubeNumber() > 0 && validPosition[x][y]) {
                        compCard.getConnectors()[3].setExposed(true);
                    }
                }
            }
        }
    }


    public int acceptCalculateCrew(OperationVisitor visitor) {
        return visitor.visitCalculateCrew(this);
    }

    public int acceptCalculateBattery(OperationVisitor visitor) {
        return visitor.visitCalculateBattery(this);
    }

    public void acceptRemoveCrew(OperationVisitor visitor, ArrayList<ArrayList<Integer>> removedCrewFrom) throws IOException {
        visitor.visitRemoveCrew(this, removedCrewFrom);
    }

    public int acceptCalculateGoods(OperationVisitor visitor) {
        return visitor.visitCalculateGoods(this);
    }

    public void acceptManageGoods(OperationVisitor visitor, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        visitor.visitManageGoods(this, goodsMap);
    }

    public int acceptCalculateExposedConnectors(OperationVisitor visitor) throws IOException {
        return visitor.visitCalculateExposedConnectors(this);
    }

    public void acceptRemoveBattery(OperationVisitor visitor, ArrayList<ArrayList<Integer>> batteriesList) {
        visitor.visitRemoveBattery(this, batteriesList);
    }

    public void acceptRemoveGoods(OperationVisitor visitor, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) throws IOException {
        visitor.visitRemoveGoods(this, goodsMap);
    }

    public String[][] getGrid() {
        String[][] grid = new String[getRowLength(this.level)][getColumnLength(this.level)];

        for (int x = 0; x < rowLength; x++) {
            for (int y = 0; y < columnLength; y++) {
                if (!validPosition[x][y]) {
                    grid[x][y] = "     "; // posizione non valida
                } else if (ship[x][y] == null) {
                    grid[x][y] = "[   ]"; // posizione valida ma vuota
                } else {
                    grid[x][y] = "[" + ship[x][y].getShortName() + "]"; // componente presente
                }
            }
        }
        return grid;
    }

}
