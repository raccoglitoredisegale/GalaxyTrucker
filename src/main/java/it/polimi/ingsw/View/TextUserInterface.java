package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.Exception.InputException;
import it.polimi.ingsw.Network.Client.Exception.InvalidComponentCardID;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.*;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.*;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.StallMessage;

import java.util.ArrayList;
import java.util.Scanner;

public class TextUserInterface {

    private final Scanner scanner;

    private final Client client;

    private final Boolean running;
    private final ModelElementPrinter modelPrinter;
    private final AdventureCardInteraction adventureCardInteraction;
    private final ArrayList<String> stalled;
    private TUIState state = TUIState.PRE_LOBBY;

    public TextUserInterface(Client client) {
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.client = client;
        this.modelPrinter = new ModelElementPrinter();
        this.adventureCardInteraction = new AdventureCardInteraction();
        this.stalled = new ArrayList<>();
    }

    public void start(int mode) {
        if (client == null) {
            System.err.println("TUI Error: Client reference not set. Exiting.");
            return;
        }


        System.out.println("Welcome, type 'help' to show commands or 'exit' to quit.");
        printHelpForCurrentState();

        try {
            while (running) {
                System.out.print(state.getState() + "> ");
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("help")) {
                    printHelpForCurrentState();
                } else if (running) {
                    processCommand(command);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in main loop: " + e.getMessage());
        } finally {
            System.out.println("TextUserInterface stopped.");
        }
    }

    public void processCommand(String command) {
        try {
            this.client.getCommandMatchHandlerDispatcher().dispatch(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printHelpForCurrentState() {
        if (state == TUIState.PRE_LOBBY) {
            System.out.println("\n" + "You're in the PRE-LOBBY PHASE, these are the commands:\n"
                    + "    - type: lobbies to show available lobbies\n"
                    + "    - type: create <lobby-name> <max-players> to create a new lobby with max (from 2 to 4) members\n"
                    + "    - type: join <lobby-id> to join a lobby");
        } else if (state == TUIState.LOBBY) {
            System.out.println("\n" + "You're in the LOBBY PHASE, these are the commands:\n"
                    + "    - type: color <color-type> to choose your color\n"
                    + "    - type: opponents to see the other player's in the current lobby\n"
                    + "    - type: leave to leave the lobby\n"
                    + "    - type: start to start the game");
        } else if (state == TUIState.ASSEMBLY_PHASE) {
            System.out.println("\n" + "You're in the ASSEMBLY PHASE, these are the commands:\n"
                    + "    - type: finished to say you're done (you can't go back)\n"
                    + "    - type: saved to see the component cards you saved\n"
                    + "    - type: remove <index> to remove the component card from the list you saved\n"
                    + "    - type: draw to draw the component card\n"
                    + "    - type: revealed to see the component cards already revealed\n"
                    + "    - type: take <index> to take the component card from the revealed deck\n"
                    + "    - type: opponents to see the name of the other player in the lobby\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: discover <deck-number> to discover the corresponding deck of adventure cards\n" // TODO
                    + "    - type: hourglass to see how much time is left until the hourglass expires\n"
                    + "    - type: rotate to rotate the hourglass\n"
                    + "    - type: shipboard to see your shipboard\n");
        } else if (state == TUIState.WELD_COMPONENT_CARD) {
            System.out.println("\n" + "You must now decide what to do with the component, these are the commands:\n"
                    + "    - type: rotate to rotate the component\n"
                    + "    - type: try <row> <col> to see how the component would look placed in (row;col)\n"
                    + "    - type: place <row> <col> to place the component card\n"
                    + "    - type: reserve to save the component card\n"
                    + "    - type: discard to discard the current card\n"
                    + "    - type: saved to see the component cards you saved\n"
                    + "    - type: opponents to see the name of the other player in the lobby\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n");
        } else if (state == TUIState.PRE_FLIGHT_PHASE) {
            System.out.println("\n" + "You're in the PRE FLIGHT PHASE, these are the commands:\n"
                    + "    - type: flightboard to see your flight board\n"
                    + "    - type: place to place your rocket on the flight board\n"
                    + "    - type: rotate to rotate the hourglass\n"
                    + "    - type: hourglass to see how much time is left until the hourglass expires\n");

        } else if (state == TUIState.ADVENTURE_CARD_DRAW) {
            System.out.println("\n" + "You're in the ADVENTURE CARD DRAWING PHASE, only the leader can draw an adventure card. These are the commands:\n"
                    + "    - type: flightboard to see your flight board\n"
                    + "    - type: pile to see your discard pile\n"
                    + "    - type: draw to draw the adventure card (only the leader)\n"
                    + "    - type: stall to retire from the game\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard");
        } else if (state == TUIState.ADVENTURE_CARD_WAIT) {
            System.out.println("\n" + "You're in the ADVENTURE CARD WAITING PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n");
        } else if (state == TUIState.ADVENTURE_CARD_PLANETS) {
            System.out.println("\n" + "You're in the ADVENTURE CARD PLANETS PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: occupy to use the card\n");
        } else if (state == TUIState.ADVENTURE_CARD_ABANDONED_STATION) {
            System.out.println("\n" + "You're in the ADVENTURE CARD ABANDONED STATION PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: occupy to use the card\n");
        } else if (state == TUIState.ADVENTURE_CARD_METEOR_SWARM) {
            System.out.println("\n" + "You're in the ADVENTURE CARD METEOR SWARM PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: meteor to throw the dice (only the leader)\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: manage to manage the meteor\n");

        } else if (state == TUIState.ADVENTURE_CARD_ABANDONED_SHIP) {
            System.out.println("\n" + "You're in the ADVENTURE CARD ABANDONED SHIP PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: occupy ship to use the card\n");
        } else if (state == TUIState.ADVENTURE_CARD_SLAVERS) {
            System.out.println("\n" + "You're in the ADVENTURE CARD SLAVERS PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: fight to battle the slavers\n"
                    + "    - type: apply to use the card (if you win the fight)\n");
        } else if (state == TUIState.ADVENTURE_CARD_SMUGGLERS) {
            System.out.println("\n" + "You're in the ADVENTURE CARD SMUGGLERS PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: fight to battle the slavers\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: apply to use the card (if you win the fight)\n");
        } else if (state == TUIState.ADVENTURE_CARD_EPIDEMIC) {
            System.out.println("\n" + "You're in the ADVENTURE CARD EPIDEMIC PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: apply to apply epidemic");

        } else if (state == TUIState.ADVENTURE_CARD_STARDUST) {
            System.out.println("\n" + "You're in the ADVENTURE CARD STARDUST PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: apply to apply stardust");

        } else if (state == TUIState.ADVENTURE_CARD_OPEN_SPACE) {
            System.out.println("\n" + "You're in the ADVENTURE CARD OPEN SPACE PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: apply to apply open space");

        } else if (state == TUIState.ADVENTURE_CARD_PIRATES) {
            System.out.println("\n" + "You're in the ADVENTURE CARD PIRATES PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: skip to skip the adventure card\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: fight to battle with the pirates\n"
                    + "    - type: apply to use the card (if you win the fight)\n");
        } else if (state == TUIState.ADVENTURE_CARD_PIRATES_BLAST) {
            System.out.println("\n" + "You're in the ADVENTURE CARD PIRATE BLASTS PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: blast to throw the dice\n"
                    + "    - type: manage to manage a blast\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n");
        } else if (state == TUIState.ADVENTURE_CARD_WARZONE) {
            System.out.println("\n" + "You're in the ADVENTURE CARD WARZONE PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: first to calculate the first parameter\n"
                    + "    - type: second to calculate the second parameter\n"
                    + "    - type: third to calculate the third parameter\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: flightboard to see the flight board\n");
        } else if (state == TUIState.ADVENTURE_CARD_WARZONE_CREW) {
            System.out.println("\n" + "You're in the ADVENTURE CARD WARZONE CREW PENALTY PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: crewpenalty to apply the penalty\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: flightboard to see the flight board\n");
        } else if (state == TUIState.ADVENTURE_CARD_WARZONE_GOODS) {
            System.out.println("\n" + "You're in the ADVENTURE CARD WARZONE GOODS PENALTY PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: goodspenalty to apply the penalty\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: flightboard to see the flight board\n");
        } else if (state == TUIState.ADVENTURE_CARD_WARZONE_BLAST) {
            System.out.println("\n" + "You're in the ADVENTURE CARD WARZONE BLAST PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: blast to throw the dice\n"
                    + "    - type: manage to manage a blast\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: shipboard to see your shipboard\n"
                    + "    - type: show <player-name> to see the player's shipboard\n");
        } else if (state == TUIState.STALL) {
            System.out.println("\n" + "You're in the STALL PHASE, these are the commands:\n"
                    + "    - type: card to see the adventure card\n"
                    + "    - type: flightboard to see the flight board\n"
                    + "    - type: show <player-name> to see the player's shipboard\n"
                    + "    - type: shipboard to see your shipboard\n");
        } else if (state == TUIState.FINAL_PHASE) {
            System.out.println("\n" + "You're in the FINAL PHASE, these are the commands:\n"
                    + "    - type: exit");
        }
    }


    public void handleBroadcastMessageByServer(BroadcastMessage msg) {
        switch (msg.getSpecificType()) {
            case GAME_STARTED:
                state = TUIState.ASSEMBLY_PHASE;
                System.out.println("\n\n" +
                        "\t\t\t\t╔══════════════════════════════════════╗\n" +
                        "\t\t\t\t║ The lobby creator started the game   ║\n" +
                        "\t\t\t\t╚══════════════════════════════════════╝");

                printHelpForCurrentState();
                System.out.print(state.getState() + "> ");
                break;
            case FINISHED_ASSEMBLY_PHASE:
                state = TUIState.ADVENTURE_CARD_DRAW;
                System.out.println("\n\n" +
                        "\t\t\t\t╔═════════════════════════════════════════╗\n" +
                        "\t\t\t\t║ You've now finished the assembly phase  ║\n" +
                        "\t\t\t\t║     You're now in FLIGHT PHASE !!!      ║\n" +
                        "\t\t\t\t╚═════════════════════════════════════════╝");
                client.getShipboard().validateComponentAfterAssemblyPhase();
                printHelpForCurrentState();
                System.out.print(state.getState() + "> ");
                break;
            case BC_ADVENTURE_CARD:
                ContinueAdventureCardMessage adventureCardMessage = ((BroadcastMessageAdventureCard) msg).getMessage();

                if (!adventureCardMessage.getAdventureCard().getAlreadyShown()) {
                    System.out.print("\n\n\nThe leader drew the adventure card: \n\n");
                    modelPrinter.printCard(adventureCardMessage.getAdventureCard());
                } else print("\n============= \uD83D\uDE80 Game Status Updated \uD83D\uDE80 =============\n ");
                if (msg.getSpecificType() != MessageType.WARZONE_CREW_PENALTY && msg.getSpecificType() != MessageType.WARZONE_GOODS_PENALTY && msg.getSpecificType() != MessageType.WARZONE_BLAST_PENALTY)
                    System.out.print(state.getState() + "> ");
                break;
            case END_ADVENTURE_CARD_PHASE:
                if (((BroadCastMessageEndAdventureCardPhase) msg).getReason() == 0)
                    System.out.println("\nThere are no cards left, the game is completed\n");
                else if (((BroadCastMessageEndAdventureCardPhase) msg).getReason() == 1)
                    System.out.println("\nAll players are stalled, the game is completed\n");
                else System.out.println("\nGame end conditions satisfied, the game is completed\n");
                modelPrinter.printFinalResults(msg);
                System.out.print(state.getState() + "> ");

                break;
            case MessageType.STALL_PLAYER_OK:
                StallMessage stallMessage = ((BroadcastMessageStall) msg).getMessage();
                System.out.println("Player: <" + stallMessage.getstalledPlayerUsername() + "> decided to stall!");
                System.out.print(state.getState() + "> ");

                break;
            case MessageType.ADVENTURE_CARD_STALLED:
                BroadcastMessageAdventureCardStall message = ((BroadcastMessageAdventureCardStall) msg);
                for (String name : message.getStalledPlayers()) {
                    if (!stalled.contains(name)) stalled.add(name);
                }
                break;
            case MessageType.BLAST_INFO:
                System.out.println("A blast is heading towards " + client.getCurrentPlayer() + "!");
                modelPrinter.printDiceAndInfo(client.getClientGameController());
                System.out.print(state.getState() + "> ");
                break;

            case MessageType.METEOR_INFO:
                if (getTUIState() == TUIState.STALL) print("A meteor is heading towards the players!");
                else print("A meteor is heading towards you!");
                modelPrinter.printDiceAndInfo(client.getClientGameController());
                System.out.print(state.getState() + "> ");
                break;

            case MessageType.WARZONE_CONTROL_INFO:
                BroadcastMessageWarzoneInfo warzoneInfo = ((BroadcastMessageWarzoneInfo) msg);
                print("\nPlayer: " + ((BroadcastMessageWarzoneInfo) msg).getPlayerName() + " passed the control with " + ((BroadcastMessageWarzoneInfo) msg).getCriteria() + " = " + ((BroadcastMessageWarzoneInfo) msg).getParameter());
                print("\n\n============= \uD83D\uDE80 Game Status Updated \uD83D\uDE80 =============\n ");
                if (((BroadcastMessageWarzoneInfo) msg).getCurrentPlayerName().equals("0000"))
                    print("\nPlayer: " + ((BroadcastMessageWarzoneInfo) msg).getLooser() + " lost the comparison and is facing " + ((BroadcastMessageWarzoneInfo) msg).getPenalty() + " penalty\n");
                System.out.print(state.getState() + "> ");
                break;

            case MessageType.ADVENTURE_CARD_COMPLETED:
                print("\n\n============= \uD83D\uDE80 Game Status Updated \uD83D\uDE80 =============\n ");
                print("\ncard completed\n");
                for (String name : stalled) {
                    print("\nPlayer: " + name + " is stalled\n");
                }
                if (!stalled.contains(client.getUsername())) {
                    setTUIState(TUIState.ADVENTURE_CARD_DRAW);
                }
                System.out.print(state.getState() + "> ");
                break;
            default:
                System.err.println("Unknown broadcast message: " + msg.getSpecificType());
                break;
        }
    }

    public TUIState getTUIState() {
        return state;
    }

    public void setTUIState(TUIState state) {
        this.state = state;
    }

    public ModelElementPrinter getModelElementPrinter() {
        return modelPrinter;
    }

    public void print(String stringToPrint) {
        System.out.println(stringToPrint);
    }

    /**
     * @param componentCard It is the component card that the client
     *                      has just drawn from the deck (it can be either face-down or face-up cards)
     *                      <p>
     *                      This function manages the phase in which the client has in his hand the card he has just drawn from the deck.
     *                      It must therefore manage all the possible actions that the client can perform in this phase, which are:
     *                      <p>
     *                      rotate 					-> to rotate the component
     *                      place <x> <y> 			-> to see the name of the other player in the lobby
     *                      try <x> <y>				-> to see how the component would look placed in (x;y)
     *                      reserve 					-> to save the component card
     *                      discard 					-> to discard the current card
     *                      saved 					-> to see the component cards you saved
     *                      opponents 				-> to see the name of the other player in the lobby
     *                      show <player-name> 		-> to see the player's shipboard
     *                      shipboard 				-> to see your shipboard
     **/
    public void componentCardDecision(ComponentCard componentCard) throws Exception {
        printHelpForCurrentState();
        modelPrinter.printSingleCard(componentCard);
        String commandID;
        String[] parts;
        while (running) {
            System.out.print(state.getState() + "> ");
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("help")) {
                printHelpForCurrentState();
            } else {
                parts = command.split("\\s+");
                commandID = parts[0];
                if (commandID.equals("opponents") || commandID.equals("show") || commandID.equals("shipboard")) {
                    processCommand(command);
                } else {
                    switch (commandID) {
                        case "discard":
                            if (parts.length == 1) {
                                try {
                                    command = command + " " + componentCard.getId();
                                    processCommand(command);
                                    return;
                                } catch (Exception e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            } else {
                                System.out.println("The command is correct, but the syntax is wrong!" +
                                        "\nType 'help' to see available commands.");
                            }
                        case "place": // if the user place the component in the wrong position he can retype place with the new (x;y)
                            if (parts.length != 3) {
                                System.out.println("The command is correct, but the syntax is wrong!" +
                                        "\nType 'help' to see available commands.");
                            } else {
                                try {
                                    this.client.getCommandMatchHandlerDispatcher().dispatch(command + " " + componentCard.getId());
                                    return;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        case "try":
                            try {
                                if (parts.length != 3) {
                                    System.out.println("The command is correct, but the syntax is wrong!" +
                                            "\nType 'help' to see available commands.");
                                } else {
                                    try {
                                        client.getShipboard().addComponent(
                                                componentCard, Integer.parseInt(parts[1]) - 5, Integer.parseInt(parts[2]) - 4);
                                        modelPrinter.printShipboard(client.getShipboard());

                                        client.getShipboard().removeComponent(
                                                Integer.parseInt(parts[1]) - 5, Integer.parseInt(parts[2]) - 4);
                                    } catch (InputException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "saved":
                            if (!command.equals("saved")) {
                                System.out.println("The command is correct, but the syntax is wrong!" +
                                        "\nType 'help' to see available commands.");
                            }
                            if (client.getShipboard().getSavedComponentCards().isEmpty()) {
                                System.out.println("You don't have any saved cards!");
                            } else {
                                modelPrinter.printCardList(client.getShipboard().getSavedComponentCards());
                            }
                            break;
                        case "reserve":
                            if (parts.length != 1) {
                                System.out.println("The command is correct, but the syntax is wrong!" +
                                        "\nType 'help' to see available commands.");
                            } else if (client.getShipboard().getSavedComponentCards().size() == 2) {
                                System.out.println("You have run out of spaces for component cards!\n");
                            } else if (client.getShipboard().getSavedComponentCards().contains(componentCard)) {
                                System.out.println("This card, since it was in the saved pile, " +
                                        "you can only place it or discard it.!\n");
                            } else {
                                try {
                                    client.getShipboard().addToSavedComponentCards(
                                            componentCard
                                    );
                                } catch (IndexOutOfBoundsException | InvalidComponentCardID e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            state = TUIState.ASSEMBLY_PHASE;
                            return;
                        case "rotate":
                            if (parts.length != 1) {
                                System.out.println("The command is correct, but the syntax is wrong!" +
                                        "\nType 'help' to see available commands.");
                            } else {
                                componentCard.rotation();
                                modelPrinter.printSingleCard(componentCard);
                            }
                            break;
                        default:
                            System.out.println("Unknown command: " + command +
                                    "\nType 'help' to see available commands.");
                            break;
                    }
                }
            }
        }
    }

    public PlanetsCardMessage handlePlanets(Client client) {
        PlanetsCardMessage message = null;
        message = adventureCardInteraction.calculatePlanetsCardMessage(client);
        return message;
    }

    public AbandonedStationCardMessage handleAbandonedStation(Client client) {
        AbandonedStationCardMessage message = null;
        message = adventureCardInteraction.calculateAbandonedStationCardMessage(client);
        return message;
    }

    public MeteorCardMessage handleMeteor(Client client, Character dimension, Character direction, int dice) {
        MeteorCardMessage message = null;
        message = adventureCardInteraction.calculateMeteorCardMessage(client, dimension, direction, dice);
        return message;
    }

    public OpenSpaceCardMessage handleOpenSpace(Client client) {
        OpenSpaceCardMessage message = null;
        message = adventureCardInteraction.calculateOpenSpaceMessage(client);
        return message;
    }

    public AbandonedShipCardMessage handleAbandonedShip(Client client) {
        AbandonedShipCardMessage message = null;
        message = adventureCardInteraction.calculateAbandonedShipMessage(client);
        return message;
    }

    public SlaversWonCardMessage handleSlaversControl(Client client) {
        SlaversWonCardMessage message = null;
        message = adventureCardInteraction.calculateSlaversWonMessage(client);
        return message;
    }

    public SlaversLostCardMessage handleSlaversLost(Client client) {
        SlaversLostCardMessage message = null;
        message = adventureCardInteraction.calculateSlaversLostMessage(client);
        return message;
    }

    public SmugglersWonCardMessage handleSmugglersControl(Client client) {
        SmugglersWonCardMessage message = null;
        message = adventureCardInteraction.calculateSmugglersWonMessage(client);
        return message;
    }

    public SmugglersLostCardMessage handleSmugglersLost(Client client) {
        SmugglersLostCardMessage message = null;
        message = adventureCardInteraction.calculateSmugglersLostMessage(client);
        return message;
    }

    public SmugglersCardMessage handleSmugglersMessage(Client client) {
        SmugglersCardMessage message = null;
        message = adventureCardInteraction.calculateSmugglersMessage(client);
        return message;
    }

    public PiratesWonCardMessage handlePiratesControl(Client client) {
        PiratesWonCardMessage message = null;
        message = adventureCardInteraction.calculatePiratesWonMessage(client);
        return message;
    }

    public BlastCardMessage handleBlast(Client client, Character dimension, Character direction, int dice) {
        BlastCardMessage message = null;
        message = adventureCardInteraction.calculateBlastCardMessage(client, dimension, direction, dice);
        return message;
    }

    public WarzoneControlCardMessage handleWarzoneCannon(Client client) {
        WarzoneControlCardMessage message = null;
        message = adventureCardInteraction.calculateWarzoneCannonMessage(client);
        return message;
    }

    public WarzoneControlCardMessage handleWarzoneEngine(Client client) {
        WarzoneControlCardMessage message = null;
        message = adventureCardInteraction.calculateWarzoneEngineMessage(client);
        return message;
    }

    public WarzoneCrewPenaltyCardMessage handleWarzoneCrewPenalty(Client client) {
        WarzoneCrewPenaltyCardMessage message = null;
        message = adventureCardInteraction.calculateWarzoneCrewPenaltyMessage(client);
        return message;
    }

    public WarzoneGoodsPenaltyCardMessage handleWarzoneGoodsPenalty(Client client) {
        WarzoneGoodsPenaltyCardMessage message = null;
        message = adventureCardInteraction.calculateWarzoneGoodsPenaltyMessage(client);
        return message;
    }


}
