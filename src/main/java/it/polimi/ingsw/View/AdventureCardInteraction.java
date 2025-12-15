package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.*;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.OccupantType;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.*;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.*;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.*;

public class AdventureCardInteraction {
    private final Scanner scanner = new Scanner(System.in);

    public PlanetsCardMessage calculatePlanetsCardMessage(Client client) {
        PlanetsCardMessage message = null;
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<ArrayList<Integer>, ArrayList<Goods>>();
        Planets planets = (Planets) client.getClientGameController().getCurrentAdventureCard();
        boolean finishStoring = false;
        boolean finishCard = false;
        boolean existingStoragePassed = false;
        Map<ArrayList<Integer>, Integer> usedStorage = new HashMap<>();
        int planetIndex;

        System.out.println(
                "In this phase you can manage your goods. It means you can reorganize your goods as you prefer. You can also get rid of them you want." +
                        "\nFirst choose the planet. Then type 'storage' followed by the coords x y and the goods you want to store there. An example is: 'storage 4 6 yellow yellow green' " +
                        "\nRepeat the process for every storage. Remember You can leave some resources on the planet by typing 'done', and all the remaining resources will be left on the planet. " +
                        "\nYou can type help to see the all the available commands\n\n");


        outer:
        while (!finishCard) {
            goodsMap.clear();
            usedStorage.clear();

            StringBuilder description = new StringBuilder();
            description.append("Planets discovered:\n");
            for (int i = 0; i < planets.getPlanetsList().size(); i++) {
                description.append("  - Planet ").append(i + 1).append(" contains: ");
                if (planets.getPlanetsList().get(i).isEmpty()) {
                    description.append("no goods");
                } else {
                    for (int j = 0; j < planets.getPlanetsList().get(i).size(); j++) {
                        description.append(planets.getPlanetsList().get(i).get(j));
                        if (j != planets.getPlanetsList().get(i).size() - 1) {
                            description.append(", ");
                        }
                    }
                }
                if (planets.getOccupedPlanets().get(i) == 1) {
                    description.append(" [already occupied]");
                }
                description.append("\n");
            }
            System.out.println(description);
            System.out.println("Choose the planet where you want to land <number>");


            try {
                planetIndex = scanner.nextInt();
                planetIndex--;
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
                continue;
            }
            if (planetIndex + 1 > ((Planets) client.getClientGameController().getCurrentAdventureCard()).getOccupedPlanets().size()) {
                System.out.println("Uncorrect index!");
                continue;
            }
            if (((Planets) client.getClientGameController().getCurrentAdventureCard()).getOccupedPlanets().get(planetIndex) == 1) {
                System.out.println("Planet already occupied!");
                continue;
            }

            ArrayList<Goods> totalGoodsList = new ArrayList<>();

            for (Integer[] storage : client.getShipboard().getStorageList()) {
                Hold hold = (Hold) client.getShipboard().getShipComponent(storage[0], storage[1]);
                List<Goods> goods = hold.getLoad();
                for (Goods good : goods) {
                    totalGoodsList.add(good);
                }
            }
            for (Goods good : planets.getPlanetsList().get(planetIndex)) {
                totalGoodsList.add(good);
            }

            inner:
            while (!finishStoring) {
                existingStoragePassed = false;
                System.out.println("\nYou have to manage these goods: ");


                for (Goods good : totalGoodsList) {
                    System.out.println(good);
                }

                System.out.print("Enter command: ");
                String line = scanner.nextLine().trim();

                if (line.startsWith("help")) {
                    System.out.println("Type: storage x y and the colors to store them" +
                            "\nType: ship to see your ship board" +
                            "\nType: planets to see the card" +
                            "\nType: restart to restart the process" +
                            "\nType: done to finish the process");
                    continue;
                }

                if (line.startsWith("storage")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length < 4) {
                        System.out.println("Not enough arguments. Use format: storage x y goods...");
                        continue;
                    }

                    try {
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        x = x - 5;
                        y = y - 4;

                        int goodsCount = parts.length - 3;
                        if (goodsCount < 1 || goodsCount > 3) {
                            System.out.println("You must provide between 1 and 3 goods.");
                            continue;
                        }

                        ArrayList<Goods> selectedGoodsList = new ArrayList<>();
                        boolean specialCargo = false;
                        for (int i = 3; i < parts.length; i++) {
                            Goods selectedGood;

                            try {
                                selectedGood = Goods.valueOf(parts[i].toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid goods type: " + parts[i] + ". Check spelling.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            if (!totalGoodsList.contains(selectedGood)) {
                                System.out.println("The good " + selectedGood + " is not available in your list.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            selectedGoodsList.add(selectedGood);

                            if (selectedGood == Goods.RED) {
                                specialCargo = true;
                            }
                        }

                        for (Integer[] storage : client.getShipboard().getStorageList()) {
                            if (storage[0] == x && storage[1] == y) {
                                existingStoragePassed = true;
                                break;
                            }
                        }

                        if (!existingStoragePassed) {
                            System.out.println("it doesn't seem to be an existing storage with these coordinates");
                            continue;
                        }

                        Hold hold = (Hold) client.getShipboard().getShipComponent(x, y);
                        if (goodsCount > hold.getCapacity()) {
                            System.out.println("This storage capacity is insufficient");
                            continue;
                        }

                        ArrayList<Integer> coordsForCheck = new ArrayList<>();
                        coordsForCheck.add(x);
                        coordsForCheck.add(y);
                        int alreadyUsed = usedStorage.getOrDefault(coordsForCheck, 0);
                        if (alreadyUsed + goodsCount > hold.getCapacity()) {
                            System.out.println("You cannot add this amount of goods here. Exceeds remaining capacity of: " + (hold.getCapacity() - alreadyUsed));
                            continue;
                        }

                        if (specialCargo && !hold.isSpecial()) {
                            System.out.println("This storage is not special");
                            continue;
                        }

                        ArrayList<Integer> coords = new ArrayList<>();
                        coords.add(x);
                        coords.add(y);
                        if (goodsMap.containsKey(coords)) {
                            goodsMap.get(coords).addAll(selectedGoodsList); //already existing at these coords
                        } else {
                            goodsMap.put(coords, new ArrayList<>(selectedGoodsList)); //create new coords key
                        }
                        for (Goods good : selectedGoodsList) {
                            totalGoodsList.remove(good);
                        }
                        usedStorage.merge(coordsForCheck, goodsCount, Integer::sum);
                        for (Goods good : selectedGoodsList) {
                            System.out.println("Stored good: " + good + " in " + (x + 5) + " " + (y + 4));
                        }


                    } catch (Exception e) {
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                } else if (line.startsWith("ship")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printShipboard(client.getShipboard());
                    continue;
                } else if (line.startsWith("planet")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printCard(client.getClientGameController().getCurrentAdventureCard());
                    continue;
                } else if (line.startsWith("restart")) {
                    continue outer;
                } else if (line.startsWith("done")) {
                    finishStoring = true;
                    finishCard = true;
                    message = new PlanetsCardMessage(MessageType.OCCUPY_PlANET, planetIndex, goodsMap);


                } else System.out.println("Unknown command: " + line);

            }
        }
        return message;
    }

    public AbandonedStationCardMessage calculateAbandonedStationCardMessage(Client client) {
        AbandonedStationCardMessage message = null;
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<ArrayList<Integer>, ArrayList<Goods>>();
        AbandonedStation abandonedStation = (AbandonedStation) client.getClientGameController().getCurrentAdventureCard();
        boolean finishStoring = false;
        boolean finishCard = false;
        boolean existingStoragePassed = false;
        Map<ArrayList<Integer>, Integer> usedStorage = new HashMap<>();

        System.out.println(
                "In this phase you can manage your goods. It means you can reorganize your goods as you prefer. You can also get rid of them you want." +
                        "\nType 'storage' followed by the coords x y and the goods you want to store there. An example is: 'storage 4 6 yellow yellow green' " +
                        "\n Repeat the process for every storage. Remember You can leave some resources on the station by typing 'done', and all the remaining resources will be left on the station. " +
                        "\nYou can type help to see the all the available commands");


        outer:
        while (!finishCard) {

            ArrayList<Goods> totalGoodsList = new ArrayList<>();

            for (Integer[] storage : client.getShipboard().getStorageList()) {
                Hold hold = (Hold) client.getShipboard().getShipComponent(storage[0], storage[1]);
                List<Goods> goods = hold.getLoad();
                for (Goods good : goods) {
                    totalGoodsList.add(good);
                }
            }
            for (Goods good : abandonedStation.getGoodsList()) {
                totalGoodsList.add(good);
            }


            inner:
            while (!finishStoring) {
                System.out.println("\nYou have to manage these goods: ");


                for (Goods good : totalGoodsList) {
                    System.out.println(good);
                }

                System.out.print("Enter command: ");
                String line = scanner.nextLine().trim();

                if (line.startsWith("help")) {
                    System.out.println("Type: storage x y and the colors to store them" +
                            "\nType: ship to see your ship board" +
                            "\nType: abandoned station to see the card" +
                            "\nType: restart to restart the process" +
                            "\nType: done to finish the process");
                    continue;
                }

                if (line.startsWith("storage")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length < 4) {
                        System.out.println("Not enough arguments. Use format: storage x y goods...");
                        continue;
                    }

                    try {
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        x = x - 5;
                        y = y - 4;

                        int goodsCount = parts.length - 3;
                        if (goodsCount < 1 || goodsCount > 3) {
                            System.out.println("You must provide between 1 and 3 goods.");
                            continue;
                        }

                        ArrayList<Goods> selectedGoodsList = new ArrayList<>();
                        boolean specialCargo = false;
                        for (int i = 3; i < parts.length; i++) {
                            Goods selectedGood;

                            try {
                                selectedGood = Goods.valueOf(parts[i].toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid goods type: " + parts[i] + ". Check spelling.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            if (!totalGoodsList.contains(selectedGood)) {
                                System.out.println("The good " + selectedGood + " is not available in your list.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            selectedGoodsList.add(selectedGood);

                            if (selectedGood == Goods.RED) {
                                specialCargo = true;
                            }
                        }

                        for (Integer[] storage : client.getShipboard().getStorageList()) {
                            if (storage[0] == x && storage[1] == y) {
                                existingStoragePassed = true;
                                break;
                            }
                        }

                        if (!existingStoragePassed) {
                            System.out.println("it doesn't seem to be an existing storage with these coordinates");
                            continue;
                        }

                        Hold hold = (Hold) client.getShipboard().getShipComponent(x, y);
                        if (goodsCount > hold.getCapacity()) {
                            System.out.println("This storage capacity is insufficient");
                            continue;
                        }

                        ArrayList<Integer> coordsForCheck = new ArrayList<>();
                        coordsForCheck.add(x);
                        coordsForCheck.add(y);
                        int alreadyUsed = usedStorage.getOrDefault(coordsForCheck, 0);
                        if (alreadyUsed + goodsCount > hold.getCapacity()) {
                            System.out.println("You cannot add this amount of goods here. Exceeds remaining capacity of: " + (hold.getCapacity() - alreadyUsed));
                            continue;
                        }

                        if (specialCargo && !hold.isSpecial()) {
                            System.out.println("This storage is not special");
                            continue;
                        }

                        ArrayList<Integer> coords = new ArrayList<>();
                        coords.add(x);
                        coords.add(y);
                        if (goodsMap.containsKey(coords)) {
                            goodsMap.get(coords).addAll(selectedGoodsList); //already existing at these coords
                        } else {
                            goodsMap.put(coords, new ArrayList<>(selectedGoodsList)); //create new coords key
                        }
                        for (Goods good : selectedGoodsList) {
                            totalGoodsList.remove(good);
                        }
                        usedStorage.merge(coordsForCheck, goodsCount, Integer::sum);
                        for (Goods good : selectedGoodsList) {
                            System.out.println("Stored good: " + good + " in " + (x + 5) + " " + (y + 4));
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                }
                if (line.startsWith("ship")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printShipboard(client.getShipboard());
                    continue;
                }
                if (line.startsWith("abandoned station")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printCard(client.getClientGameController().getCurrentAdventureCard());
                    continue;
                }
                if (line.startsWith("restart")) {
                    continue outer;
                }
                if (line.startsWith("done")) {
                    finishStoring = true;
                    finishCard = true;
                    message = new AbandonedStationCardMessage(MessageType.OCCUPY_STATION, goodsMap);


                }

            }
        }
        return message;
    }


    public MeteorCardMessage calculateMeteorCardMessage(Client client, Character dimension, Character direction, int dice) {
        MeteorCardMessage message = new MeteorCardMessage(MessageType.MANAGE_METEOR);
        System.out.println("\nYou have to deal with a meteor: " +
                "\nA small meteor can be stopped by an well placed component or a shield pointing in that direction." +
                "\nA big meteor can be stopped only with a cannon pointing in that direction.");

        switch (direction) {
            case 'N':
                if (dice > 10 || dice < 4) {
                    System.out.println("\nmeteor dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_x = 5; coord_x <= 9; coord_x++) {// x fissa e la il meteorite va sul primo componente che incontra sulle y
                    ComponentCard tempComponent = client.getShipboard().getShipComponent(coord_x - 5, dice - 4);

                    if (tempComponent != null) {
                        if (dimension.equals('B') && !cannonDirectionMeteor(client.getShipboard(), 'N', dice - 4, message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension
                                .equals('S') && tempComponent.getConnectors()[0].isExposed() && !calculateShieldMeteor(client.getShipboard(),
                                'N', message)) { //controllo il connenttore esposto o se ce il cannone)
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension.equals('S') && !tempComponent.getConnectors()[0].isExposed()) break;
                        else break;
                    }
                }
                break;
            case 'S':
                if (dice > 10 || dice < 4) {
                    System.out.println("\nmeteor dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_x = 9; coord_x >= 5; coord_x--) { // x fissa e la il meteorite va sul primo componente che incontra sulle y
                    ComponentCard tempComponent = client.getShipboard().getShipComponent(coord_x - 5, dice - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && !cannonDirectionMeteor(client.getShipboard(), 'S', dice - 4, message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension
                                .equals('S') && tempComponent.getConnectors()[2].isExposed() && !calculateShieldMeteor(client.getShipboard(),
                                'S', message)) { //controllo il connenttore esposto o se ce il cannone)
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension.equals('S') && !tempComponent.getConnectors()[0].isExposed()) break;
                        else break;
                    }
                }
                break;
            case 'E':
                if (dice > 9 || dice < 5) {
                    System.out.println("\nmeteor dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_y = 10; coord_y >= 4; coord_y--) { // x fissa e la il meteorite va sul primo componente che incontra sulle y
                    ComponentCard tempComponent = client.getShipboard().getShipComponent(dice - 5, coord_y - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && !cannonDirectionMeteor(client.getShipboard(), 'E', dice - 5, message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);
                            return message;
                        } else if (dimension
                                .equals('S') && tempComponent.getConnectors()[1].isExposed() && !calculateShieldMeteor(client.getShipboard(),
                                'E', message)) { //controllo il connenttore esposto o se ce il cannone)
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);
                            return message;
                        } else if (dimension.equals('S') && !tempComponent.getConnectors()[0].isExposed()) break;
                        else break;
                    }
                }
                break;
            case 'W':
                if (dice > 9 || dice < 5) {
                    System.out.println("\nmeteor dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_y = 4; coord_y <= 10; coord_y++) { // x fissa e la il meteorite va sul primo componente che incontra sulle y
                    ComponentCard tempComponent = client.getShipboard().getShipComponent(dice - 5, coord_y - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && !cannonDirectionMeteor(client.getShipboard(), 'W', dice - 5, message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);
                            return message;
                        } else if (dimension
                                .equals('S') && tempComponent.getConnectors()[3].isExposed() && !calculateShieldMeteor(client.getShipboard(),
                                'W', message)) { //controllo il connenttore esposto o se ce il cannone)
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);
                            return message;
                        } else if (dimension.equals('S') && !tempComponent.getConnectors()[0].isExposed()) break;
                        else break;
                    }
                }
                break;
        }
        System.out.println("\nmeteor defended");
        message.setCanDefend(true);
        return message;
    }

    public OpenSpaceCardMessage calculateOpenSpaceMessage(Client client) {
        OpenSpaceCardMessage message = new OpenSpaceCardMessage(MessageType.APPLY_OPEN_SPACE);
        int enginePower = 0;
        Engine engine;
        Cabin cabin;
        boolean foundBC = false;
        Shipboard shipboard = client.getShipboard();
        OperationVisitor applyOperation = new ApplyOperation();
        int batteries = shipboard.acceptCalculateBattery(applyOperation);
        outer:
        for (Integer[] tile : shipboard.getEngineList()) {
            engine = (Engine) shipboard.getShipComponent(tile[0], tile[1]);
            if (!engine.isDouble() || batteries == 0) {
                enginePower++;
            } else if (engine.isDouble() && batteries > 0) {
                inner:
                while (true) {
                    System.out.println("\nDo you want to activate the double engine at " + (tile[0] + 5) + " " + (tile[1] + 4) + " ? <yes> <no>");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipboard);

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipboard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipboard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    enginePower = enginePower + 2;
                                    foundBC = true;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available here");
                                    continue inner;
                                }
                            }
                        }
                        if (!foundBC) System.out.println("Is not a valid battery compartment");
                        foundBC = false;
                    } else if (input.equals("no")) {
                        enginePower++;
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }

        }
        for (Integer[] tile : shipboard.getCabinList()) {
            cabin = (Cabin) shipboard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.BROWNALIEN) {
                enginePower++;
            }
        }

        message.setEnginePower(enginePower);
        return message;
    }

    public AbandonedShipCardMessage calculateAbandonedShipMessage(Client client) {
        AbandonedShipCardMessage abandonedShipCardMessage = new AbandonedShipCardMessage(MessageType.OCCUPY_SHIP);
        AbandonedShip abandonedShip = (AbandonedShip) client.getClientGameController().getCurrentAdventureCard();
        int crewNeeded = abandonedShip.getCrewLost();

        outer:
        while (true) {
            int removedCrew = 0;
            System.out.println("Choose where you want to remove the crew or type 'restart' to restart the process");
            inner:
            for (Integer[] cabinCoords : client.getShipboard().getCabinList()) {
                Cabin cabin = (Cabin) client.getShipboard().getShipComponent(cabinCoords[0], cabinCoords[1]);
                if (cabin.getOccupantNumber() > 0) {
                    int removedCrewHere = 0;
                    while (removedCrew < crewNeeded) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());
                        System.out.println("do you want to remove a " + cabin.getOccupantType() + " from the cabin " + (cabinCoords[0] + 5) + " " + (cabinCoords[1] + 4) + " ? <yes> <no>");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            removedCrew++;
                            removedCrewHere++;
                            abandonedShipCardMessage.setRemovedCrewFrom(cabinCoords[0], cabinCoords[1]);
                            if (cabin.getOccupantNumber() > removedCrewHere) continue;
                            continue inner;
                        } else if (reply.equals("no")) {
                            continue inner;
                        } else if (reply.equals("restart")) {
                            continue outer;
                        } else System.out.println("Invalid syntax");
                    }
                }
            }
            if (removedCrew != crewNeeded) {
                System.out.println("you removed " + removedCrew + " crew, but the needed crew is " + crewNeeded + " \nrestating");
            } else break;
        }

        return abandonedShipCardMessage;
    }


    public SlaversWonCardMessage calculateSlaversWonMessage(Client client) {
        OperationVisitor applyOperation = new ApplyOperation();
        SlaversWonCardMessage message = new SlaversWonCardMessage(MessageType.SLAVERS_WON);
        Shipboard shipBoard = client.getShipboard();
        Cabin cabin;
        int rawPower = shipBoard.getCannonList().size();
        int plusPower = 0;

        for (Integer[] tile : shipBoard.getCabinList()) {
            cabin = (Cabin) shipBoard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.PURPLEALIEN) {
                plusPower++;
            }
        }

        outer:
        for (Integer[] coords : shipBoard.getCannonList()) {
            Cannon tempComponent = (Cannon) shipBoard.getShipComponent(coords[0], coords[1]);
            int batteries = shipBoard.acceptCalculateBattery(applyOperation);
            if (tempComponent.isDouble()) {
                if (batteries < 1) break;
                inner:
                while (true) {
                    Slavers slavers = (Slavers) client.getClientGameController().getCurrentAdventureCard();
                    int currentPower = rawPower + plusPower;
                    System.out.println("Do you want to activate the double cannon? <yes> <no> \n" +
                            "Smugglers' power is: " + slavers.getSlaversCannonPower() + "\n" +
                            "your raw power is: " + rawPower + "\n" +
                            "your plus power is: " + plusPower + "\n" +
                            "your current power is: " + currentPower);
                    String reply = scanner.nextLine().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    plusPower++;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available here");
                                    continue inner;
                                }
                            }
                        }
                        System.out.println("Is not a valid battery compartment");

                    } else if (reply.equals("no")) {
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }
        }
        message.setCannonPower(rawPower + plusPower);
        return message;
    }

