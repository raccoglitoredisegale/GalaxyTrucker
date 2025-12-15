package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Controller.Commands.CommandDispatcher;
import it.polimi.ingsw.Controller.GameControllers.SystemController;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    public static List<ClientHandler> clientHandlers = new ArrayList<>();

    private final String socketIdentifier;
    private final CommandDispatcher cd = new CommandDispatcher();
    private final SystemController sc = SystemController.getInstance();
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Player player;

    public ClientHandler(Socket socket, String socketIdentifier) {
        this.socketIdentifier = socketIdentifier;
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }


    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) inputStream.readObject();
                cd.dispatch(message, this);
            } catch (Exception e) {
                break;
            }
        }
        sc.gameLeaved(this);
        System.out.println("Client <socketID>" + socketIdentifier + " disconnected");
    }

    public void sendToClient(Message message) {
        try {
            if (socket.isConnected() && outputStream != null) {
                outputStream.reset();
                outputStream.writeObject(message);
                outputStream.flush(); // Ensure data is sent immediately
            }
        } catch (IOException e) {
            System.err.println("Error sending message to client: " + e.getMessage());
            // Handle disconnection or error, e.g., by cleaning up this client
            cleanupClientResources();
        }
    }

    private void cleanupClientResources() {
        System.out.println("Closing socket: " + socketIdentifier);
        try {
            if (inputStream != null) inputStream.close();
        } catch (IOException e) { /* log */ }
        try {
            if (outputStream != null) outputStream.close();
        } catch (IOException e) { /* log */ }
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) { /* log */ }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
