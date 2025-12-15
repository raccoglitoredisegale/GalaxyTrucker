package it.polimi.ingsw.Network.Exception;

public class ServerCommunicationError extends RuntimeException {
    private final String message;

    public ServerCommunicationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
