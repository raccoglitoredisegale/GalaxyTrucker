package it.polimi.ingsw.Model.ShipBoard;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.*;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.ClientGameController;
import it.polimi.ingsw.View.ModelElementPrinter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ShipboardTest {
    Shipboard shipboard = new Shipboard(2);
    ArrayList<ComponentCard> components;

    @Test
    public void validateComponentTest() throws Exception {

        ClientGameController clientGameController = new ClientGameController();
        ModelElementPrinter printer = new ModelElementPrinter();

        shipboard.addComponent(clientGameController.getStartingCabin(Color.BLUE), 2, 3);

        shipboard.addComponent(clientGameController.getComponentCard("11616"), 2, 4);
        shipboard.addComponent(clientGameController.getComponentCard("11823"), 2, 2);

        shipboard.addComponent(clientGameController.getComponentCard("11316"), 3, 4);
        shipboard.addComponent(clientGameController.getComponentCard("11227"), 3, 3);
        shipboard.addComponent(clientGameController.getComponentCard("11515"), 3, 2);
        ComponentCard curr = clientGameController.getComponentCard("11536");
        curr.rotation();
        shipboard.addComponent(curr, 3, 5);
        shipboard.addComponent(clientGameController.getComponentCard("11432"), 4, 4);
        shipboard.addComponent(clientGameController.getComponentCard("11545"), 4, 2);
        shipboard.addComponent(clientGameController.getComponentCard("11415"), 4, 1);

        printer.printShipboard(shipboard);
        System.out.println("\n\n\n");
        ArrayList<String> componentsRemoved = new ArrayList<>();
        validateComponentAfterAssemblyPhase(componentsRemoved);

        printer.printShipboard(shipboard);
        for (String component : componentsRemoved) {
            System.out.print(component + ", ");
        }
    }

    private void validateComponentAfterAssemblyPhase(ArrayList<String> componentsRemoved) {
        for (int row = 0; row < shipboard.getShip().length; row++) {
            for (int col = 0; col < shipboard.getShip()[row].length; col++) {
                if (shipboard.getShip()[row][col] != null) {
                    if (!shipboard.getShip()[row][col].isValid()) {
                        componentsRemoved.add(shipboard.getShip()[row][col].getId());
                        shipboard.removeComponent(row, col);
                    }
                    boolean[][] visited = new boolean[shipboard.getShip().length][shipboard.getShip()[0].length];
                    if (shipboard.getShip()[row][col] != null && !findPath(row, col, visited)) {
                        componentsRemoved.add(shipboard.getShip()[row][col].getId());
                        shipboard.removeComponent(row, col);
                        validateComponentAfterAssemblyPhase(componentsRemoved);
                        return;
                    }
                }
            }
        }
    }

    private boolean findPath(int row, int col, boolean[][] visited) {
        // Base case: reached the center
        if (row == 2 && col == 3) {
            return true;
        }

        // Base case: out of bounds or already visited
        if (row < 0 || row >= shipboard.getShip().length ||
                col < 0 || col >= shipboard.getShip()[0].length ||
                visited[row][col] || shipboard.getShip()[row][col] == null) {
            return false;
        }

        // Mark as visited
        visited[row][col] = true;

        ComponentCard currComponent = shipboard.getShip()[row][col];

        // Check DOWN (row+1)
        if (row < shipboard.getShip().length - 1 &&
                shipboard.getShip()[row+1][col] != null &&
                checkNeighbourConnection(2, row, col)) { // Note: [2] for opposite direction
            if (findPath(row+1, col, visited)) {
                return true;
            }
        }

        // Check RIGHT (col+1)
        if (col < shipboard.getShip()[0].length - 1 &&
                shipboard.getShip()[row][col+1] != null &&
                checkNeighbourConnection(1, row, col)) { // Note: [3] for opposite direction
            if (findPath(row, col+1, visited)) {
                return true;
            }
        }

        // Check UP (row-1)
        if (row > 0 &&
                shipboard.getShip()[row-1][col] != null &&
                checkNeighbourConnection(0, row, col)) {
            if (findPath(row-1, col, visited)) {
                return true;
            }
        }

        // Check LEFT (col-1)
        if (col > 0 &&
                shipboard.getShip()[row][col-1] != null &&
                checkNeighbourConnection(3, row, col)) {
            if (findPath(row, col-1, visited)) {
                return true;
            }
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
                if (shipboard.getShip()[row-1][col].getConnectors()[2].getTubeNumber() == 3 && shipboard.getShip()[row][col].getConnectors()[0].getTubeNumber() > 0
                    || shipboard.getShip()[row-1][col].getConnectors()[2].getTubeNumber() > 0 && shipboard.getShip()[row][col].getConnectors()[0].getTubeNumber() == 3) {
                    return true;
                } else if (shipboard.getShip()[row-1][col].getConnectors()[2].getTubeNumber() == 2 && shipboard.getShip()[row][col].getConnectors()[0].getTubeNumber() == 2) {
                    return true;
                } else if (shipboard.getShip()[row-1][col].getConnectors()[2].getTubeNumber() == 1 && shipboard.getShip()[row][col].getConnectors()[0].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 1:
                // CHECK RIGHT
                // [ROW][COL] --> [ROW][COL+1]
                if (shipboard.getShip()[row][col+1].getConnectors()[3].getTubeNumber() == 3 && shipboard.getShip()[row][col].getConnectors()[1].getTubeNumber() > 0
                        || shipboard.getShip()[row][col+1].getConnectors()[3].getTubeNumber() > 0 && shipboard.getShip()[row][col].getConnectors()[1].getTubeNumber() == 3) {
                    return true;
                } else if (shipboard.getShip()[row][col+1].getConnectors()[3].getTubeNumber() == 2 && shipboard.getShip()[row][col].getConnectors()[1].getTubeNumber() == 2) {
                    return true;
                } else if (shipboard.getShip()[row][col+1].getConnectors()[3].getTubeNumber() == 1 && shipboard.getShip()[row][col].getConnectors()[1].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 2:
                // CHECK DOWN
                // [ROW][COL]
                //      |
                //      |
                // [ROW+1][COL]
                if (shipboard.getShip()[row+1][col].getConnectors()[3].getTubeNumber() == 3 && shipboard.getShip()[row][col].getConnectors()[2].getTubeNumber() > 0
                        || shipboard.getShip()[row+1][col].getConnectors()[3].getTubeNumber() > 0 && shipboard.getShip()[row][col].getConnectors()[2].getTubeNumber() == 3) {
                    return true;
                } else if (shipboard.getShip()[row+1][col].getConnectors()[3].getTubeNumber() == 2 && shipboard.getShip()[row][col].getConnectors()[2].getTubeNumber() == 2) {
                    return true;
                } else if (shipboard.getShip()[row+1][col].getConnectors()[3].getTubeNumber() == 1 && shipboard.getShip()[row][col].getConnectors()[2].getTubeNumber() == 1) {
                    return true;
                }
                break;
            case 3:
                // CHECK RIGHT
                // [ROW][COL-1] <-- [ROW][COL]
                if (shipboard.getShip()[row][col-1].getConnectors()[1].getTubeNumber() == 3 && shipboard.getShip()[row][col].getConnectors()[3].getTubeNumber() > 0
                        || shipboard.getShip()[row][col-1].getConnectors()[1].getTubeNumber() > 0 && shipboard.getShip()[row][col].getConnectors()[3].getTubeNumber() == 3) {
                    return true;
                } else if (shipboard.getShip()[row][col-1].getConnectors()[1].getTubeNumber() == 2 && shipboard.getShip()[row][col].getConnectors()[3].getTubeNumber() == 2) {
                    return true;
                } else if (shipboard.getShip()[row][col-1].getConnectors()[1].getTubeNumber() == 1 && shipboard.getShip()[row][col].getConnectors()[3].getTubeNumber() == 1) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Test
    public void placeComponentTest() throws Exception {
        ClientGameController clientGameController = new ClientGameController();
        ModelElementPrinter printer = new ModelElementPrinter();
        Shipboard shipboard = new Shipboard(2);
        int row = 8; // parts[1]
        int col = 7; // parts[2]

        shipboard.addComponent(
                clientGameController.getComponentCard("11616"), row - 5, col - 4);
        printer.printShipboard(shipboard);
    }
}
