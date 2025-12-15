package it.polimi.ingsw.Controller.GameControllers;

import it.polimi.ingsw.Model.Player.Player;

/**
 * is needed to be able to support multiple game modes such as tutorial and
 * manage them as different game controllers variants
 */
public class GameControllerFactory {

    public static GameController create(int maxPlayers, String lobbyName, Player player) {
        return new GameController(maxPlayers, lobbyName, player);
    }

}
