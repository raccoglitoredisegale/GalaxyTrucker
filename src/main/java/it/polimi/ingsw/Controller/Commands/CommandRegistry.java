package it.polimi.ingsw.Controller.Commands;

import it.polimi.ingsw.Controller.Commands.AdventureCardCommands.*;
import it.polimi.ingsw.Controller.Commands.AssemblyPhaseCommands.*;
import it.polimi.ingsw.Controller.Commands.FinalPhaseCommands.Exit;
import it.polimi.ingsw.Controller.Commands.LobbyCommands.ChooseColor;
import it.polimi.ingsw.Controller.Commands.LobbyCommands.LeaveGame;
import it.polimi.ingsw.Controller.Commands.LobbyCommands.ShowPlayersInLobby;
import it.polimi.ingsw.Controller.Commands.LobbyCommands.StartGame;
import it.polimi.ingsw.Controller.Commands.PreLobbyCommands.CreateNewLobby;
import it.polimi.ingsw.Controller.Commands.PreLobbyCommands.JoinLobby;
import it.polimi.ingsw.Controller.Commands.PreLobbyCommands.ListLobbies;
import it.polimi.ingsw.Controller.Commands.UsernameCommands.CreateNewUser;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private static final Map<MessageType, ServerControllerCommand> commands = new HashMap<>();

    static {
        // PRE-LOBBY COMMANDS
        commands.put(MessageType.USERNAME, new CreateNewUser());
        commands.put(MessageType.SHOW_LOBBY, new ListLobbies());
        commands.put(MessageType.CREATE_NEW_LOBBY, new CreateNewLobby());
        commands.put(MessageType.JOIN_LOBBY, new JoinLobby());

        // LOBBY COMMANDS
        commands.put(MessageType.CHOOSE_COLOR, new ChooseColor());
        commands.put(MessageType.START_GAME, new StartGame());
        commands.put(MessageType.LEAVE_GAME, new LeaveGame());
        commands.put(MessageType.SHOW_PLAYERS_IN_LOBBY, new ShowPlayersInLobby());

        // ASSEMBLY PHASE COMMANDS
        commands.put(MessageType.COMPONENT_CARD_DRAWN, new ComponentCardDrawn());
        commands.put(MessageType.COMPONENT_CARD_PLACED, new ComponentCardPlaced());
        commands.put(MessageType.ROTATE_HOURGLASS, new RotateHourglass());
        commands.put(MessageType.FINISHED_ASSEMBLY_PHASE, new FinishedAssemblyPhase());
        commands.put(MessageType.DISCARD_COMPONENT_CARD_DRAWN, new ComponentCardDiscarded());
        commands.put(MessageType.GET_COMPONENT_CARD_REVEALED_DECK, new GetComponentCardRevealedDeck()); // expose the entire deck of revealed card
        commands.put(MessageType.SHOW_OTHER_PLAYER_SHIPBOARD, new ShowOtherPlayerShipboard());
        commands.put(MessageType.SEE_TIME_REMAINING, new SeeTimeRemainingHourglass());
        commands.put(MessageType.TAKE_COMPONENT_CARD_FROM_REVEALED_DECK, new TakeCardFromRevealedDeck());

        // ADVENTURE CARD PHASE COMMANDS
        commands.put(MessageType.DRAW_ADVENTURE_CARD, new DrawAdventureCard());
        commands.put(MessageType.SKIP_ADVENTURE_CARD, new SkipAdventureCard());
        commands.put(MessageType.STALL_PLAYER, new StallPlayer());
        commands.put(MessageType.THROW_DICE_METEOR, new DiceMeteor());
        commands.put(MessageType.OCCUPY_PlANET, new OccupyPlanet());
        commands.put(MessageType.OCCUPY_STATION, new OccupyStation());
        commands.put(MessageType.APPLY_EPIDEMIC, new ApplyEpidemic());
        commands.put(MessageType.MANAGE_METEOR, new ManageMeteor());
        commands.put(MessageType.APPLY_STARDUST, new ApplyStardust());
        commands.put(MessageType.APPLY_OPEN_SPACE, new ApplyOpenSpace());
        commands.put(MessageType.OCCUPY_SHIP, new OccupyShip());
        commands.put(MessageType.SLAVERS_WON, new SlaversWon());
        commands.put(MessageType.SLAVERS_LOST, new SlaversLost());
        commands.put(MessageType.APPLY_SLAVERS, new ApplySlavers());
        commands.put(MessageType.SMUGGLERS_WON, new SmugglersWon());
        commands.put(MessageType.SMUGGLERS_LOST, new SmugglersLost());
        commands.put(MessageType.APPLY_SMUGGLERS, new ApplySmugglers());
        commands.put(MessageType.PIRATES_WON, new PiratesWon());
        commands.put(MessageType.WARZONE_CONTROL, new WarzoneControl());
        commands.put(MessageType.WARZONE_CREW_PENALTY, new WarzoneCrewPenalty());
        commands.put(MessageType.WARZONE_GOODS_PENALTY, new WarzoneGoodsPenalty());
        commands.put(MessageType.THROW_DICE_BLAST, new DiceBlast());
        commands.put(MessageType.APPLY_PIRATES, new ApplyPirates());
        commands.put(MessageType.MANAGE_BLAST, new ManageBlast());
        commands.put(MessageType.SHOW_FLIGHT_BOARD, new ShowFlightBoard());
        commands.put(MessageType.ASK_SHIP, new UpdateShipboard());
        commands.put(MessageType.EXIT, new Exit());

    }

    public static Map<MessageType, ServerControllerCommand> getCommands() {
        return commands;
    }

    public ServerControllerCommand get(MessageType key) {
        return commands.get(key);
    }
}
