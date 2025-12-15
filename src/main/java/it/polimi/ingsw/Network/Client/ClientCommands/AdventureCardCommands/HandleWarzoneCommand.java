package it.polimi.ingsw.Network.Client.ClientCommands.AdventureCardCommands;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.Shipboard.ApplyOperation;
import it.polimi.ingsw.Model.Shipboard.OperationVisitor;
import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Client.ClientCommand;
import it.polimi.ingsw.Network.Client.ClientGameController;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.BlastCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneControlCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneCrewPenaltyCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneGoodsPenaltyCardMessage;
import it.polimi.ingsw.Network.Messages.ErrorMessage.ErrorMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.View.TextUserInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class HandleWarzoneCommand implements ClientCommand {
    @Override
    public void execute(Client client, String command) {
        TextUserInterface tui = client.getTUI();
        ClientGameController gc = client.getClientGameController();
        Warzone warzone = (Warzone) client.getClientGameController().getCurrentAdventureCard();
        if ((command.equals("first") || command.equals("second") || command.equals("third")) && warzone.getWait()) {
            tui.print("wait for the others to finish their control");
            return;
        }
        if (command.equals("first")) {
            if (!warzone.getFirstControl() && !warzone.getSecondControl() && !warzone.getThirdControl()) {
                Character firstCriteria = warzone.getWarzoneCriteria().getFirst().get(0);
                switch (firstCriteria) {
                    case 'T': {
                        WarzoneControlCardMessage warzoneTeamCardMessage = new WarzoneControlCardMessage(MessageType.WARZONE_CONTROL);
                        OperationVisitor applyOperation = new ApplyOperation();
                        warzoneTeamCardMessage.setParameter(client.getShipboard().acceptCalculateCrew(applyOperation));
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneTeamCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("crew control successful");
                            warzone.setWait(true);
                            //tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling crew members");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'C': {
                        WarzoneControlCardMessage warzoneCannonCardMessage = tui.handleWarzoneCannon(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneCannonCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("cannons control successful");
                            updateYourShip(client);
                            warzone.setWait(true);
                        } else {
                            tui.print("error controlling cannons");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'E': {
                        WarzoneControlCardMessage warzoneEngineCardMessage = tui.handleWarzoneEngine(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneEngineCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("engines control successful");
                            updateYourShip(client);
                            warzone.setWait(true);

                            // tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling engines");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }

                }

            } else if (command.equals("first") && warzone.getFirstControl())
                tui.print("you already controlled the first parameter");
        } else if (command.equals("second")) {
            if (warzone.getFirstControl() && !warzone.getSecondControl() && !warzone.getThirdControl()) {
                Character secondCriteria = warzone.getWarzoneCriteria().get(1).get(0);
                switch (secondCriteria) {
                    case 'T': {
                        WarzoneControlCardMessage warzoneTeamCardMessage = new WarzoneControlCardMessage(MessageType.WARZONE_CONTROL);
                        OperationVisitor applyOperation = new ApplyOperation();
                        warzoneTeamCardMessage.setParameter(client.getShipboard().acceptCalculateCrew(applyOperation));
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneTeamCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("crew control successful");
                            warzone.setWait(true);

                            //  tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling crew members");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'C': {
                        WarzoneControlCardMessage warzoneCannonCardMessage = tui.handleWarzoneCannon(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneCannonCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("cannons control successful");
                            updateYourShip(client);
                            warzone.setWait(true);

                            //  tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling cannons");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'E': {
                        WarzoneControlCardMessage warzoneEngineCardMessage = tui.handleWarzoneEngine(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneEngineCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("engines control successful");
                            updateYourShip(client);
                            warzone.setWait(true);

                            //  tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling engines");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }

                }

            } else if (command.equals("second") && warzone.getSecondControl())
                tui.print("you already controlled the second parameter");
            else if (command.equals("second") && !warzone.getFirstControl())
                tui.print("finish first the first control");
        } else if (command.equals("third")) {
            if (warzone.getFirstControl() && warzone.getSecondControl() && !warzone.getThirdControl()) {
                Character thirdCriteria = warzone.getWarzoneCriteria().get(2).get(0);
                switch (thirdCriteria) {
                    case 'T': {
                        WarzoneControlCardMessage warzoneTeamCardMessage = new WarzoneControlCardMessage(MessageType.WARZONE_CONTROL);
                        OperationVisitor applyOperation = new ApplyOperation();
                        warzoneTeamCardMessage.setParameter(client.getShipboard().acceptCalculateCrew(applyOperation));
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneTeamCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print(" crew control successful");
                            warzone.setWait(true);

                            //    tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling crew members");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'C': {
                        WarzoneControlCardMessage warzoneCannonCardMessage = tui.handleWarzoneCannon(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneCannonCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("cannons control successful");
                            updateYourShip(client);
                            warzone.setWait(true);

                            //    tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling cannons");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }
                    case 'E': {
                        WarzoneControlCardMessage warzoneEngineCardMessage = tui.handleWarzoneEngine(client);
                        //client.lockBroadCast();
                        Message response = client.sendMessageAndWaitResponse(warzoneEngineCardMessage, getCreateLobbyTypesMessage1());
                        if (response.getType() == MessageType.WARZONE_CONTROL_OK) {
                            tui.print("engines control successful");
                            updateYourShip(client);
                            warzone.setWait(true);

                            //   tui.setTUIState(TUIState.ADVENTURE_CARD_WAIT);

                        } else {
                            tui.print("error controlling engines");
                            tui.printHelpForCurrentState();
                        }
                        //client.unlockBroadCast();
                        break;
                    }

                }

            } else if (command.equals("third") && !warzone.getFirstControl() && !warzone.getSecondControl() && !warzone.getThirdControl()) {
                tui.print("finish first the first control");
            } else tui.print("finish first the second control");


        } else if (command.equals("crewpenalty")) {
            WarzoneCrewPenaltyCardMessage warzoneCrewPenaltyCardMessage = tui.handleWarzoneCrewPenalty(client);
            Message response = client.sendMessageAndWaitResponse(warzoneCrewPenaltyCardMessage, getCreateLobbyTypesMessage2());
            if (response.getType() == MessageType.WARZONE_CREW_PENALTY_OK) {
                tui.print("crew penalty applied successfully");
                updateYourShip(client);

            } else {
                tui.print("error applying crew penalty");
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("goodspenalty")) {
            WarzoneGoodsPenaltyCardMessage warzoneGoodsPenaltyCardMessage = tui.handleWarzoneGoodsPenalty(client);
            Message response = client.sendMessageAndWaitResponse(warzoneGoodsPenaltyCardMessage, getCreateLobbyTypesMessage6());
            if (response.getType() == MessageType.WARZONE_GOODS_PENALTY_OK) {
                tui.print("goods penalty applied successfully");
                updateYourShip(client);

            } else {
                tui.print("error applying goods penalty");
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("blast") && !client.getClientGameController().getMTcontrolled()) {
            try {
                Message response = client.sendMessageAndWaitResponse(new Message(MessageType.THROW_DICE_BLAST), getCreateLobbyTypesMessage3());
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
        else if (command.startsWith("manage") && client.getClientGameController().getMTcontrolled()) {
            try {
                char dimension = client.getClientGameController().getDiceAndInfo().get(0).charAt(0);
                char direction = client.getClientGameController().getDiceAndInfo().get(1).charAt(0);
                int dice = Integer.parseInt(client.getClientGameController().getDiceAndInfo().get(2));


                BlastCardMessage manageBlastMessage = tui.handleBlast(client, dimension, direction, dice);
                Message response = client.sendMessageAndWaitResponse(manageBlastMessage, getCreateLobbyTypesMessage4());
                if (response.getType() == MessageType.MANAGE_BLAST_OK) {

                    tui.print("managed blast");
                    updateYourShip(client);
                    client.getClientGameController().setMTcontrolled(false);
                } else {
                    tui.print("error manage blast");
                    client.getClientGameController().setMTcontrolled(false);
                }
            } catch (Exception e) {
                tui.print("Error occurred while handling blasts");
                client.getClientGameController().setMTcontrolled(false);
                tui.printHelpForCurrentState();
            }
        } else if (command.equals("manage") && !client.getClientGameController().getMTcontrolled())
            tui.print("there is no blast to manage");


        else {
            tui.print("unknown command");
        }
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage1() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.WARZONE_CONTROL_OK, MessageType.WARZONE_CONTROL_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage2() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.WARZONE_CREW_PENALTY_OK, MessageType.WARZONE_CREW_PENALTY_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage3() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.THROW_DICE_OK, MessageType.THROW_DICE_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage4() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.MANAGE_BLAST_OK, MessageType.MANAGE_BLAST_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage5() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.ASK_SHIP_OK, MessageType.ASK_SHIP_KO)
        );
    }

    public ArrayList<MessageType> getCreateLobbyTypesMessage6() {
        return new ArrayList<MessageType>(
                Arrays.asList(MessageType.WARZONE_GOODS_PENALTY_OK, MessageType.WARZONE_GOODS_PENALTY_KO)
        );
    }

    void updateYourShip(Client client) {
        Message newShip = client.sendMessageAndWaitResponse(new Message(MessageType.ASK_SHIP), getCreateLobbyTypesMessage5());
        if (newShip.getType() == MessageType.ASK_SHIP_OK) {
            Shipboard shipboard = ((UpdateShipboardMessage) newShip).getShipboard();
            client.updateShipboard(shipboard);
        } else client.getTUI().print("error updating your shipboard");
    }
}
