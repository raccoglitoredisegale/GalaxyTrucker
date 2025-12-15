package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands.*;
import it.polimi.ingsw.Network.Client.ClientCommands.AssemblyPhaseCommands.*;
import it.polimi.ingsw.Network.Client.ClientCommands.FinalPhase.HandleExit;
import it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands.HandleChooseColor;
import it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands.HandleLeaveLobby;
import it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands.HandleShowPlayersInLobby;
import it.polimi.ingsw.Network.Client.ClientCommands.LobbyCommands.HandleStartCommand;
import it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands.HandleCreateNewLobby;
import it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands.HandleJoinLobby;
import it.polimi.ingsw.Network.Client.ClientCommands.PreLobbyCommands.HandleShowLobbies;

import java.util.HashMap;
import java.util.Map;

public class ClientCommandRegistry {
    private static final Map<String, ClientCommand> commandsPreLobbuPhase = new HashMap<>();
    private static final Map<String, ClientCommand> commandsLobbuPhase = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAssemblyPhase = new HashMap<>();
    private static final Map<String, ClientCommand> commandsWeldPhase = new HashMap<>();
    private static final Map<String, ClientCommand> commandsPreFlightPhase = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardDraw = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardWait = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardPlanets = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardAbandonedStation = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardAbandonedShip = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardMeteorSwarm = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardSlavers = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardSmugglers = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardPirates = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardPiratesBlast = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardEpidemic = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardStardust = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardOpenSpace = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardWarzone = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardWarzoneCrew = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardWarzoneGoods = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardWarzoneBlast = new HashMap<>();
    private static final Map<String, ClientCommand> commandsAdventureCardStall = new HashMap<>();
    private static final Map<String, ClientCommand> commandsFinalPhase = new HashMap<>();


