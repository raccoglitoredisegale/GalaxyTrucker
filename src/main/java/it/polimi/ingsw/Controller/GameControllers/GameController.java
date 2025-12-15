package it.polimi.ingsw.Controller.GameControllers;

import it.polimi.ingsw.Controller.Exceptions.HourglassException;
import it.polimi.ingsw.Controller.Exceptions.InvalidUsername;
import it.polimi.ingsw.Controller.Exceptions.JoinGameException;
import it.polimi.ingsw.Model.AdventureCard.DeckManager;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.ComponentCard.ComponentCardDeck;
import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Model.Game.Hourglass;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadCastMessageEndAdventureCardPhase;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.BroadcastMessageAdventureCardStall;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.*;

public class GameController {
    private final int maxPlayers;
    private final String lobbyName;
    private final List<Player> players;
    private final ArrayList<String> playerWhoFinishedAssemblyPhase;
    private final ArrayList<Player> affectedPlayers;
    private final ArrayList<ClientHandler> handlers;
    private final Map<String, Integer> playerPositions;
    private Player lobbyCreator;
    private ArrayList<Player> inGamePlayers;
    private ArrayList<Player> playerActionOrder;
    private Player currentPlayer;
    private Flightboard flightboard;
    private AdventureCard currentAdventureCard;
    private Hourglass hourglass;
    private final ArrayList<Player> gameStalledPlayers;

    private ComponentCardDeck componentCardDeck;
    private DeckManager deckManager;

    public GameController(int maxPlayers, String lobbyName, Player lobbyCreator) {
        this.maxPlayers = maxPlayers;
        this.lobbyName = lobbyName;
        this.lobbyCreator = lobbyCreator;
        this.players = new ArrayList<>();
        this.inGamePlayers = new ArrayList<>();
        this.affectedPlayers = new ArrayList<>();
        this.playerPositions = new HashMap<>();
        this.playerWhoFinishedAssemblyPhase = new ArrayList<>();
        this.inGamePlayers = new ArrayList<>();
        this.playerActionOrder = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.gameStalledPlayers = new ArrayList<>();
    }


    public Flightboard getFlightboard() {
        return this.flightboard;
    }

    public void setFlightboard(Flightboard flightboard) {
        this.flightboard = flightboard;
    }

    public String getLobbyCreator() {
        return lobbyCreator.getUsername();
    }

    public void setLobbyCreator(Player newLobbyCreator) {
        this.lobbyCreator = newLobbyCreator;
    }

    public synchronized boolean canJoin() {
        return players.size() < maxPlayers;
    }

    public synchronized boolean canStartGame(ClientHandler clientHandler) throws JoinGameException {
        if (players.size() < 2) {
            throw (new JoinGameException("Not enough players in lobby"));
        } else if (!clientHandler.getPlayer().equals(lobbyCreator)) {
            throw (new JoinGameException("You're not the lobby creator"));
        }
        for (Player player : players) {
            if (player.getColor() == null) {
                throw (new JoinGameException("Not all players have choose the color"));
            }
        }

        this.componentCardDeck = new ComponentCardDeck();
        this.deckManager = new DeckManager();
        this.hourglass = new Hourglass();
        this.flightboard = new Flightboard(2);

        return true;
    }

    public synchronized void addPlayer(Player player) {
        if (!canJoin()) {
            throw new IllegalStateException("Cannot join in current state or max reached");
        }
        players.add(player);
        inGamePlayers.add(player);
    }

    public synchronized void removePlayer(Player player) {
        System.out.println("Player " + player.getUsername() + " removed from game " + player.getGameID());
        players.remove(player);
        try {
            inGamePlayers.remove(player);
            playerActionOrder.remove(player);
            playerWhoFinishedAssemblyPhase.remove(player.getUsername());
            affectedPlayers.remove(player);
        } catch (Exception e) {

        }
    }


    public void swapPlayerPositions(String playerA, String playerB) {
        if (!playerPositions.containsKey(playerA) || !playerPositions.containsKey(playerB)) {
            throw new IllegalArgumentException("Players must be in game to be swapped");
        }
        int posA = playerPositions.get(playerA);
        int posB = playerPositions.get(playerB);
        playerPositions.put(playerA, posB);
    }

