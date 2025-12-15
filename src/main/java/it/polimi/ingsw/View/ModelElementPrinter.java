package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.BatteryCompartment;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Hold;
import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.ClientGameController;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadCastMessageEndAdventureCardPhase;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessage;

import java.util.List;
import java.util.Map;

public class ModelElementPrinter {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";
    private static final String UNDERLINE = "\u001B[4m";
    // Color mapping for the four available colors
    private Map<Color, String> colorMap;

    private static void displayTrackAsLine(Flightboard flightboard, int trackLength) {
        StringBuilder line = new StringBuilder();
        line.append("↩ ");
        for (int position = 0; position < trackLength; position++) {
            line.append(getPositionDisplay(flightboard, position));
            line.append(" ");
        }
        line.append("↪");
        System.out.println(line);
    }


    private static String getPositionDisplay(Flightboard flightboard, int position) {
        int playerId = flightboard.getTrack().getValue(position);
        Player player = findPlayerById(flightboard, playerId);

        String positionStr = String.format("%2d", position);

        if (player != null) {
            String colorCode = getColorCode(player.getColor());
            String playerSymbol = player.getColor().getColor().substring(0, 1).toUpperCase();

            // Check if this player is the leader
            boolean isLeader = flightboard.getLeader() != null &&
                    flightboard.getLeader().getId() == player.getId();

            if (isLeader) {
                return "[" + colorCode + BOLD + UNDERLINE + positionStr + playerSymbol + RESET + "]";
            } else {
                return "[" + colorCode + BOLD + positionStr + playerSymbol + RESET + "]";
            }
        } else {
            return "[" + WHITE + positionStr + " " + RESET + "]";
        }
    }

    private static Player findPlayerById(Flightboard flightboard, int playerId) {
        if (playerId == 0) return null;

        for (Player player : flightboard.getPlayersInOrder()) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        for (Player player : flightboard.getStalledPlayers()) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        return null;
    }

    private static void displayPlayerOrder(Flightboard flightboard) {
        int rank = 1;
        for (Player player : flightboard.getPlayersInOrder()) {
            String colorCode = getColorCode(player.getColor());
            boolean isLeader = flightboard.getLeader() != null &&
                    flightboard.getLeader().getId() == player.getId();

            System.out.print(rank + ". " + colorCode + BOLD + player.getUsername() + RESET);
            System.out.print(" (" + player.getColor().getColor() + ")");
            System.out.print(" - Position: " + flightboard.getTrack().getIndex(player.getId()));
            System.out.print(" - Laps: " + player.getCompletedLaps());

            if (isLeader) {
                System.out.print(" " + YELLOW + BOLD + "[LEADER]" + RESET);
            }

            System.out.println();
            rank++;
        }
    }

    private static String getColorCode(Color color) {
        switch (color) {
            case RED:
                return RED;
            case GREEN:
                return GREEN;
            case BLUE:
                return BLUE;
            case YELLOW:
                return YELLOW;
            default:
                return WHITE;
        }
    }

    public void displayFlightboard(Flightboard flightboard) {
        int trackLength = flightboard.getTrack().getLength();
        Player leader = flightboard.getLeader();

        System.out.println("\n" + BOLD + "=== FLIGHT BOARD ===" + RESET);
        System.out.println("Track Length: " + trackLength + " positions");

        if (leader != null) {
            System.out.println("Leader: " + getColorCode(leader.getColor()) + BOLD +
                    leader.getUsername() + " (" + leader.getColor().getColor() + ")" + RESET);
        }

        System.out.println("\nStalled Players: " + flightboard.getStalledPlayers().size());
        for (Player stalledPlayer : flightboard.getStalledPlayers()) {
            System.out.println("  - " + getColorCode(stalledPlayer.getColor()) +
                    stalledPlayer.getUsername() + " (" + stalledPlayer.getColor().getColor() + ")" + RESET);
        }

        System.out.println("\n" + BOLD + "TRACK LAYOUT:" + RESET);
        displayTrackAsLine(flightboard, trackLength);

        System.out.println("\n" + BOLD + "PLAYER ORDER:" + RESET);
        displayPlayerOrder(flightboard);
    }

