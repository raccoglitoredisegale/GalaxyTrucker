package it.polimi.ingsw.Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(54321);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            System.out.println("Server disconnected");
            System.exit(-1);
        }
    }

    /**
     * accept new connection from the client
     */
    public void startServer() {
        System.out.println("Server started, waiting for client connection!");
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, UUID.randomUUID().toString());
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }

}