    public void addHandler(ClientHandler clientHandler) {
        handlers.add(clientHandler);
    }

    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }

    public synchronized String getPlayerListToString() {
        StringBuilder list = new StringBuilder();
        String color;
        String RESET = "\u001B[0m";
        String WHITE = "\u001B[37m";
        String BOLD = "\u001B[1m";
        for (Player player : players) {
            if (player.getColor() == null) {
                color = WHITE;
            } else {
                color = getTextColor(player.getColor());
            }
            if (player.getUsername().equals(lobbyCreator.getUsername())) {
                list.append(color).append(BOLD).append(player.getUsername()).append(RESET).append(" (creator)").append("\n\t");
            } else {
                list.append(color).append(BOLD).append(player.getUsername()).append(RESET).append("\n\t");
            }
        }
        return list.toString();
    }

    public String getTextColor(Color color) {
        return switch (color) {
            case BLUE -> "\u001B[34m";
            case GREEN -> "\u001B[32m";
            case YELLOW -> "\u001B[33m";
            case RED -> "\u001B[31m";
        };
    }

    public synchronized String getCurrentPlayer() {
        if (currentPlayer == null) {
            return "0000";
        }
        return currentPlayer.getUsername();
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized void sendBroadCastMessage(Message message, ArrayList<Player> excluded) {
        SystemController sc = SystemController.getInstance();
        for (ClientHandler handler : handlers) {
            if (!excluded.contains(handler.getPlayer())) {
                sc.sendBroadCastMessage(handler, message);
            }
        }
    }

    public synchronized int getLobbySize() {
        return players.size();
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public boolean isColorAlreadyTaken(Color color) {
        for (Player player : players) {
            if (player.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    public boolean canDrawAdventureCard(Player player) {
        return Objects.equals(this.flightboard.getLeader().getUsername(), player.getUsername());
    }

    public boolean canThrowDice(Player player) {
        return Objects.equals(this.flightboard.getLeader().getUsername(), player.getUsername()) && (getAffectedPlayers().isEmpty() || getAffectedPlayers().size() == inGamePlayers.size());
    }

    public ArrayList<Player> getPlayerActionOrder() {
        return playerActionOrder;
    }

    public void setPlayerActionOrder(AdventureCard card) {
        if (card.getHasActionOrder()) {
            playerActionOrder = card.calculatePlayerActionOrder(this.flightboard.getPlayersInOrder());
            setCurrentPlayer();
        }
    }

    public synchronized ArrayList<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public synchronized void removeInGamePlayer(Player player) {
        inGamePlayers.remove(player);
    }


    public void setCurrentPlayer() {
        currentPlayer = playerActionOrder.getFirst();
        playerActionOrder.remove(currentPlayer);
    }

    public Shipboard getShipboardByUsername(String username) throws InvalidUsername {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player.getShip();
            }
        }
        throw new InvalidUsername("Player " + username + " not found");
    }

    public void completeCurrentAdventureCard() {
        currentAdventureCard = null;
    }

    public AdventureCard getCurrentAdventureCard() {
        return currentAdventureCard;
    }

    public void setCurrentAdventureCard(AdventureCard card) {
        this.currentAdventureCard = card;
    }

    public ComponentCardDeck getComponentCardDeck() {
        return this.componentCardDeck;
    }

    public DeckManager getDeckManager() {
        return this.deckManager;
    }

    /**
     * @param playerUsername telling me who's the player that call the function
     *                       <p>
     *                       check if timesTurned is different from three I need it to cover the case where the hourglass
     *                       is turned for the last time. In fact, when the last hourglass is finished,
     *                       the assembly phase will be finished and a broadcast message will be sent
     */
    public boolean turnTheHourglass(String playerUsername) throws HourglassException {
        synchronized (hourglass) {
            if (!this.hourglass.isFinished()) {
                throw new HourglassException("The hourglass is not finished yet!");
            }
            synchronized (playerWhoFinishedAssemblyPhase) {
                if (this.hourglass.getTimesTurned() == 1 && !playerWhoFinishedAssemblyPhase.contains(playerUsername)) {
                    throw new HourglassException("You must terminated your assembly phase to turn " +
                            "the hourglass for the last time!");
                }
            }
            if (this.hourglass.getTimesTurned() >= 2) {
                throw new HourglassException("The maximum number of times you can turn the hourglass is two!");
            }
            this.hourglass.incrementTimesTurned();
            return this.hourglass.getTimesTurned() != 2;
        }
    }

    public long getRemainingTime() {
        long elapsed = System.currentTimeMillis() - this.hourglass.getStartTime();
        return Math.max(0, 90000 - elapsed);
    }

    public void addToFlightBoard() {
        synchronized (playerWhoFinishedAssemblyPhase) {
            for (Player player : players) {
                if (!playerWhoFinishedAssemblyPhase.contains(player.getUsername())) {
                    playerWhoFinishedAssemblyPhase.add(player.getUsername());
                    flightboard.insertPlayer(player, 2);
                }
            }
        }
    }

    public void addToPlayerWhoFinishedAssemblyPhase(String playerUsername) {
        synchronized (playerWhoFinishedAssemblyPhase) {
            playerWhoFinishedAssemblyPhase.add(playerUsername);
        }
    }

    public synchronized ArrayList<Player> getAffectedPlayers() {
        return affectedPlayers;
    }

    public synchronized void setAffectedPlayers(Player player) {
        affectedPlayers.add(player);
    }

    public synchronized void clearAffectedPlayers() {
        affectedPlayers.clear();
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getUsername().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public void checkGameEnded() throws Exception {
        Map<String, Integer> baseCredits = new HashMap<>();
        Map<String, Integer> arrivalOrder = new HashMap<>();
        Map<String, Integer> goodsValue = new HashMap<>();
        Map<String, Integer> exposedConnectors = new HashMap<>();
        Map<String, Integer> loses = new HashMap<>();
        OperationVisitor applyOperation = new ApplyOperation();
        String winner;
        Map<String, Integer> totalCredits = new HashMap<>();
        int reason = -1;

        if (deckManager.getMainDeck().isEmpty() || gameStalledPlayers.size() == players.size()) {


            for (Player player : players) {
                baseCredits.put(player.getUsername(), player.getCredits());
            }
            /// /////////
            int value = 4;
            for (int i = 0; i < inGamePlayers.size() && value > 0; i++) {
                arrivalOrder.put(inGamePlayers.get(i).getUsername(), value);
                value--;
            }
            for (Player p : flightboard.getStalledPlayers()) {
                arrivalOrder.put(p.getUsername(), 0);
            }
            /// //////////
            for (Player p : players) {
                int points = p.getShip().acceptCalculateGoods(applyOperation);
                if (gameStalledPlayers.contains(p.getUsername())) {
                    points /= 2;
                }
                goodsValue.put(p.getUsername(), points);
            }
            /// //////////
            Map<String, Integer> tempExposed = new HashMap<>();
            int minExposed = Integer.MAX_VALUE;
            for (Player p : players) {
                int exposed = p.getShip().acceptCalculateExposedConnectors(applyOperation);
                tempExposed.put(p.getUsername(), exposed);
                if (exposed < minExposed) {
                    minExposed = exposed;
                }
            }
            for (Player p : players) {
                if (tempExposed.get(p.getUsername()) == minExposed) {
                    exposedConnectors.put(p.getUsername(), 2);
                } else {
                    exposedConnectors.put(p.getUsername(), 0);
                }
            }
            /// //////////
            for (Player p : players) {
                loses.put(p.getUsername(), p.getShip().getDiscardedComponentCards().size());
            }
            /// //////////
            for (Player p : players) {
                p.gainCredits(arrivalOrder.get(p.getUsername()) + goodsValue.get(p.getUsername()) + exposedConnectors.get(p.getUsername()) - loses.get(p.getUsername()));
            }
            List<Player> sortedRank = new ArrayList<>(players);
            sortedRank.sort((p1, p2) -> Integer.compare(p2.getCredits(), p1.getCredits()));
            ArrayList<String> finalRank = new ArrayList<>();
            for (Player p : sortedRank) {
                finalRank.add(p.getUsername());
            }
            for (Player p : sortedRank) {
                totalCredits.put(p.getUsername(), p.getCredits());
            }
            winner = finalRank.get(0);
            if (deckManager.getMainDeck().isEmpty()) reason = 0;
            if (gameStalledPlayers.size() == players.size()) reason = 1;
            sendBroadCastMessage(new BroadCastMessageEndAdventureCardPhase(MessageType.BROADCAST, MessageType.END_ADVENTURE_CARD_PHASE, winner, finalRank, arrivalOrder, goodsValue, exposedConnectors, loses, totalCredits, baseCredits, reason),
                    new ArrayList<Player>());
        }
    }

    public void addStalledPlayer(Player p) {
        if (!gameStalledPlayers.contains(p)) gameStalledPlayers.add(p);
        if (!flightboard.getStalledPlayers().contains(p)) {
            flightboard.stallPlayer(p);
        }
        inGamePlayers.remove(p);
    }

    public void clearStalledPlayers() {
        gameStalledPlayers.clear();
    }

    public ArrayList<Player> getStalledPlayers() {
        return gameStalledPlayers;
    }

    public void checkStalledPlayers() {
        OperationVisitor applyOperation = new ApplyOperation();
        ArrayList<String> stalledPlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getShip().acceptCalculateCrew(applyOperation) < 1) {
                if (!gameStalledPlayers.contains(player)) addStalledPlayer(player);
            }
        }
        for (Player p : flightboard.getStalledPlayers()) {
            if (!gameStalledPlayers.contains(p)) {
                addStalledPlayer(p);
            }
        }
        for (Player p : gameStalledPlayers) {
            stalledPlayers.add(p.getUsername());
        }
        BroadcastMessageAdventureCardStall message = new BroadcastMessageAdventureCardStall(MessageType.BROADCAST, MessageType.ADVENTURE_CARD_STALLED, stalledPlayers);

        sendBroadCastMessage(message, new ArrayList<>());
    }

}