    static {
        commandsPreLobbuPhase.put("create", new HandleCreateNewLobby());
        commandsPreLobbuPhase.put("lobbies", new HandleShowLobbies());
        commandsPreLobbuPhase.put("join", new HandleJoinLobby());

        commandsLobbuPhase.put("color", new HandleChooseColor());
        commandsLobbuPhase.put("start", new HandleStartCommand());
        commandsLobbuPhase.put("opponents", new HandleShowPlayersInLobby());
        commandsLobbuPhase.put("leave", new HandleLeaveLobby());
        commandsLobbuPhase.put("shipboard", new HandleShowShipboard());

        commandsAssemblyPhase.put("draw", new HandleDrawCardCommand());
        commandsAssemblyPhase.put("opponents", new HandleShowPlayersInLobby());
        commandsAssemblyPhase.put("shipboard", new HandleShowShipboard());
        commandsAssemblyPhase.put("discard", new HandleDiscardComponentCard());
        commandsAssemblyPhase.put("revealed", new HandleRevealedComponentCardDeck());
        commandsAssemblyPhase.put("show", new HandleShowPlayerShipboard());
        commandsAssemblyPhase.put("remove", new HandleRemoveComponentCardFromSaved());
        commandsAssemblyPhase.put("saved", new HandleShowSavedComponentCards());
        commandsAssemblyPhase.put("hourglass", new HandleHourglass());
        commandsAssemblyPhase.put("take", new HandleTakeComponentCardFromRevealedDeck());
        commandsAssemblyPhase.put("rotate", new HandleRotateHourglass());
        commandsAssemblyPhase.put("finished", new HandleFinished());

        commandsWeldPhase.put("place", new HandlePlaceComponentCard());
        commandsWeldPhase.put("discard", new HandleDiscardComponentCard());
        commandsWeldPhase.put("saved", new HandleShowSavedComponentCards());
        commandsWeldPhase.put("opponents", new HandleShowPlayersInLobby());
        commandsWeldPhase.put("show", new HandleShowPlayerShipboard());
        commandsWeldPhase.put("shipboard", new HandleShowShipboard());

        commandsPreFlightPhase.put("hourglass", new HandleHourglass());
        commandsPreFlightPhase.put("rotate", new HandleRotateHourglass());
        commandsPreFlightPhase.put("shipboard", new HandleShowShipboard());
        commandsPreFlightPhase.put("show", new HandleShowPlayerShipboard());
        commandsPreFlightPhase.put("flightboard", new HandleFlightBoard());

        commandsAdventureCardDraw.put("stall", new HandleStall());
        commandsAdventureCardDraw.put("draw", new HandleDrawAdventureCard());
        commandsAdventureCardDraw.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardDraw.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardDraw.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardDraw.put("pile", new HandleSeeDiscardPile());

        commandsAdventureCardWait.put("card", new HandleShowAdventureCard());
        commandsAdventureCardWait.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardWait.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardWait.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardPlanets.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardPlanets.put("occupy", new HandlePlanetsCommand());
        commandsAdventureCardPlanets.put("card", new HandleShowAdventureCard());
        commandsAdventureCardPlanets.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardPlanets.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardPlanets.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardAbandonedStation.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardAbandonedStation.put("occupy", new HandleAbandonedStationCommand());
        commandsAdventureCardAbandonedStation.put("card", new HandleShowAdventureCard());
        commandsAdventureCardAbandonedStation.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardAbandonedStation.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardAbandonedStation.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardEpidemic.put("apply", new HandleEpidemicCommand());
        commandsAdventureCardEpidemic.put("card", new HandleShowAdventureCard());
        commandsAdventureCardEpidemic.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardEpidemic.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardEpidemic.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardStardust.put("apply", new HandleStardustCommand());
        commandsAdventureCardStardust.put("card", new HandleShowAdventureCard());
        commandsAdventureCardStardust.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardStardust.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardStardust.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardOpenSpace.put("apply", new HandleOpenSpaceCommand());
        commandsAdventureCardOpenSpace.put("card", new HandleShowAdventureCard());
        commandsAdventureCardOpenSpace.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardOpenSpace.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardOpenSpace.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardAbandonedShip.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardAbandonedShip.put("occupy", new HandleAbandonedShipCommand());
        commandsAdventureCardAbandonedShip.put("card", new HandleShowAdventureCard());
        commandsAdventureCardAbandonedShip.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardAbandonedShip.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardAbandonedShip.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardSlavers.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardSlavers.put("apply", new HandleSlaversCommand());
        commandsAdventureCardSlavers.put("card", new HandleShowAdventureCard());
        commandsAdventureCardSlavers.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardSlavers.put("fight", new HandleSlaversCommand());
        commandsAdventureCardSlavers.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardSlavers.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardSmugglers.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardSmugglers.put("apply", new HandleSmugglersCommand());
        commandsAdventureCardSmugglers.put("card", new HandleShowAdventureCard());
        commandsAdventureCardSmugglers.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardSmugglers.put("fight", new HandleSmugglersCommand());
        commandsAdventureCardSmugglers.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardSmugglers.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardMeteorSwarm.put("card", new HandleShowAdventureCard());
        commandsAdventureCardMeteorSwarm.put("meteor", new HandleMeteorSwarmCommand());
        commandsAdventureCardMeteorSwarm.put("manage", new HandleMeteorSwarmCommand());
        commandsAdventureCardMeteorSwarm.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardMeteorSwarm.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardMeteorSwarm.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardPiratesBlast.put("card", new HandleShowAdventureCard());
        commandsAdventureCardPiratesBlast.put("blast", new HandlePiratesCommand());
        commandsAdventureCardPiratesBlast.put("manage", new HandlePiratesCommand());
        commandsAdventureCardPiratesBlast.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardPiratesBlast.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardPiratesBlast.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardPirates.put("skip", new HandleSkipAdventureCard());
        commandsAdventureCardPirates.put("apply", new HandlePiratesCommand());
        commandsAdventureCardPirates.put("card", new HandleShowAdventureCard());
        commandsAdventureCardPirates.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardPirates.put("fight", new HandlePiratesCommand());
        commandsAdventureCardPirates.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardPirates.put("show", new HandleShowPlayerShipboard());


        commandsAdventureCardWarzone.put("card", new HandleShowAdventureCard());
        commandsAdventureCardWarzone.put("first", new HandleWarzoneCommand());
        commandsAdventureCardWarzone.put("second", new HandleWarzoneCommand());
        commandsAdventureCardWarzone.put("third", new HandleWarzoneCommand());
        commandsAdventureCardWarzone.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardWarzone.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardWarzone.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardWarzoneBlast.put("card", new HandleShowAdventureCard());
        commandsAdventureCardWarzoneBlast.put("blast", new HandleWarzoneCommand());
        commandsAdventureCardWarzoneBlast.put("manage", new HandleWarzoneCommand());
        commandsAdventureCardWarzoneBlast.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardWarzoneBlast.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardWarzoneBlast.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardWarzoneGoods.put("card", new HandleShowAdventureCard());
        commandsAdventureCardWarzoneGoods.put("goodspenalty", new HandleWarzoneCommand());
        commandsAdventureCardWarzoneGoods.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardWarzoneGoods.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardWarzoneGoods.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardWarzoneCrew.put("card", new HandleShowAdventureCard());
        commandsAdventureCardWarzoneCrew.put("crewpenalty", new HandleWarzoneCommand());
        commandsAdventureCardWarzoneCrew.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardWarzoneCrew.put("show", new HandleShowPlayerShipboard());
        commandsAdventureCardWarzoneCrew.put("flightboard", new HandleFlightBoard());


        commandsAdventureCardStall.put("card", new HandleShowAdventureCard());
        commandsAdventureCardStall.put("flightboard", new HandleFlightBoard());
        commandsAdventureCardStall.put("shipboard", new HandleShowShipboard());
        commandsAdventureCardStall.put("show", new HandleShowPlayerShipboard());

        commandsFinalPhase.put("exit", new HandleExit());


    }

