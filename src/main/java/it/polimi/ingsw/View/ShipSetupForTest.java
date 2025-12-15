package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.OccupantType;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.*;
import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Messages.ClientToServer.AssemblyPhaseMessage.ComponentCardPlacedMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ShipSetupForTest {
    //Tiles
    ArrayList<ComponentCard> components;
    Shipboard shipboard;
    Flightboard flightboard;
    Player player;

    Cabin startingCabin = new Cabin("a", true, true, new Connector[]{
            new Connector(3, false),
            new Connector(3, false),
            new Connector(3, false),
            new Connector(3, false),
    }, 2, OccupantType.HUMAN, true);
    Cabin cabin_1 = new Cabin("1", true, true, new Connector[]{
            new Connector(0, false),
            new Connector(2, false),
            new Connector(0, false),
            new Connector(3, false),
    }, 2, OccupantType.HUMAN, false);
    Cabin cabin_2 = new Cabin("2", true, true, new Connector[]{
            new Connector(3, false),
            new Connector(0, false),
            new Connector(0, false),
            new Connector(2, false),
    }, 2, OccupantType.HUMAN, false);
    StructuralModule structuralModule_1 = new StructuralModule("3", true, true, new Connector[]{
            new Connector(3, true),
            new Connector(1, false),
            new Connector(2, false),
            new Connector(3, false),
    });
    StructuralModule structuralModule_2 = new StructuralModule("4", true, true, new Connector[]{
            new Connector(0, false),
            new Connector(3, false),
            new Connector(3, false),
            new Connector(2, false),
    });
    BatteryCompartment batteryCompartment_1 = new BatteryCompartment("5", true, true,
            new Connector[]{
                    new Connector(0, false),
                    new Connector(3, false),
                    new Connector(0, false),
                    new Connector(3, true),
            }, 2);
    BatteryCompartment batteryCompartment_2 = new BatteryCompartment("6", true, true,
            new Connector[]{
                    new Connector(0, false),
                    new Connector(3, false),
                    new Connector(0, false),
                    new Connector(0, false),
            }, 2);
    //    Cannon cannon_1 = new Cannon("a", true, true,
//            new Connector[]{
//                    new Connector(0, false),
//                    new Connector(0, false),
//                    new Connector(2, false),
//                    new Connector(1, true),
//            }, false, 1, 0);
    Cannon cannon_2 = new Cannon("7", true, true,
            new Connector[]{
                    new Connector(2, false),
                    new Connector(0, false),
                    new Connector(3, false),
                    new Connector(0, false),
            }, false, 1, 3);
    Cannon cannon_3 = new Cannon("8", true, true,
            new Connector[]{
                    new Connector(0, false),
                    new Connector(0, false),
                    new Connector(3, false),
                    new Connector(2, false),
            }, true, 1, 0);
    Hold hold_1 = new Hold("9", true, true, new Connector[]{
            new Connector(0, false),
            new Connector(0, false),
            new Connector(0, false),
            new Connector(1, false),
    }, 3, false);
    Hold hold_2 = new Hold("10", true, true, new Connector[]{
            new Connector(2, true),
            new Connector(1, false),
            new Connector(3, false),
            new Connector(0, false),
    }, 2, false);
    Hold hold_3 = new Hold("11", true, true, new Connector[]{
            new Connector(3, false),
            new Connector(0, false),
            new Connector(3, false),
            new Connector(0, false),
    }, 1, true);
    Engine engine_1 = new Engine("12", true, true, new Connector[]{
            new Connector(2, false),
            new Connector(0, false),
            new Connector(0, false),
            new Connector(0, false),
    }, false, 2);
    Engine engine_2 = new Engine("13", true, true, new Connector[]{
            new Connector(2, false),
            new Connector(0, false),
            new Connector(0, false),
            new Connector(0, false),
    }, true, 2);
    ShieldGenerator shieldGenerator_1 = new ShieldGenerator("14", true, true, new Connector[]{
            new Connector(3, false),
            new Connector(2, true),
            new Connector(0, false),
            new Connector(0, false),
    }, 2);


    public ShipSetupForTest() throws Exception {
        this.shipboard = new Shipboard(2);


        shipboard.addComponent(cabin_1, 2, 2);//cab1
        shipboard.addComponent(cabin_2, 3, 3);//cab2
        shipboard.addComponent(structuralModule_1, 1, 3);//tube1
        shipboard.addComponent(structuralModule_2, 2, 4);//tube2
        shipboard.addComponent(batteryCompartment_1, 1, 2);//bat1
        shipboard.addComponent(batteryCompartment_2, 3, 2);//bat2
        shipboard.addComponent(cannon_2, 3, 1);//can2
        shipboard.addComponent(cannon_3, 2, 5);//can3
        shipboard.addComponent(hold_1, 1, 4);//hol1
        shipboard.addComponent(hold_2, 2, 1);//hol2
        shipboard.addComponent(hold_3, 3, 4);//hol3
        shipboard.addComponent(engine_1, 4, 1);//eng1
        shipboard.addComponent(engine_2, 4, 4);//eng2
        shipboard.addComponent(shieldGenerator_1, 3, 5);//shi1
        shipboard.addComponent(startingCabin, 2, 3);//str

        flightboard = new Flightboard(2);
        player = new Player("carlo");
        flightboard.insertPlayer(player, 2);
        player.setShip(shipboard);
    }

    public Player getPlayer() {
        return player;
    }

    public Flightboard getFlightboard() {
        return flightboard;
    }

    public Shipboard getShipboard() {
        return shipboard;
    }

    public Cabin getStr() {
        return startingCabin;
    }

    public Cabin getCabin1() {
        return cabin_1;
    }

    public Cabin getCabin2() {
        return cabin_2;
    }

    public Hold getHold1() {
        return hold_1;
    }

    public Hold getHold3() {
        return hold_3;
    }

    public BatteryCompartment getBatteryCompartment_1() {
        return batteryCompartment_1;
    }

    public BatteryCompartment getBatteryCompartment_2() {
        return batteryCompartment_2;
    }

    public void addComponents(ArrayList<Component> components) {

    }

    public void setComponents(Client client) {

        try {
            insertComponent(components.get(0), 2, 2, client);//cab1
            insertComponent(components.get(1), 3, 3, client);//cab2
            insertComponent(components.get(2), 1, 3, client);//tube1
            insertComponent(components.get(3), 2, 4, client);//tube2
            insertComponent(components.get(4), 1, 2, client);//bat1
            insertComponent(components.get(5), 3, 2, client);//bat2
            insertComponent(components.get(6), 3, 1, client);//can2
            insertComponent(components.get(7), 2, 5, client);//can3
            insertComponent(components.get(8), 1, 4, client);//hol1
            insertComponent(components.get(9), 2, 1, client);//hol2
            insertComponent(components.get(10), 3, 4, client);//hol3
            insertComponent(components.get(11), 4, 1, client);//eng1
            insertComponent(components.get(12), 4, 4, client);//eng2
            insertComponent(components.get(13), 3, 5, client);//shi1

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }

    void insertComponent(ComponentCard componentCard, int x, int y, Client client) throws Exception {
        client.getShipboard().addComponent(componentCard, x, y);
        Message message = new ComponentCardPlacedMessage(MessageType.COMPONENT_CARD_PLACED, componentCard, x, y);
        Message response = client.sendMessageAndWaitResponse(message,
                new ArrayList<MessageType>(
                        Arrays.asList(MessageType.COMPONENT_CARD_PLACED_OK, MessageType.COMPONENT_CARD_PLACED_KO)
                ));
    }

}