    private int getFacingDirectionIndex(ComponentCard card) {
        try {
            var method = card.getClass().getMethod("getFacingDirectionIndex");
            return (int) method.invoke(card);
        } catch (Exception e) {
            try {
                var method = card.getClass().getMethod("getShieldSides");
                return (int) method.invoke(card);
            } catch (Exception ex) {
                return -1;
            }
        }
    }

    public void printCardList(List<ComponentCard> cards) {
        StringBuilder[] cellLines = new StringBuilder[6];


        for (int i = 0; i < 6; i++) {
            cellLines[i] = new StringBuilder();
        }
        for (ComponentCard card : cards) {
            int j = cards.indexOf(card);

            String up = "         ";
            String down = "         ";
            String left = "   ";
            String right = "   ";
            String symbol = card.getShortName();
            Connector[] connectors = card.getConnectors();
            boolean hasUp = connectors.length > 0 && connectors[0].getTubeNumber() > 0;
            boolean hasDown = connectors.length > 2 && connectors[2].getTubeNumber() > 0;
            boolean hasLeft = connectors.length > 3 && connectors[3].getTubeNumber() > 0;
            boolean hasRight = connectors.length > 1 && connectors[1].getTubeNumber() > 0;
            int dir = getFacingDirectionIndex(card);
            if (hasUp) {
                if (connectors[0].getTubeNumber() == 1) {
                    up = ("    ↑    ");
                }
                if (connectors[0].getTubeNumber() == 2) {
                    up = ("   ↑ ↑   ");
                }
                if (connectors[0].getTubeNumber() == 3) {
                    up = ("   ↑↑↑   ");
                }
            } else if (dir == 0) {
                up = ("    ▲    ");
            }

            if (hasDown) {
                if (connectors[2].getTubeNumber() == 1) {
                    down = ("    ↓    ");
                }
                if (connectors[2].getTubeNumber() == 2) {
                    down = ("   ↓ ↓   ");
                }
                if (connectors[2].getTubeNumber() == 3) {
                    down = ("   ↓↓↓   ");
                }
            } else if (dir == 2) {
                down = ("    ▼    ");
            }

            if (hasLeft) {
                if (connectors[3].getTubeNumber() == 1) {
                    left = (" ← ");
                }
                if (connectors[3].getTubeNumber() == 2) {
                    left = ("← ←");
                }
                if (connectors[3].getTubeNumber() == 3) {
                    left = ("←←←");
                }
            } else if (dir == 3) {
                left = (" ◀ ");
            }

            if (hasRight) {
                if (connectors[1].getTubeNumber() == 1) {
                    right = (" → ");
                }
                if (connectors[1].getTubeNumber() == 2) {
                    right = ("→ →");
                }
                if (connectors[1].getTubeNumber() == 3) {
                    right = ("→→→");
                }
            } else if (dir == 1) {
                right = (" ▶ ");
            }
            String index = "           ";
            if (j < 10) {
                index = "    [" + j + "]    ";
            } else {
                index = "    [" + j + "]   ";
            }
            cellLines[0].append("┌---------┐");
            cellLines[1].append("|").append(up).append("|");
            cellLines[2].append("|").append(left).append(symbol).append(right).append("|");
            cellLines[3].append("|").append(down).append("|");
            cellLines[4].append("└---------┘");
            cellLines[5].append(index);


        }
        for (StringBuilder line : cellLines) {
            System.out.println(line.toString());

        }

        System.out.println();
    }

