package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Model.Shipboard.Shipboard;
import it.polimi.ingsw.Network.Messages.BroadcastMessage.*;
import it.polimi.ingsw.Network.Messages.ClientToServer.PreLobbyMessage.NewUserMessage;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.BlastMessage;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.MeteorMessage;
import it.polimi.ingsw.View.TUIState;
import it.polimi.ingsw.View.TextUserInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private final Object waitLock = new Object();
    private final boolean waitingForReply = false;
    ArrayList<BroadcastMessage> broadcastQueue = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ClientCommandHandlerDispatcher cmh;
    private String username;
    private TextUserInterface tui;
    private ServerListener serverListener;
    private ArrayList<MessageType> messagesWaitingForReply;
    private Message expectedResponseMessage;
    private ClientGameController cgc;
    private Shipboard shipboard;
    private String currentPlayer;


    public Client(Socket socket, String username) {
        this.socket = socket;
        this.username = username;

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.cmh = new ClientCommandHandlerDispatcher(this);
            messagesWaitingForReply = new ArrayList<>();
            this.serverListener = new ServerListener(this);

        } catch (IOException e) {
            System.err.println("Client connection setup failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = consoleScanner.nextLine();
        Client client = null;

        System.out.print("Insert the ip address of the server: ");
        String ip = consoleScanner.nextLine();

        System.out.print("Insert the port number of the server: ");
        int port = consoleScanner.nextInt();


        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port), 5000);
            client = new Client(socket, username);
            Thread listenerThread = new Thread(client.serverListener);
            listenerThread.setName("ServerListenerThread");
            listenerThread.start();

        } catch (ConnectException e) {
            System.out.println("Failed to connect to server: Connection refused");
            return; // or System.exit(1); depending on your application flow

        } catch (UnknownHostException e) {
            System.out.println("Failed to connect to server: Unknown ip server address");
            System.out.println("Please check if the IP address is not valid");
            return; // or System.exit(1);

        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out");
            System.out.println("The server may be unreachable or taking too long to respond");
            return; // or System.exit(1);

        } catch (IOException e) {
            System.out.println("Failed to connect to server: " + e.getMessage());
            System.out.println("Please check your network connection and server details");
            return; // or System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Failed to connect to server: " + e.getMessage());
        }
        boolean exit = true;


        do {
            Message response = client.sendMessageAndWaitResponse(
                    new NewUserMessage(MessageType.USERNAME, username), new ArrayList<>(Arrays.asList(MessageType.USERNAME_OK, MessageType.USERNAME_KO))
            );
            if (response.getType() == MessageType.USERNAME_OK) {
                TextUserInterface tui = new TextUserInterface(client);
                client.setTUI(tui);
                tui.start(1);
                exit = false;
            } else {
                System.out.println("The username " + username + " is already in use.");
                System.out.print("Enter a new username (or type 'exit' to quit): ");
                username = consoleScanner.nextLine();
                if (username.equals("exit")) {
                    exit = false;
                }
            }
        } while (exit);

    }

    public Object readSerializableObject() throws IOException, ClassNotFoundException {
        return this.inputStream.readUnshared();
    }


    public synchronized Message sendMessageAndWaitResponse(Message message, ArrayList<MessageType> types) {
        synchronized (this.waitLock) {
            this.messagesWaitingForReply.addAll(types);
        }
        return sendMessage(message);

    }


    private synchronized Message sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cant send the message: " + e.getMessage());
        }

        synchronized (this.waitLock) {
            while (this.expectedResponseMessage == null) {
                try {
                    this.waitLock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Interrupted while waiting for response" + e.getMessage());
                }
            }
            this.messagesWaitingForReply.clear();
        }

        Message response = this.expectedResponseMessage;
        this.expectedResponseMessage = null;
        return response;
    }

    public void instanceClientGameController() {
        this.cgc = new ClientGameController();
    }

    public void handleBroadcast(Message message) {
        if (!message.getType().equals(MessageType.BROADCAST)) {
            System.out.println("Client received not a broadcast message in handleBroadcast function: " + message.getType());
        } else {
            BroadcastMessage bm = (BroadcastMessage) message;
            switch (bm.getSpecificType()) {
                case MessageType.GAME_STARTED, FINISHED_ASSEMBLY_PHASE -> getTUI().handleBroadcastMessageByServer(bm);
                case MessageType.BC_ADVENTURE_CARD -> {
                    ContinueAdventureCardMessage adventureCardMessage = ((BroadcastMessageAdventureCard) message).getMessage();
                    switch (adventureCardMessage.getType()) {
                        case MessageType.CONTINUE_ADVENTURE_CARD -> {
                            getClientGameController().updateCurrentAdventureCard(adventureCardMessage.getAdventureCard());
                            currentPlayer = adventureCardMessage.getCurrentPlayerUsername();
                            if (tui.getTUIState() != TUIState.STALL) {
                                if ((getUsername().equals(adventureCardMessage.getCurrentPlayerUsername())) || (adventureCardMessage.getCurrentPlayerUsername().equals("0000"))) {
                                    ClientAdventureCardDispatcher acd = new ClientAdventureCardDispatcher(this, getTUI());
                                    acd.dispatch(adventureCardMessage.getAdventureCard());
                                    getClientGameController().setIsDefeated(false);
                                } else {
                                    getTUI().setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                                }

                            }
                            getTUI().handleBroadcastMessageByServer(bm);
                            break;
                        }
                        case MessageType.WARZONE_CREW_PENALTY -> {
                            getClientGameController().updateCurrentAdventureCard(adventureCardMessage.getAdventureCard());
                            currentPlayer = adventureCardMessage.getCurrentPlayerUsername();
                            if (tui.getTUIState() != TUIState.STALL) {
                                if ((getUsername().equals(adventureCardMessage.getCurrentPlayerUsername())) || (adventureCardMessage.getCurrentPlayerUsername().equals("0000"))) {
                                    tui.setTUIState(TUIState.ADVENTURE_CARD_WARZONE_CREW);
                                } else {
                                    getTUI().setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                                }
                            }
                            getTUI().handleBroadcastMessageByServer(bm);
                        }
                        case MessageType.WARZONE_GOODS_PENALTY -> {
                            getClientGameController().updateCurrentAdventureCard(adventureCardMessage.getAdventureCard());
                            currentPlayer = adventureCardMessage.getCurrentPlayerUsername();
                            if (tui.getTUIState() != TUIState.STALL) {
                                if ((getUsername().equals(adventureCardMessage.getCurrentPlayerUsername())) || (adventureCardMessage.getCurrentPlayerUsername().equals("0000"))) {
                                    tui.setTUIState(TUIState.ADVENTURE_CARD_WARZONE_GOODS);
                                } else {
                                    getTUI().setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                                }
                            }
                            getTUI().handleBroadcastMessageByServer(bm);
                        }
                        case MessageType.WARZONE_BLAST_PENALTY -> {
                            getClientGameController().updateCurrentAdventureCard(adventureCardMessage.getAdventureCard());
                            currentPlayer = adventureCardMessage.getCurrentPlayerUsername();
                            if (tui.getTUIState() != TUIState.STALL) {
                                if ((getUsername().equals(adventureCardMessage.getCurrentPlayerUsername())) || (adventureCardMessage.getCurrentPlayerUsername().equals("0000"))) {
                                    tui.setTUIState(TUIState.ADVENTURE_CARD_WARZONE_BLAST);
                                } else {
                                    getTUI().setTUIState(TUIState.ADVENTURE_CARD_WAIT);
                                }
                            }
                            getTUI().handleBroadcastMessageByServer(bm);
                        }
                    }
                }
                case MessageType.WARZONE_CONTROL_INFO -> {
                    BroadcastMessageWarzoneInfo warzoneInfo = (BroadcastMessageWarzoneInfo) message;
                    if (warzoneInfo.getCurrentPlayerName().equals(getUsername()) && !warzoneInfo.getCurrentPlayerName().equals("0000")) {
                        tui.setTUIState(TUIState.ADVENTURE_CARD_WARZONE);
                    }
                    tui.handleBroadcastMessageByServer(bm);
                }
                case MessageType.ADVENTURE_CARD_COMPLETED -> getTUI().handleBroadcastMessageByServer(bm);
                case MessageType.END_ADVENTURE_CARD_PHASE -> getTUI().handleBroadcastMessageByServer(bm);
                case MessageType.STALL_PLAYER_OK -> getTUI().handleBroadcastMessageByServer(bm);
                case MessageType.METEOR_INFO -> {
                    MeteorMessage meteorMessage = ((BroadcastMessageMeteor) bm).getMessage();
                    getClientGameController().setDiceAndInfo(meteorMessage.getDimension(), meteorMessage.getDirection(), meteorMessage.getDice());
                    break;
                }
                case MessageType.BLAST_INFO -> {
                    BlastMessage blastMessage = ((BroadcastMessageBlast) message).getMessage();
                    getClientGameController().setDiceAndInfo(blastMessage.getDimension(), blastMessage.getDirection(), blastMessage.getDice());
                    getTUI().handleBroadcastMessageByServer(bm);
                    break;
                }
            }
        }
    }


    public void shutdown() {
        try {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) { /* ignore */ }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) { /* ignore */ }
            }
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) { /* ignore */ }
            }
            System.out.println("Client shutting down");
        } finally {
            outputStream = null;
            inputStream = null;
            socket = null;
            System.out.println("Client resources closed.");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public Socket getSocket() {
        return socket;
    }

    public TextUserInterface getTUI() {
        return this.tui;
    }

    public void setTUI(TextUserInterface tui) {
        this.tui = tui;
    }

    public ClientCommandHandlerDispatcher getCommandMatchHandlerDispatcher() {
        return this.cmh;
    }

    public ArrayList<MessageType> messagesWaitingForReply() {
        return this.messagesWaitingForReply;
    }

    public Object waitLock() {
        return this.waitLock;
    }

    public void setExpectedResponseMessage(Message expectedResponseMessage) {
        this.expectedResponseMessage = expectedResponseMessage;
    }

    public ClientGameController getClientGameController() {
        return this.cgc;
    }

    public void setShipboard() {
        this.shipboard = new Shipboard(2);
    }

    public Shipboard getShipboard() {
        return this.shipboard;
    }

    public void updateShipboard(Shipboard ship) {
        this.shipboard = ship;
    }
}
