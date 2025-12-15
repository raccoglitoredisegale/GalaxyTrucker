package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Exception.GenericCommnadException;
import it.polimi.ingsw.View.TUIState;

import java.util.Map;

public class ClientCommandHandlerDispatcher {
    private final Client client;

    private final Map<String, ClientCommand> commandsPreLobbyPhaseMap = ClientCommandRegistry.getCommandsPreLobbyPhase();
    private final Map<String, ClientCommand> commandsLobbyPhaseMap = ClientCommandRegistry.getCommandsLobbyPhase();
    private final Map<String, ClientCommand> commandAssemblyPhaseMap = ClientCommandRegistry.getCommandsAssemblyPhase();
    private final Map<String, ClientCommand> commandsWeldPhaseMap = ClientCommandRegistry.getCommandsWeldPhase();
    private final Map<String, ClientCommand> commandsPreFlightPhase = ClientCommandRegistry.getCommandsPreFlightPhase();
    private final Map<String, ClientCommand> commandsAdventureCardDraw = ClientCommandRegistry.getCommandsAdventureCardDraw();
    private final Map<String, ClientCommand> commandsAdventureCardWait = ClientCommandRegistry.getCommandsAdventureCardWait();
    private final Map<String, ClientCommand> commandsAdventureCardPlanets = ClientCommandRegistry.getCommandsAdventureCardPlanets();
    private final Map<String, ClientCommand> commandsAdventureCardAbandonedStation = ClientCommandRegistry.getCommandsAdventureCardAbandonedStation();
    private final Map<String, ClientCommand> commandsAdventureCardAbandonedShip = ClientCommandRegistry.getCommandsAdventureCardAbandonedShip();
    private final Map<String, ClientCommand> commandsAdventureCardSlavers = ClientCommandRegistry.getCommandsAdventureCardSlavers();
    private final Map<String, ClientCommand> commandsAdventureCardSmugglers = ClientCommandRegistry.getCommandsAdventureCardSmugglers();
    private final Map<String, ClientCommand> commandsAdventureCardPirates = ClientCommandRegistry.getCommandsAdventureCardPirates();
    private final Map<String, ClientCommand> commandsAdventureCardPiratesBlast = ClientCommandRegistry.getCommandsAdventureCardPiratesBlast();
    private final Map<String, ClientCommand> commandsAdventureCardMeteorSwarm = ClientCommandRegistry.getCommandsAdventureCardMeteorSwarm();
    private final Map<String, ClientCommand> commandsAdventureCardEpidemic = ClientCommandRegistry.getCommandsAdventureCardEpidemic();
    private final Map<String, ClientCommand> commandsAdventureCardStardust = ClientCommandRegistry.getCommandsAdventureCardStardust();
    private final Map<String, ClientCommand> commandsAdventureCardOpenSpace = ClientCommandRegistry.getCommandsAdventureCardOpenSpace();
    private final Map<String, ClientCommand> commandsAdventureCardWarzone = ClientCommandRegistry.getCommandsAdventureCardWarzone();
    private final Map<String, ClientCommand> commandsAdventureCardWarzoneBlast = ClientCommandRegistry.getCommandsAdventureCardWarzoneBlast();
    private final Map<String, ClientCommand> commandsAdventureCardWarzoneCrew = ClientCommandRegistry.getCommandsAdventureCardWarzoneCrew();
    private final Map<String, ClientCommand> commandsAdventureCardWarzoneGoods = ClientCommandRegistry.getCommandsAdventureCardWarzoneGoods();


    private final Map<String, ClientCommand> commandsAdventureCardStall = ClientCommandRegistry.getCommandsAdventureCardStall();
    private final Map<String, ClientCommand> commandsFinalPhase = ClientCommandRegistry.getCommandsFinalPhase();


    public ClientCommandHandlerDispatcher(Client client) {
        this.client = client;
    }

    public void dispatch(String clientCommand) throws GenericCommnadException {
        String key = clientCommand.split("\\s+")[0];
        ClientCommand command = null;
        switch (client.getTUI().getTUIState()) {
            case PRE_LOBBY:
                command = commandsPreLobbyPhaseMap.get(key);
                break;

            case LOBBY:
                command = commandsLobbyPhaseMap.get(key);
                break;

            case ASSEMBLY_PHASE:
                command = commandAssemblyPhaseMap.get(key);
                break;

            case TUIState.ADVENTURE_CARD_DRAW:
                command = commandsAdventureCardDraw.get(key);
                break;

            case TUIState.ADVENTURE_CARD_WAIT:
                command = commandsAdventureCardWait.get(key);
                break;

            case TUIState.ADVENTURE_CARD_PLANETS:
                command = commandsAdventureCardPlanets.get(key);
                break;

            case TUIState.ADVENTURE_CARD_EPIDEMIC:
                command = commandsAdventureCardEpidemic.get(key);
                break;

            case TUIState.ADVENTURE_CARD_STARDUST:
                command = commandsAdventureCardStardust.get(key);
                break;

            case TUIState.ADVENTURE_CARD_OPEN_SPACE:
                command = commandsAdventureCardOpenSpace.get(key);
                break;

            case TUIState.ADVENTURE_CARD_METEOR_SWARM:
                command = commandsAdventureCardMeteorSwarm.get(key);
                break;

            case TUIState.ADVENTURE_CARD_ABANDONED_STATION:
                command = commandsAdventureCardAbandonedStation.get(key);
                break;

            case TUIState.ADVENTURE_CARD_ABANDONED_SHIP:
                command = commandsAdventureCardAbandonedShip.get(key);
                break;

            case TUIState.ADVENTURE_CARD_SLAVERS:
                command = commandsAdventureCardSlavers.get(key);
                break;

            case TUIState.ADVENTURE_CARD_SMUGGLERS:
                command = commandsAdventureCardSmugglers.get(key);
                break;

            case TUIState.ADVENTURE_CARD_PIRATES:
                command = commandsAdventureCardPirates.get(key);
                break;

            case TUIState.ADVENTURE_CARD_PIRATES_BLAST:
                command = commandsAdventureCardPiratesBlast.get(key);
                break;

            case TUIState.ADVENTURE_CARD_WARZONE:
                command = commandsAdventureCardWarzone.get(key);
                break;

            case TUIState.ADVENTURE_CARD_WARZONE_BLAST:
                command = commandsAdventureCardWarzoneBlast.get(key);
                break;

            case TUIState.ADVENTURE_CARD_WARZONE_CREW:
                command = commandsAdventureCardWarzoneCrew.get(key);
                break;

            case TUIState.ADVENTURE_CARD_WARZONE_GOODS:
                command = commandsAdventureCardWarzoneGoods.get(key);
                break;

            case WELD_COMPONENT_CARD:
                command = commandsWeldPhaseMap.get(key);
                break;

            case PRE_FLIGHT_PHASE:
                command = commandsPreFlightPhase.get(key);
                break;

            case STALL:
                command = commandsAdventureCardStall.get(key);
                break;

            case FINAL_PHASE:
                command = commandsFinalPhase.get(key);
                break;

            default:
                System.out.println("Unknown TUI state");
        }


        try {
            if (command != null) {
                command.execute(this.client, clientCommand); // esegui il comando con gli argomenti args
            } else {
                client.getTUI().print("Unknown command: " + clientCommand +
                        "\nType 'help' to see available commands."); // comando non riconosciuto
            }
        } catch (GenericCommnadException e) {
            throw new GenericCommnadException(e.getMessage());
        }

    }


    public void handleJoinLobby(String command) {
        System.out.println(command);
    }
}
