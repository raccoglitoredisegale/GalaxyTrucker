package it.polimi.ingsw.Network.Exception;

public class GenericCommnadException extends Exception {
    private final String message;

    public GenericCommnadException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