    public static Map<String, ClientCommand> getCommandsPreLobbyPhase() {
        return commandsPreLobbuPhase;
    }

    public static Map<String, ClientCommand> getCommandsLobbyPhase() {
        return commandsLobbuPhase;
    }

    public static Map<String, ClientCommand> getCommandsAssemblyPhase() {
        return commandsAssemblyPhase;
    }

    public static Map<String, ClientCommand> getCommandsWeldPhase() {
        return commandsWeldPhase;
    }

    public static Map<String, ClientCommand> getCommandsPreFlightPhase() {
        return commandsPreFlightPhase;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardDraw() {
        return commandsAdventureCardDraw;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardWait() {
        return commandsAdventureCardWait;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardPlanets() {
        return commandsAdventureCardPlanets;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardSlavers() {
        return commandsAdventureCardSlavers;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardSmugglers() {
        return commandsAdventureCardSmugglers;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardWarzone() {
        return commandsAdventureCardWarzone;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardWarzoneBlast() {
        return commandsAdventureCardWarzoneBlast;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardWarzoneCrew() {
        return commandsAdventureCardWarzoneCrew;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardWarzoneGoods() {
        return commandsAdventureCardWarzoneGoods;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardPirates() {
        return commandsAdventureCardPirates;
    }


    public static Map<String, ClientCommand> getCommandsAdventureCardMeteorSwarm() {
        return commandsAdventureCardMeteorSwarm;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardPiratesBlast() {
        return commandsAdventureCardPiratesBlast;
    }


    public static Map<String, ClientCommand> getCommandsAdventureCardAbandonedShip() {
        return commandsAdventureCardAbandonedShip;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardAbandonedStation() {
        return commandsAdventureCardAbandonedStation;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardEpidemic() {
        return commandsAdventureCardEpidemic;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardStardust() {
        return commandsAdventureCardStardust;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardOpenSpace() {
        return commandsAdventureCardOpenSpace;
    }

    public static Map<String, ClientCommand> getCommandsAdventureCardStall() {
        return commandsAdventureCardStall;
    }

    public static Map<String, ClientCommand> getCommandsFinalPhase() {
        return commandsFinalPhase;
    }


}
