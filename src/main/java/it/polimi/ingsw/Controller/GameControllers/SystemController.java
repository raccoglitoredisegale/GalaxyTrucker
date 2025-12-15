package it.polimi.ingsw.Controller.GameControllers;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Server.ClientHandler;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SystemController {
    public static SystemController instance;
    private final ConcurrentHashMap<String, GameController> lobbyRegistry;
    private final ArrayList<String> playersUsernames;

    private SystemController() {
        this.lobbyRegistry = new ConcurrentHashMap<>();
        this.playersUsernames = new ArrayList<>();
    }

    public static synchronized SystemController getInstance() {
        if (instance == null) {
            instance = new SystemController();
        }
        return instance;
    }

    public void addUsername(String username) {
        this.playersUsernames.add(username);
    }

    public boolean checkUsername(String username) {
        return this.playersUsernames.contains(username);
    }

    public void createGame(int maxPlayers, ClientHandler handler, String lobbyName) {
        String gameID = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
//		String gameID = "gameid";
        GameController controller = GameControllerFactory.create(maxPlayers, lobbyName, handler.getPlayer());
        lobbyRegistry.put(gameID, controller);
        handler.getPlayer().setGameID(gameID);
        controller.addPlayer(handler.getPlayer());
        controller.addHandler(handler);
    }

    public synchronized void joinGame(String gameID, ClientHandler handler) {
        handler.getPlayer().setGameID(gameID);
        GameController controller = lobbyRegistry.get(gameID);
        if (controller == null) {
            throw new IllegalArgumentException("Invalid game");
        }
        if (!controller.canJoin()) {
            throw new IllegalArgumentException("Can't join game");
        }
        controller.addPlayer(handler.getPlayer());
        controller.addHandler(handler);
    }

    public synchronized void gameLeaved(ClientHandler handler) {
        String gameID = handler.getPlayer().getGameID();
        GameController controller = lobbyRegistry.get(gameID);
        controller.removePlayer(handler.getPlayer());
        handler.getPlayer().setColor(null);
        controller.getHandlers().remove(handler);
        if (controller.getLobbySize() == 0) {
            lobbyRegistry.remove(gameID);
        } else if (handler.getPlayer().getUsername().equals(controller.getLobbyCreator())) {
            controller.setLobbyCreator(controller.getPlayers().getFirst());
        }
    }

    public void sendBroadCastMessage(ClientHandler handler, Message message) {
        handler.sendToClient(message);
    }

    public String lobbiesToString() {
        synchronized (lobbyRegistry) {
            if (lobbyRegistry.isEmpty()) {
                return "No lobbies have been created";
            }
            String lobbyName;
            String lobbyPlayers;
            StringBuilder listOfLobbies = new StringBuilder();
            for (String gameID : lobbyRegistry.keySet()) {
                lobbyName = lobbyRegistry.get(gameID).getLobbyName();
                lobbyPlayers = lobbyRegistry.get(gameID).getPlayerListToString();
                listOfLobbies.append("Lobby ").append(gameID).append(": <name> ").append(lobbyName)
                        .append("\n\t").append(lobbyPlayers).append("\n");
            }
            return listOfLobbies.toString();
        }
    }

    public synchronized GameController getGameController(String gameID) {
        return this.lobbyRegistry.get(gameID);
    }

    public synchronized void removeGameController(String lobbyId) throws Exception {
        if (lobbyRegistry.containsKey(lobbyId)) {
            this.lobbyRegistry.remove(lobbyId);
        }
    }
}
