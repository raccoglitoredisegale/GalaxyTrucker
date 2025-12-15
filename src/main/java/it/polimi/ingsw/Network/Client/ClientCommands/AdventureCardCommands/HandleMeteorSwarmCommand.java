package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.MeteorSwarm;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.MeteorCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleMeteorSwarmCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();
        MeteorSwarm meteorSwarm = (MeteorSwarm) client.getClientGameController().getCurrentAdventureCard();

        if (command.equals("meteor") && !client.getClientGameController().getMTcontrolled()) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.THROW_DICE_METEOR), getCreateLobbyTypesMessage1());
                if (response.getType() == MessageType.THROW_DICE_OK) {
                    tui.print("dice thrown");
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("You can't throw the dice, you are not the leader or you have to wait for the others");
            }
        } else if (command.equals("meteor") && client.getClientGameController().getMTcontrolled())
            tui.print("you have to manage a meteor first");
        else if (command.startsWith("manage") && client.getClientGameController().getMTcontrolled()) {
            try {
                char dimension = client.getClientGameController().getDiceAndInfo().get(0).charAt(0);
                char direction = client.getClientGameController().getDiceAndInfo().get(1).charAt(0);
                int dice = Integer.parseInt(client.getClientGameController().getDiceAndInfo().get(2));

                MeteorCardMessage manageMeteorMessage = tui.handleMeteor(client, dimension, direction, dice);
                Message response = client.sendMessageAndWaitResponse(manageMeteorMessage, getCreateLobbyTypesMessage2());
                if (response.getType() == MessageType.MANAGE_METEOR_OK) {
                    tui.print("\nmanaged meteor");
                    client.getClientGameController().setMTcontrolled(false);

                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage3());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                } else if (response.getType() == MessageType.MANAGE_METEOR_KO) {
                    tui.print("error manage meteor");
                    client.getClientGameController().setMTcontrolled(false);
                }
            } catch (Exception e) {
                tui.print("Error occurred while handling meteor card");
                client.getClientGameController().setMTcontrolled(false);
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("manage") && !client.getClientGameController().getMTcontrolled())
            tui.print("there is no meteor to manage");
        else {
            tui.print("unknown command");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage1() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.THROW_DICE_OK, MessageType.THROW_DICE_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.MANAGE_METEOR_OK, MessageType.MANAGE_METEOR_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage3() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
