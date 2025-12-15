package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;

public class BroadcastMessageWarzoneInfo extends BroadcastMessage {
    int parameter;
    String looser;
    Character penalty;
    private final String playerName;
    private final String currentPlayerName;
    private final MessageType specificType;
    private final Character criteria;

    public BroadcastMessageWarzoneInfo(MessageType messageType, MessageType specificType, String playerName, String currentPlayerName, int parameter, Character criteria, Character penalty, String looser) {
        super(messageType, messageType);
        this.playerName = playerName;
        this.currentPlayerName = currentPlayerName;
        this.parameter = parameter;
        this.criteria = criteria;
        this.specificType = specificType;
        this.penalty = penalty;
        this.looser = looser;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public int getParameter() {
        return parameter;
    }

    public MessageType getSpecificType() {
        return specificType;
    }

    public Character getCriteria() {
        return criteria;
    }

    public Character getPenalty() {
        return penalty;
    }

    public String getLooser() {
        return looser;
    }
}
