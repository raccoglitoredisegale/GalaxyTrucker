package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Pirates;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.BlastCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.PiratesWonCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandlePiratesCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();
        Pirates pirates = (Pirates) client.getClientGameController().getCurrentAdventureCard();
        if (command.equals("fight") && !pirates.isControlled()) {
            PiratesWonCardMessage piratesWonCardMessage = tui.handlePiratesControl(client);
            pirates.setControlled(true);
            if (piratesWonCardMessage.getCannonPower() >= pirates.getPirateCannonsPower()) {
                Message response = client.sendMessageAndWaitResponse(piratesWonCardMessage, getCreateLobbyTypesMessage1());
                if (response.getType() == MessageType.PIRATES_WON_OK) {
                    client.getClientGameController().setIsDefeated(true);
                    tui.print("you won against pirates, choose what to do");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage5());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                    tui.printHelpForCurrentState();
                } else tui.print("error handling pirates");

            } else {
                tui.setTUIState(TUIState.ADVENTURE_CARD_PIRATES_BLAST);
                tui.print("you lost against pirates, you have to handle some blasts");
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("fight") && client.getClientGameController().getMTcontrolled())
            tui.print("you already had your chance to fight, now you have to handle some blasts");
        else if (command.equals("blast") && !client.getClientGameController().getMTcontrolled()) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.THROW_DICE_BLAST), getCreateLobbyTypesMessage2());
                if (response.getType() == MessageType.THROW_DICE_OK) {
                    tui.print("dice thrown");
                } else {
                    tui.print(((ErrorMessage) response).getErrorSpecifiy());
                }
            } catch (Exception e) {
                tui.print("error throwing dice");
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("blast") && client.getClientGameController().getMTcontrolled())
            tui.print("you have to manage a blast first");
        else if (command.equals("manage") && client.getClientGameController().getMTcontrolled()) {
            try {
                char dimension = client.getClientGameController().getDiceAndInfo().get(0).charAt(0);
                char direction = client.getClientGameController().getDiceAndInfo().get(1).charAt(0);
                int dice = Integer.parseInt(client.getClientGameController().getDiceAndInfo().get(2));

                BlastCardMessage manageBlastMessage = tui.handleBlast(client, dimension, direction, dice);
                Message response = client.sendMessageAndWaitResponse(manageBlastMessage, getCreateLobbyTypesMessage3());
                if (response.getType() == MessageType.MANAGE_BLAST_OK) {
                    client.getClientGameController().setMTcontrolled(false);
                    tui.print("managed blast");
                    Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage5());
                    if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                        Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                        client.updateShipboard(shipboard);
                    } else tui.print("error updating your shipboard");

                } else if (response.getType() == MessageType.MANAGE_BLAST_KO) {
                    tui.print("error manage blast");
                }
            } catch (Exception e) {
                tui.print("Error occurred while handling blasts");
                client.getClientGameController().setMTcontrolled(false);
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("manage") && !client.getClientGameController().getMTcontrolled())
            tui.print("there is no blast to manage");
        else if (command.startsWith("apply") && pirates.isControlled()) {

            Message piratesCardMessage = new Message(MessageType.APPLY_PIRATES);
            Message response = client.sendMessageAndWaitResponse(piratesCardMessage, getCreateLobbyTypesMessage4());
            if (response.getType() == MessageType.APPLY_PIRATES_OK) {
                tui.print("pirates applied");
                Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage5());
                if (newShip.getType() == MessageType.ASK_SHIP_OK) {
                    Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
                    client.updateShipboard(shipboard);
                } else tui.print("error updating your shipboard");

                tui.printHelpForCurrentState();
            } else tui.print("error applying pirates");

        } else if (command.equals("apply") && !pirates.isControlled()) tui.print("you have to fight first!");
        else if (command.equals("fight") && pirates.isControlled()) tui.print("you already defeated the pirates");
        else tui.print("unknown command");
    }


    public ArrayList<MessageType> getCreateLobbyTypesMessage1() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.PIRATES_WON_OK, MessageType.PIRATES_WON_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.THROW_DICE_OK, MessageType.THROW_DICE_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage3() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.MANAGE_BLAST_OK, MessageType.MANAGE_BLAST_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage4() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.APPLY_PIRATES_OK, MessageType.APPLY_PIRATES_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage5() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }
}
