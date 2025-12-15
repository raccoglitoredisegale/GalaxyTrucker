package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class ServerListener implements Runnable {
    private final Client client;
    private final Object keepAliveLock;
    private Boolean keepAlive;

    public ServerListener(Client client) {
        this.client = client;
        this.keepAlive = true;
        this.keepAliveLock = new Object();
    }

    @Override
    public void run() {
        while (true) {

            synchronized (this.keepAliveLock) {
                if (!keepAlive) {
                    break;
                }
            }

            try {
                Message message = (Message) client.readSerializableObject();
                if (message != null) {
                    ArrayList<MessageType> waitingFor = client.messagesWaitingForReply();

                    if (waitingFor.contains(message.getType())) {
                        Object lock = client.waitLock();
                        synchronized (lock) {
                            client.setExpectedResponseMessage(message);
                            lock.notifyAll();
                        }
                    } else {
                        client.handleBroadcast(message);
                    }
                }
            } catch (Exception e) {
                synchronized (keepAliveLock) {
                    keepAlive = false;
                }
                break;
            }

        }
    }
}
