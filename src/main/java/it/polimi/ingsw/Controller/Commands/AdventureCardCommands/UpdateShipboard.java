package it.polimi.ingsw.Controller.Commands.AdventureCardCommands;

import it.polimi.ingsw.Controller.Commands.ServerControllerCommand;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.UpdateShipboardMessage;
import it.polimi.ingsw.Network.Server.ClientHandler;

public class UpdateShipboard implements ServerControllerCommand {
    @Override
    public void execute(ClientHandler handler, Message message) {
        try {
            UpdateShipboardMessage updateShipboardMessage = new UpdateShipboardMessage(MessageType.ASK_SHIP_OK);
            updateShipboardMessage.setShipboard(handler.getPlayer().getShip());
            handler.sendToClient(updateShipboardMessage);
        } catch (Exception e) {
            handler.sendToClient(new UpdateShipboardMessage(MessageType.ASK_SHIP_KO));
        }

    }
}
