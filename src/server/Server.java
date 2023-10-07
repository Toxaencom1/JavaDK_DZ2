package server;

import account.Account;
import client.Client;
import exceptions.AlreadyLoggedEx;
import exceptions.FileProblemsEx;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String DEFAULT_NAME = "Test";
    public static final String DEFAULT_PASS = "1234";
    public static final String LOG_PATH = ".\\src\\server\\log.txt";
    private final String IP = "127.0.0.1";
    private final String PORT = "8080";
    private final FileJob fileJob;
    private boolean isServerWorking;
    private final ServerView serverView;
    private final List<Account> whiteList;
    private final List<Client> clientList;

    {
        whiteList = new ArrayList<>();
        whiteList.add(new Account(DEFAULT_NAME + "1", DEFAULT_PASS, IP, PORT));
        whiteList.add(new Account(DEFAULT_NAME + "2", DEFAULT_PASS, IP, PORT));
    }

    /**
     * Constructs a new server with the specified server view and file storage.
     *
     * @param serverView The server view associated with this server.
     * @param storage    The file storage component for logging messages.
     */
    public Server(ServerView serverView, FileJob storage) {
        clientList = new ArrayList<>();
        this.serverView = serverView;
        this.fileJob = storage;
    }


    /**
     * Reads the chat history from the log file.
     *
     * @return The chat history as a string.
     * @throws FileProblemsEx If there are problems reading the chat history from the log file.
     */
    public String readFromLog() throws FileProblemsEx {
        return fileJob.read(LOG_PATH);
    }

    /**
     * Writes a message to the log file and sends it to all connected clients.
     *
     * @param text The message text to be written and sent.
     * @throws FileProblemsEx If there are problems writing the message to the log file.
     */
    public void writeMessageToLog(String text) throws FileProblemsEx {
        fileJob.write(text, LOG_PATH);
        answerAll(text);
    }

    /**
     * Checks if a client is allowed to connect based on the whitelist and whether the server is online.
     *
     * @param client The client attempting to connect.
     * @return `true` if the client is allowed to connect; otherwise, `false`.
     * @throws AlreadyLoggedEx If the client is already logged in.
     */
    public boolean checkConnection(Client client) throws AlreadyLoggedEx {
        if (isServerWorking) {
            for (Client c : clientList) {
                if (c.getUser().equals(client.getUser())) {
                    throw new AlreadyLoggedEx("Already Logged in");
                }
            }
            return whiteList.contains(client.getUser());
        }
        return false;
    }

    /**
     * Sends a message to all connected clients.
     *
     * @param text The message text to be sent.
     */
    private void answerAll(String text) {
        for (Client client : clientList) {
            client.showMessage(text);
        }
    }

    /**
     * Displays a message on the server view.
     *
     * @param message The message to be displayed on the server view.
     */
    public void showMessage(String message) {
        serverView.printMessage(message);
    }

    /**
     * Adds a client to the list of connected clients.
     *
     * @param client The client to be added.
     */
    public void clientAdd(Client client) {
        clientList.add(client);
    }

    /**
     * Removes a client from the list of connected clients.
     *
     * @param client The client to be removed.
     */
    public void clientRemove(Client client) {
        clientList.remove(client);
    }

    /**
     * Disconnects all connected clients and clears the client list.
     */
    public void disconnectAll() {
        clientList.forEach((el) -> {
            el.showMessage("Disconnected\n");
            el.disconnect();
        });
        clientList.clear();
    }

    /**
     * Checks if the server is currently working.
     *
     * @return `true` if the server is working; otherwise, `false`.
     */
    public boolean isServerWorking() {
        return isServerWorking;
    }

    /**
     * Sets the working status of the server.
     *
     * @param serverWorking `true` to indicate that the server is working; `false` otherwise.
     */
    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }
}