    public void printSingleCard(ComponentCard card) {
        StringBuilder[] cellLines = new StringBuilder[5];


        for (int i = 0; i < 5; i++) {
            cellLines[i] = new StringBuilder();
        }

        String up = "         ";
        String down = "         ";
        String left = "   ";
        String right = "   ";
        String symbol = card.getShortName();
        Connector[] connectors = card.getConnectors();
        boolean hasUp = connectors.length > 0 && connectors[0].getTubeNumber() > 0;
        boolean hasDown = connectors.length > 2 && connectors[2].getTubeNumber() > 0;
        boolean hasLeft = connectors.length > 3 && connectors[3].getTubeNumber() > 0;
        boolean hasRight = connectors.length > 1 && connectors[1].getTubeNumber() > 0;
        int dir = getFacingDirectionIndex(card);
        if (hasUp) {
            if (connectors[0].getTubeNumber() == 1) {
                up = ("    ↑    ");
            }
            if (connectors[0].getTubeNumber() == 2) {
                up = ("   ↑ ↑   ");
            }
            if (connectors[0].getTubeNumber() == 3) {
                up = ("   ↑↑↑   ");
            }
        } else if (dir == 0) {
            up = ("    ▲    ");
        }

        if (hasDown) {
            if (connectors[2].getTubeNumber() == 1) {
                down = ("    ↓    ");
            }
            if (connectors[2].getTubeNumber() == 2) {
                down = ("   ↓ ↓   ");
            }
            if (connectors[2].getTubeNumber() == 3) {
                down = ("   ↓↓↓   ");
            }
        } else if (dir == 2) {
            down = ("    ▼    ");
        }

        if (hasLeft) {
            if (connectors[3].getTubeNumber() == 1) {
                left = (" ← ");
            }
            if (connectors[3].getTubeNumber() == 2) {
                left = ("← ←");
            }
            if (connectors[3].getTubeNumber() == 3) {
                left = ("←←←");
            }
        } else if (dir == 3) {
            left = (" ◀ ");
        }

        if (hasRight) {
            if (connectors[1].getTubeNumber() == 1) {
                right = (" → ");
            }
            if (connectors[1].getTubeNumber() == 2) {
                right = ("→ →");
            }
            if (connectors[1].getTubeNumber() == 3) {
                right = ("→→→");
            }
        } else if (dir == 1) {
            right = (" ▶ ");
        }
        cellLines[0].append("┌---------┐");
        cellLines[1].append("|").append(up).append("|");
        cellLines[2].append("|").append(left).append(symbol).append(right).append("|");
        cellLines[3].append("|").append(down).append("|");
        cellLines[4].append("└---------┘");

        for (StringBuilder line : cellLines) {
            System.out.println(line.toString());

        }

        System.out.println();

    }

    public void printShipboard(Shipboard ship) {
        int cols = ship.getRowLength(ship.getLevel());
        int rows = ship.getColumnLength(ship.getLevel());
        ComponentCard[][] grid = ship.getShip();


        System.out.println("==SHIPBOARD==\n");


        for (int col = 0; col < cols; col++) {
            System.out.print("      " + (col + 4) + "     ");
        }
        System.out.println();
        for (int row = 0; row < rows; row++) {

            StringBuilder[] cellLines = new StringBuilder[5];
            for (int i = 0; i < 5; i++) {
                cellLines[i] = new StringBuilder();
            }
            for (int col = 0; col < cols; col++) {
                ComponentCard card = grid[row][col];
                String up = "         ";
                String down = "         ";
                String left = "   ";
                String right = "   ";
                String symbol = "   ";
                if (card != null) {
                    symbol = card.getShortName();
                    Connector[] connectors = card.getConnectors();
                    boolean hasUp = connectors.length > 0 && connectors[0].getTubeNumber() > 0;
                    boolean hasDown = connectors.length > 2 && connectors[2].getTubeNumber() > 0;
                    boolean hasLeft = connectors.length > 3 && connectors[3].getTubeNumber() > 0;
                    boolean hasRight = connectors.length > 1 && connectors[1].getTubeNumber() > 0;
                    int dir = getFacingDirectionIndex(card);

                    if (hasUp) {
                        if (connectors[0].getTubeNumber() == 1) {
                            up = ("    ↑    ");
                        }
                        if (connectors[0].getTubeNumber() == 2) {
                            up = ("   ↑ ↑   ");
                        }
                        if (connectors[0].getTubeNumber() == 3) {
                            up = ("   ↑↑↑   ");
                        }
                    } else if (dir == 0) {
                        up = ("    ▲    ");
                    }

                    if (hasDown) {
                        if (connectors[2].getTubeNumber() == 1) {
                            down = ("    ↓    ");
                        }
                        if (connectors[2].getTubeNumber() == 2) {
                            down = ("   ↓ ↓   ");
                        }
                        if (connectors[2].getTubeNumber() == 3) {
                            down = ("   ↓↓↓   ");
                        }
                    } else if (dir == 2) {
                        down = ("    ▼    ");
                    }

                    if (hasLeft) {
                        if (connectors[3].getTubeNumber() == 1) {
                            left = (" ← ");
                        }
                        if (connectors[3].getTubeNumber() == 2) {
                            left = ("← ←");
                        }
                        if (connectors[3].getTubeNumber() == 3) {
                            left = ("←←←");
                        }
                    } else if (dir == 3) {
                        left = (" ◀ ");
                    }

                    if (hasRight) {
                        if (connectors[1].getTubeNumber() == 1) {
                            right = (" → ");
                        }
                        if (connectors[1].getTubeNumber() == 2) {
                            right = ("→ →");
                        }
                        if (connectors[1].getTubeNumber() == 3) {
                            right = ("→→→");
                        }
                    } else if (dir == 1) {
                        right = (" ▶ ");
                    }

                }
                cellLines[0].append("┌---------┐ ");
                cellLines[1].append(" |").append(up).append("|");
                cellLines[2].append(" |").append(left).append(symbol).append(right).append("|");
                cellLines[3].append(" |").append(down).append("|");
                cellLines[4].append(" └---------┘");

            }
            System.out.print(row + 5);

            for (StringBuilder line : cellLines) {
                System.out.println(line.toString());

            }
        }
        for (Integer[] coords : ship.getCabinList()) {
            Cabin cabin = (Cabin) ship.getShipComponent(coords[0], coords[1]);
            System.out.println("Cabin: <" + (coords[0] + 5) + "," + (coords[1] + 4) + "> " + cabin.getOccupantNumber() + " " + cabin.getOccupantType());
        }
        for (Integer[] coords : ship.getBatteryList()) {
            BatteryCompartment bat = (BatteryCompartment) ship.getShipComponent(coords[0], coords[1]);
            System.out.println("Battery compartment: <" + (coords[0] + 5) + "," + (coords[1] + 4) + "> " + bat.getBatteriesAvailable() + " batteries with capacity: " + bat.getCapacity());
        }
        for (Integer[] coords : ship.getStorageList()) {
            Hold hold = (Hold) ship.getShipComponent(coords[0], coords[1]);
            System.out.println("Hold: <" + (coords[0] + 5) + "," + (coords[1] + 4) + "> " + hold.getCurrentCargo() + " goods with capacity: " + hold.getCapacity() + " ,special: <" + hold.isSpecial() + "> ");
            for (Goods good : hold.getLoad()) System.out.println(" " + good);
        }
    }