    public SlaversLostCardMessage calculateSlaversLostMessage(Client client) {
        SlaversLostCardMessage slaversLostCardMessage = new SlaversLostCardMessage(MessageType.SLAVERS_LOST);
        Slavers slavers = (Slavers) client.getClientGameController().getCurrentAdventureCard();
        int crewNeeded = slavers.getCrewLost();


        outer:
        while (true) {
            int removedCrew = 0;
            System.out.println("you lost against slavers, choose where you want to remove the crew or type 'restart' to restart the process");
            inner:
            for (Integer[] cabinCoords : client.getShipboard().getCabinList()) {
                Cabin cabin = (Cabin) client.getShipboard().getShipComponent(cabinCoords[0], cabinCoords[1]);
                if (cabin.getOccupantNumber() > 0) {
                    int removedCrewHere = 0;
                    while (removedCrew < crewNeeded) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());
                        System.out.println("do you want to remove a " + cabin.getOccupantType() + " from the cabin " + (cabinCoords[0] + 5) + " " + (cabinCoords[1] + 4) + " ? <yes> <no>");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            removedCrew++;
                            removedCrewHere++;
                            slaversLostCardMessage.setRemovedCrewFrom(cabinCoords[0], cabinCoords[1]);
                            if (cabin.getOccupantNumber() > removedCrewHere) continue;
                            continue inner;
                        } else if (reply.equals("no")) {
                            continue inner;
                        } else if (reply.equals("restart")) {
                            continue outer;
                        } else System.out.println("Invalid syntax");
                    }
                }
            }
            if (removedCrew != crewNeeded) {
                System.out.println("you removed " + removedCrew + " crew, but the needed crew is " + crewNeeded + " \nrestating");
            } else break;
        }
        return slaversLostCardMessage;

    }

    public SmugglersWonCardMessage calculateSmugglersWonMessage(Client client) {
        OperationVisitor applyOperation = new ApplyOperation();
        SmugglersWonCardMessage message = new SmugglersWonCardMessage(MessageType.SMUGGLERS_WON);
        Shipboard shipBoard = client.getShipboard();
        Cabin cabin;
        int rawPower = shipBoard.getCannonList().size();
        int plusPower = 0;

        for (Integer[] tile : shipBoard.getCabinList()) {
            cabin = (Cabin) shipBoard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.PURPLEALIEN) {
                plusPower++;
            }
        }

        outer:
        for (Integer[] coords : shipBoard.getCannonList()) {
            Cannon tempComponent = (Cannon) shipBoard.getShipComponent(coords[0], coords[1]);
            int batteries = shipBoard.acceptCalculateBattery(applyOperation);
            if (tempComponent.isDouble()) {
                if (batteries < 1) break;
                inner:
                while (true) {
                    Smugglers smugglers = (Smugglers) client.getClientGameController().getCurrentAdventureCard();
                    int currentPower = rawPower + plusPower;
                    System.out.println("Do you want to activate the double cannon? \n" +
                            "Slavers' power is: " + smugglers.getCannonsPower() + "\n" +
                            "your raw power is: " + rawPower + "\n" +
                            "your plus power is: " + plusPower + "\n" +
                            "your current power is: " + currentPower);
                    String reply = scanner.nextLine().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipBoard);

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    plusPower++;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available here");
                                    continue inner;
                                }
                            }
                        }
                        System.out.println("Is not a valid battery compartment");

                    } else if (reply.equals("no")) {
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }
        }
        message.setCannonPower(rawPower + plusPower);
        return message;
    }

    public SmugglersLostCardMessage calculateSmugglersLostMessage(Client client) {
        SmugglersLostCardMessage smugglersLostCardMessage = new SmugglersLostCardMessage(MessageType.SMUGGLERS_LOST);
        OperationVisitor applyOperation = new ApplyOperation();
        Smugglers smugglers = (Smugglers) client.getClientGameController().getCurrentAdventureCard();
        int goodsNeeded = smugglers.getGoodsLost();
        int removedGoods = 0;

        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.RED) {
                    removedGoods++;
                    smugglersLostCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return smugglersLostCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.YELLOW) {
                    removedGoods++;
                    smugglersLostCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return smugglersLostCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.GREEN) {
                    removedGoods++;
                    smugglersLostCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return smugglersLostCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.BLUE) {
                    removedGoods++;
                    smugglersLostCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return smugglersLostCardMessage;
                }
            }
        }


        outer:
        while (true) {
            int batteriesNeeded = goodsNeeded - removedGoods;
            int removedBatteries = 0;
            if (client.getShipboard().getBatteryList().isEmpty()) {
                return smugglersLostCardMessage;
            }
            System.out.println("you don't have enough goods, choose where you want to remove the batteries or type 'restart' to restart the process");
            inner:
            for (Integer[] batteryCoords : client.getShipboard().getBatteryList()) {
                BatteryCompartment batteryCompartment = (BatteryCompartment) client.getShipboard().getShipComponent(batteryCoords[0], batteryCoords[1]);
                if (batteryCompartment.getBatteriesAvailable() > 0) {
                    int removedBatteriesHere = 0;
                    while (removedBatteries < batteriesNeeded) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());
                        System.out.println("do you want to remove a battery from the cabin " + (batteryCoords[0] + 5) + " " + (batteryCoords[1] + 4) + " ? <yes> <no>");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            removedBatteries++;
                            removedBatteriesHere++;
                            smugglersLostCardMessage.setRemovedBatteryFrom(batteryCoords[0], batteryCoords[1]);
                            if (removedBatteries == batteriesNeeded) {
                                return smugglersLostCardMessage;
                            }
                            if (batteryCompartment.getBatteriesAvailable() > removedBatteriesHere) continue;
                            continue inner;
                        } else if (reply.equals("no")) {
                            continue inner;
                        } else if (reply.equals("restart")) {
                            continue outer;
                        } else System.out.println("Invalid syntax");
                    }
                }
            }
            if (removedBatteries != batteriesNeeded) {
                System.out.println("you removed " + removedBatteries + " batteries, but you have to remove " + batteriesNeeded + " \nrestating");
            } else break;
        }
        return smugglersLostCardMessage;
    }


    public SmugglersCardMessage calculateSmugglersMessage(Client client) {
        SmugglersCardMessage message = null;
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<ArrayList<Integer>, ArrayList<Goods>>();
        Smugglers smugglers = (Smugglers) client.getClientGameController().getCurrentAdventureCard();
        boolean finishStoring = false;
        boolean finishCard = false;
        boolean existingStoragePassed = false;
        Map<ArrayList<Integer>, Integer> usedStorage = new HashMap<>();


        System.out.println(
                "In this phase you can manage your goods. It means you can reorganize your goods as you prefer. You can also get rid of them you want." +
                        "\nType 'storage' followed by the coords x y and the goods you want to store there. An example is: 'storage 4 6 yellow yellow green' " +
                        "\n Repeat the process for every storage. Remember You can leave some resources on the station by typing 'done', and all the remaining resources will be left on the station. " +
                        "\nYou can type help to see the all the available commands");


        outer:
        while (!finishCard) {

            ArrayList<Goods> totalGoodsList = new ArrayList<>();

            for (Integer[] storage : client.getShipboard().getStorageList()) {
                Hold hold = (Hold) client.getShipboard().getShipComponent(storage[0], storage[1]);
                List<Goods> goods = hold.getLoad();
                for (Goods good : goods) {
                    totalGoodsList.add(good);
                }
            }
            for (Goods good : smugglers.getGoodsList()) {
                totalGoodsList.add(good);
            }


            inner:
            while (!finishStoring) {
                System.out.println("\nYou have to manage these goods: ");


                for (Goods good : totalGoodsList) {
                    System.out.println(good);
                }

                System.out.print("Enter command: ");
                String line = scanner.nextLine().trim();

                if (line.startsWith("help")) {
                    System.out.println("Type: storage x y and the colors to store them" +
                            "\nType: ship to see your ship board" +
                            "\nType: smugglers to see the card" +
                            "\nType: restart to restart the process" +
                            "\nType: done to finish the process");
                    continue;
                }

                if (line.startsWith("storage")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length < 4) {
                        System.out.println("Not enough arguments. Use format: storage x y goods...");
                        continue;
                    }

                    try {
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        x = x - 5;
                        y = y - 4;

                        int goodsCount = parts.length - 3;
                        if (goodsCount < 1 || goodsCount > 3) {
                            System.out.println("You must provide between 1 and 3 goods.");
                            continue;
                        }

                        ArrayList<Goods> selectedGoodsList = new ArrayList<>();
                        boolean specialCargo = false;
                        for (int i = 3; i < parts.length; i++) {
                            Goods selectedGood;

                            try {
                                selectedGood = Goods.valueOf(parts[i].toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid goods type: " + parts[i] + ". Check spelling.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            if (!totalGoodsList.contains(selectedGood)) {
                                System.out.println("The good " + selectedGood + " is not available in your list.");
                                selectedGoodsList.clear();
                                continue inner;
                            }

                            selectedGoodsList.add(selectedGood);

                            if (selectedGood == Goods.RED) {
                                specialCargo = true;
                            }
                        }

                        for (Integer[] storage : client.getShipboard().getStorageList()) {
                            if (storage[0] == x && storage[1] == y) {
                                existingStoragePassed = true;
                                break;
                            }
                        }

                        if (!existingStoragePassed) {
                            System.out.println("it doesn't seem to be an existing storage with these coordinates");
                            continue;
                        }

                        Hold hold = (Hold) client.getShipboard().getShipComponent(x, y);
                        if (goodsCount > hold.getCapacity()) {
                            System.out.println("This storage capacity is insufficient");
                            continue;
                        }

                        ArrayList<Integer> coordsForCheck = new ArrayList<>();
                        coordsForCheck.add(x);
                        coordsForCheck.add(y);
                        int alreadyUsed = usedStorage.getOrDefault(coordsForCheck, 0);
                        if (alreadyUsed + goodsCount > hold.getCapacity()) {
                            System.out.println("You cannot add this amount of goods here. Exceeds remaining capacity of: " + (hold.getCapacity() - alreadyUsed));
                            continue;
                        }

                        if (specialCargo && !hold.isSpecial()) {
                            System.out.println("This storage is not special");
                            continue;
                        }

                        ArrayList<Integer> coords = new ArrayList<>();
                        coords.add(x);
                        coords.add(y);
                        if (goodsMap.containsKey(coords)) {
                            goodsMap.get(coords).addAll(selectedGoodsList); //already existing at these coords
                        } else {
                            goodsMap.put(coords, new ArrayList<>(selectedGoodsList)); //create new coords key
                        }
                        for (Goods good : selectedGoodsList) {
                            totalGoodsList.remove(good);
                        }
                        usedStorage.merge(coordsForCheck, goodsCount, Integer::sum);
                        for (Goods good : selectedGoodsList) {
                            System.out.println("Stored good: " + good + " in " + (x + 5) + " " + (y + 4));
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                }
                if (line.startsWith("ship")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printShipboard(client.getShipboard());
                    continue;
                }
                if (line.startsWith("smugglers")) {
                    ModelElementPrinter printer = new ModelElementPrinter();
                    printer.printCard(client.getClientGameController().getCurrentAdventureCard());
                    continue;
                }
                if (line.startsWith("restart")) {
                    continue outer;
                }
                if (line.startsWith("done")) {
                    finishStoring = true;
                    finishCard = true;
                    message = new SmugglersCardMessage(MessageType.APPLY_SMUGGLERS, goodsMap);


                }

            }
        }
        return message;
    }

    public PiratesWonCardMessage calculatePiratesWonMessage(Client client) {
        OperationVisitor applyOperation = new ApplyOperation();
        PiratesWonCardMessage message = new PiratesWonCardMessage(MessageType.PIRATES_WON);
        Shipboard shipBoard = client.getShipboard();
        Cabin cabin;
        int rawPower = shipBoard.getCannonList().size();
        int plusPower = 0;

        for (Integer[] tile : shipBoard.getCabinList()) {
            cabin = (Cabin) shipBoard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.PURPLEALIEN) {
                plusPower++;
            }
        }

        outer:
        for (Integer[] coords : shipBoard.getCannonList()) {
            Cannon tempComponent = (Cannon) shipBoard.getShipComponent(coords[0], coords[1]);
            int batteries = shipBoard.acceptCalculateBattery(applyOperation);
            if (tempComponent.isDouble()) {
                if (batteries < 1) break;
                inner:
                while (true) {
                    Pirates pirates = (Pirates) client.getClientGameController().getCurrentAdventureCard();
                    int currentPower = rawPower + plusPower;
                    System.out.println("Do you want to activate the double cannon? <yes> <no>\n" +
                            "pirates' power is: " + pirates.getPirateCannonsPower() + "\n" +
                            "your raw power is: " + rawPower + "\n" +
                            "your plus power is: " + plusPower + "\n" +
                            "your current power is: " + currentPower);
                    String reply = scanner.nextLine().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    plusPower++;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available here");
                                    continue inner;
                                }
                            }
                        }
                        System.out.println("Is not a valid battery compartment");

                    } else if (reply.equals("no")) {
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }

        }

        message.setCannonPower(rawPower + plusPower);
        return message;
    }

    public BlastCardMessage calculateBlastCardMessage(Client client, Character dimension, Character direction, int dice) {
        BlastCardMessage message = new BlastCardMessage(MessageType.MANAGE_BLAST);
        Shipboard shipBoard = client.getShipboard();
        System.out.println("\nYou have to deal with a blast: " +
                "\ndimension: " + dimension +
                "\ndirection: " + direction +
                "\nat: " + dice +
                "\nA small blast can be stopped by a shield pointing in that direction." +
                "\nA big blast can't be stopped.");

        switch (direction) {
            case 'N':
                if (dice > 10 || dice < 4) {
                    System.out.print("\nblast dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_x = 5; coord_x <= 9; coord_x++) {
                    ComponentCard tempComponent = shipBoard.getShipComponent(coord_x - 5, dice - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B')) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension.equals('S') && !calculateShieldBlast(shipBoard, 'N', message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        }
                        break;
                    }
                }
                break;
            case 'S':
                if (dice > 10 || dice < 4) {
                    System.out.print("\nblast dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_x = 9; coord_x >= 5; coord_x--) {
                    ComponentCard tempComponent = shipBoard.getShipComponent(coord_x - 5, dice - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && tempComponent != null) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        } else if (dimension.equals('S') && tempComponent != null && !calculateShieldBlast(shipBoard, 'S', message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(coord_x - 5, dice - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + coord_x + "," + dice);
                            return message;
                        }
                        break;
                    }
                }
                break;
            case 'W':
                if (dice > 9 || dice < 5) {
                    System.out.print("\nblast dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_y = 4; coord_y <= 10; coord_y++) {
                    ComponentCard tempComponent = shipBoard.getShipComponent(dice - 5, coord_y - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && tempComponent != null) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);
                            return message;
                        } else if (dimension.equals('S') && tempComponent != null && !calculateShieldBlast(shipBoard, 'W', message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);

                            return message;
                        }
                        break;
                    }
                }
                break;
            case 'E':
                if (dice > 9 || dice < 5) {
                    System.out.print("\nblast dodged\n");
                    message.setCanDefend(true);
                    return message;
                }
                for (int coord_y = 10; coord_y >= 4; coord_y--) {
                    ComponentCard tempComponent = shipBoard.getShipComponent(dice - 5, coord_y - 4);
                    if (tempComponent != null) {
                        if (dimension.equals('B') && tempComponent != null) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);

                            return message;
                        } else if (dimension.equals('S') && tempComponent != null && !calculateShieldBlast(shipBoard, 'E', message)) {
                            message.setCanDefend(false);
                            message.setRemovedComponentFrom(dice - 5, coord_y - 4);
                            System.out.println("\ndestroyed component: " + tempComponent.getShortName() + " at " + dice + "," + coord_y);

                            return message;
                        }
                        break;
                    }
                }
                break;
        }
        System.out.print("\nblast defended\n");
        message.setCanDefend(true);
        return message;
    }

    public WarzoneControlCardMessage calculateWarzoneCannonMessage(Client client) {
        OperationVisitor applyOperation = new ApplyOperation();
        WarzoneControlCardMessage message = new WarzoneControlCardMessage(MessageType.WARZONE_CONTROL);
        Shipboard shipBoard = client.getShipboard();
        Cabin cabin;
        int rawPower = shipBoard.getCannonList().size();
        int plusPower = 0;

        for (Integer[] tile : shipBoard.getCabinList()) {
            cabin = (Cabin) shipBoard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.PURPLEALIEN) {
                plusPower++;
            }
        }

        outer:
        for (Integer[] coords : shipBoard.getCannonList()) {
            Cannon tempComponent = (Cannon) shipBoard.getShipComponent(coords[0], coords[1]);
            int batteries = shipBoard.acceptCalculateBattery(applyOperation);
            if (tempComponent.isDouble()) {
                if (batteries < 1) break;
                inner:
                while (true) {
                    int currentPower = rawPower + plusPower;
                    System.out.println("Do you want to activate the double cannon? <yes> <no>\n" +
                            "your current power is: " + currentPower);
                    String reply = scanner.nextLine().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipBoard);

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    plusPower++;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available");
                                    continue inner;
                                }
                            }
                        }
                        System.out.println("Is not a valid battery compartment");
                    } else if (reply.equals("no")) {
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }
        }
        message.setParameter(rawPower + plusPower);
        return message;
    }

    public WarzoneControlCardMessage calculateWarzoneEngineMessage(Client client) {
        WarzoneControlCardMessage message = new WarzoneControlCardMessage(MessageType.WARZONE_CONTROL);
        int enginePower = 0;
        Engine engine;
        Cabin cabin;
        boolean foundBC = false;
        Shipboard shipboard = client.getShipboard();
        OperationVisitor applyOperation = new ApplyOperation();
        int batteries = shipboard.acceptCalculateBattery(applyOperation);
        outer:
        for (Integer[] tile : shipboard.getEngineList()) {
            engine = (Engine) shipboard.getShipComponent(tile[0], tile[1]);
            if (!engine.isDouble() || batteries == 0) {
                enginePower++;
            } else if (engine.isDouble() && batteries > 0) {
                inner:
                while (true) {
                    System.out.println("Do you want to activate the double engine at " + (tile[0] + 5) + " " + (tile[1] + 4) + " ? <yes> <no>");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipboard);

                        System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }

                        for (Integer[] battery : shipboard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipboard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    enginePower = enginePower + 2;
                                    foundBC = true;
                                    continue outer;
                                } else {
                                    System.out.println("Batteries not available here");
                                    continue inner;
                                }
                            }
                        }
                        if (!foundBC) System.out.println("Is not a valid battery compartment");
                        foundBC = false;
                    } else if (input.equals("no")) {
                        enginePower++;
                        continue outer;
                    } else {
                        System.out.println("Invalid syntax");
                    }
                }
            }

        }
        for (Integer[] tile : shipboard.getCabinList()) {
            cabin = (Cabin) shipboard.getShipComponent(tile[0], tile[1]);
            if (cabin.getOccupantType() == OccupantType.BROWNALIEN) {
                enginePower++;
            }
        }

        message.setParameter(enginePower);
        return message;
    }

    public WarzoneCrewPenaltyCardMessage calculateWarzoneCrewPenaltyMessage(Client client) {
        WarzoneCrewPenaltyCardMessage message = new WarzoneCrewPenaltyCardMessage(MessageType.WARZONE_CREW_PENALTY);
        Warzone warzone = (Warzone) client.getClientGameController().getCurrentAdventureCard();
        int crewNeeded = warzone.getCrewLost();


        outer:
        while (true) {
            int removedCrew = 0;
            System.out.println("you lost, choose where you want to remove the crew or type 'restart' to restart the process");
            inner:
            for (Integer[] cabinCoords : client.getShipboard().getCabinList()) {
                Cabin cabin = (Cabin) client.getShipboard().getShipComponent(cabinCoords[0], cabinCoords[1]);
                if (cabin.getOccupantNumber() > 0) {
                    int removedCrewHere = 0;
                    while (removedCrew < crewNeeded) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());
                        System.out.println("do you want to remove a " + cabin.getOccupantType() + " from the cabin " + (cabinCoords[0] + 5) + " " + (cabinCoords[1] + 4) + " ?");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            removedCrew++;
                            removedCrewHere++;
                            message.setRemovedCrewFrom(cabinCoords[0], cabinCoords[1]);
                            if (cabin.getOccupantNumber() > removedCrewHere) continue;
                            continue inner;
                        } else if (reply.equals("no")) {
                            continue inner;
                        } else if (reply.equals("restart")) {
                            continue outer;
                        } else System.out.println("Invalid syntax");
                    }
                }
            }
            if (removedCrew != crewNeeded) {
                System.out.println("you removed " + removedCrew + " crew, but the needed crew is " + crewNeeded + " \nrestating");
            } else break;
        }
        return message;
    }

    public WarzoneGoodsPenaltyCardMessage calculateWarzoneGoodsPenaltyMessage(Client client) {
        WarzoneGoodsPenaltyCardMessage warzoneGoodsPenaltyCardMessage = new WarzoneGoodsPenaltyCardMessage(MessageType.WARZONE_GOODS_PENALTY);
        Warzone warzone = (Warzone) client.getClientGameController().getCurrentAdventureCard();
        int goodsNeeded = warzone.getGoodsLost();
        int removedGoods = 0;

        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.RED) {
                    removedGoods++;
                    warzoneGoodsPenaltyCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return warzoneGoodsPenaltyCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.YELLOW) {
                    removedGoods++;
                    warzoneGoodsPenaltyCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return warzoneGoodsPenaltyCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.GREEN) {
                    removedGoods++;
                    warzoneGoodsPenaltyCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return warzoneGoodsPenaltyCardMessage;
                }
            }
        }
        for (Integer[] holdCoords : client.getShipboard().getStorageList()) {
            Hold hold = (Hold) client.getShipboard().getShipComponent(holdCoords[0], holdCoords[1]);
            for (Goods good : hold.getLoad()) {
                if (good == Goods.BLUE) {
                    removedGoods++;
                    warzoneGoodsPenaltyCardMessage.setRemovedGoodsFrom(holdCoords[0], holdCoords[1], good);
                    System.out.println("Removed good: " + good + "from hold: " + (holdCoords[0] + 5) + ", " + (holdCoords[1] + 4));
                    if (removedGoods == goodsNeeded) return warzoneGoodsPenaltyCardMessage;
                }
            }
        }


        outer:
        while (true) {
            int batteriesNeeded = goodsNeeded - removedGoods;
            int removedBatteries = 0;
            OperationVisitor applyoperation = new ApplyOperation();
            if (client.getShipboard().acceptCalculateBattery(applyoperation) < batteriesNeeded) {
                System.out.println("you don't have enough goods, and not even batteries batteries.\n " +
                        "Nothing is going to happen to you.\n ");
                return warzoneGoodsPenaltyCardMessage;
            }

            System.out.println("you don't have enough goods, choose where you want to remove the batteries.\n " +
                    "If you don't have enough batteries nothing is going to happen to you.\n " +
                    "Type 'restart' to restart the process.\n");
            inner:
            for (Integer[] batteryCoords : client.getShipboard().getBatteryList()) {
                BatteryCompartment batteryCompartment = (BatteryCompartment) client.getShipboard().getShipComponent(batteryCoords[0], batteryCoords[1]);
                if (batteryCompartment.getBatteriesAvailable() > 0) {
                    int removedBatteriesHere = 0;
                    while (removedBatteries < batteriesNeeded) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(client.getShipboard());
                        System.out.println("do you want to remove a battery from the cabin " + (batteryCoords[0] + 5) + " " + (batteryCoords[1] + 4) + " ? <yes> <no>");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            removedBatteries++;
                            removedBatteriesHere++;
                            warzoneGoodsPenaltyCardMessage.setRemovedBatteryFrom(batteryCoords[0], batteryCoords[1]);
                            if (removedBatteries == batteriesNeeded) {
                                return warzoneGoodsPenaltyCardMessage;
                            }
                            if (batteryCompartment.getBatteriesAvailable() > removedBatteriesHere) continue;
                            continue inner;
                        } else if (reply.equals("no")) {
                            continue inner;
                        } else if (reply.equals("restart")) {
                            continue outer;
                        } else System.out.println("Invalid syntax");
                    }
                }
            }
            if (removedBatteries != batteriesNeeded) {
                System.out.println("you removed " + removedBatteries + " batteries, but you have to remove " + batteriesNeeded + " \nrestating");
            } else break;
        }
        return warzoneGoodsPenaltyCardMessage;
    }


    private boolean cannonDirectionMeteor(Shipboard shipBoard, Character direction, int diceCoordDirection, MeteorCardMessage message) {
        int intDirection = -1;
        OperationVisitor applyOperation = new ApplyOperation();

        int batteries = shipBoard.acceptCalculateBattery(applyOperation);
        if (batteries < 1) return false;

        switch (direction) {
            case 'N':
                intDirection = 0;
                break;
            case 'E':
                intDirection = 1;
                break;
            case 'S':
                intDirection = 2;
                break;
            case 'W':
                intDirection = 3;
                break;
            default:
                return false;
        }

        for (Integer[] coords : shipBoard.getCannonList()) {
            Cannon tempComponent = (Cannon) shipBoard.getShipComponent(coords[0], coords[1]);

            boolean aligned = (intDirection == 0 || intDirection == 2)
                    ? coords[1] == diceCoordDirection //same vertical direction
                    : coords[0] == diceCoordDirection; //same orizontal direction

            if (tempComponent.getFacingDirectionIndex() == intDirection && aligned) {
                if (tempComponent.isDouble()) {

                    outer:
                    while (true) {
                        System.out.println("\nDo you want to activate the double cannon to destroy the meteor? <yes> <no>");
                        String reply = scanner.nextLine().trim().toLowerCase();
                        if (reply.equals("yes")) {
                            ModelElementPrinter printer = new ModelElementPrinter();
                            printer.printShipboard(shipBoard);


                            System.out.println("From which battery compartment do you want to consume a battery? Type x y");
                            String line = scanner.nextLine();
                            String[] parts = line.trim().split("\\s+");
                            int x, y;
                            try {
                                x = Integer.parseInt(parts[0]);
                                y = Integer.parseInt(parts[1]);
                                x = x - 5;
                                y = y - 4;
                            } catch (NumberFormatException e) {
                                System.out.println("Coordinates must be numbers. Try again.");
                                continue;
                            }

                            for (Integer[] battery : shipBoard.getBatteryList()) {
                                if (battery[0] == x && battery[1] == y) {
                                    BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                    if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                        message.setRemovedBatteryFrom(x, y);
                                        return true;
                                    } else {
                                        System.out.println("Batteries not available");
                                        continue outer;
                                    }
                                } else {
                                    System.out.println("is not a battery compartment");
                                    continue outer;
                                }
                            }

                        } else if (reply.equals("no")) {
                            return false;
                        } else {
                            System.out.println("Invalid syntax");
                        }
                    }
                }
            }
        }
        return false;
    }


    private boolean calculateShieldMeteor(Shipboard shipBoard, Character direction, MeteorCardMessage message) {
        OperationVisitor applyOperation = new ApplyOperation();

        int batteries = shipBoard.acceptCalculateBattery(applyOperation);
        if (batteries < 1) return false;

        for (Integer[] coords : shipBoard.getShieldList()) {
            ShieldGenerator tempComponent = (ShieldGenerator) shipBoard.getShipComponent(coords[0], coords[1]);
            if (tempComponent.getCoveredShipSides()[0] == direction || tempComponent.getCoveredShipSides()[1] == direction) {
                outer:
                while (true) {
                    System.out.println("\ndo you want to activate the shield to stop the meteor? <yes> <no>");
                    String reply = scanner.nextLine().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipBoard);
                        System.out.println("from which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }
                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x - 5 && battery[1] == y - 4) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x - 5, y - 4);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x - 5, y - 4);
                                    return true;
                                } else {
                                    System.out.println("batteries not available");
                                    continue outer;
                                }
                            } else {
                                System.out.println("is not a battery compartment");
                                continue outer;
                            }
                        }


                    } else if (reply.equals("no")) {
                        return false;
                    } else System.out.println("invalid syntax");
                }
            }
        }

        return false;
    }

    private boolean calculateShieldBlast(Shipboard shipBoard, Character direction, BlastCardMessage message) {
        OperationVisitor applyOperation = new ApplyOperation();

        int batteries = shipBoard.acceptCalculateBattery(applyOperation);
        if (batteries < 1) return false;

        for (Integer[] coords : shipBoard.getShieldList()) {
            ShieldGenerator tempComponent = (ShieldGenerator) shipBoard.getShipComponent(coords[0], coords[1]);
            if (tempComponent.getCoveredShipSides()[0] == direction || tempComponent.getCoveredShipSides()[1] == direction) {
                outer:
                while (true) {
                    System.out.println("\ndo you want to activate the shield to stop the blast? <yes> <no>");
                    String reply = scanner.nextLine();
                    if (reply.equals("yes")) {

                        ModelElementPrinter printer = new ModelElementPrinter();
                        printer.printShipboard(shipBoard);
                        System.out.println("from which battery compartment do you want to consume a battery? Type x y");
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        int x, y;
                        try {
                            x = Integer.parseInt(parts[0]);
                            y = Integer.parseInt(parts[1]);
                            x = x - 5;
                            y = y - 4;
                        } catch (NumberFormatException e) {
                            System.out.println("Coordinates must be numbers. Try again.");
                            continue;
                        }
                        for (Integer[] battery : shipBoard.getBatteryList()) {
                            if (battery[0] == x && battery[1] == y) {
                                BatteryCompartment batteryCompartment = (BatteryCompartment) shipBoard.getShipComponent(x, y);
                                if (batteryCompartment.getBatteriesAvailable() >= 1) {
                                    message.setRemovedBatteryFrom(x, y);
                                    return true;
                                } else {
                                    System.out.println("batteries not available");
                                    continue outer;
                                }
                            } else {
                                System.out.println("is not a battery compartment");
                                continue outer;
                            }
                        }


                    } else if (reply.equals("no")) {
                        return false;
                    } else {
                        System.out.println("invalid syntax");
                    }
                }
            }
        }

        return false;
    }
}


