package it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage;

import it.polimi.ingsw.Model.FlightBoard.Flightboard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ShowFlightBoardMessage extends Message {
    private final Flightboard flightBoard;


    public ShowFlightBoardMessage(MessageType messageType, Flightboard flightBoard) {
        super(messageType);
        this.flightBoard = flightBoard;
    }

    public Flightboard getFlightBoard() {
        return flightBoard;
    }

}