    public void printCard(AdventureCard card) {
        System.out.println("=== Carta Avventura ===");
        System.out.println(card.getEffectDescription());
        System.out.println("========================");
    }

    public void printDiceAndInfo(ClientGameController gameController) {
        System.out.println("=== Dice And Infos ===\n" +
                "Dice: " + gameController.getDiceAndInfo().get(2) + "\n" +
                "Dimension: " + gameController.getDiceAndInfo().getFirst() + "\n" +
                "Direction: " + gameController.getDiceAndInfo().get(1));

    }

    public void printFinalResults(BroadcastMessage msg) {

        System.out.println("\n\n🏆 WINNER: " + ((BroadCastMessageEndAdventureCardPhase) msg).getWinner() + " 🏆\n");

        System.out.println("===== FINAL RANK =====");

        List<String> rank = ((BroadCastMessageEndAdventureCardPhase) msg).getFinalRank();
        Map<String, Integer> totalCredits = ((BroadCastMessageEndAdventureCardPhase) msg).getTotalCredits();

        for (int i = 0; i < rank.size(); i++) {
            String username = rank.get(i);

            int total = totalCredits.getOrDefault(username, 0);
            int base = ((BroadCastMessageEndAdventureCardPhase) msg).getBaseCredits().getOrDefault(username, 0);
            int arrival = ((BroadCastMessageEndAdventureCardPhase) msg).getArrivalOrder().getOrDefault(username, 0);
            int goods = ((BroadCastMessageEndAdventureCardPhase) msg).getGoodsValue().getOrDefault(username, 0);
            int connectors = ((BroadCastMessageEndAdventureCardPhase) msg).getExposedConnectors().getOrDefault(username, 0);
            int losses = ((BroadCastMessageEndAdventureCardPhase) msg).getLoses().getOrDefault(username, 0);

            System.out.println((i + 1) + ". " + username + " - Total Points: " + total);
            System.out.println("   • Base credits: " + base);
            System.out.println("   • Credits For Arrival Order: " + arrival);
            System.out.println("   • Credits For Goods Value: " + goods);
            System.out.println("   • Credits For Less Exposed Connectors: " + connectors);
            System.out.println("   • Losses: -" + losses);
            System.out.println("--------------------------");
        }
    }


}
