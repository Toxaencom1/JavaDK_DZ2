package client;

import account.Account;
import exceptions.AlreadyLoggedEx;
import exceptions.FileProblemsEx;
import server.Server;


public class Client {
    private Account user;
    private final ClientView clientView;
    private final Server server;
    private boolean connected;

    /**
     * Constructs a new client with the given client view and server.
     *
     * @param clientView The client view associated with this client.
     * @param server     The server this client will connect to.
     */
    public Client(ClientView clientView, Server server) {
        this.server = server;
        this.clientView = clientView;
    }

    /**
     * Checks if the user can be associated with this server.
     *
     * @return `true` if the user is already logged in; otherwise, `false`.
     * @throws AlreadyLoggedEx If the user is already logged in on the server.
     */
    public boolean checkUser() throws AlreadyLoggedEx {
        return server.checkConnection(this);
    }

    /**
     * Disconnects the client from the server.
     * This method sets the 'connected' flag to false and notifies the client view.
     */
    public void disconnect() {
        setConnected(false);
        clientView.disconnect();
    }

    /**
     * Retrieves the chat history from the server.
     *
     * @return The chat history as a string.
     * @throws FileProblemsEx If there are problems reading the chat history from the server.
     */
    public String getHistory() throws FileProblemsEx {
        return server.readFromLog();
    }

    /**
     * Writes a message to the chat history on the server.
     *
     * @param text The message text to be written.
     * @throws FileProblemsEx If there are problems writing the message to the chat history on the server.
     */
    public void writeMessage(String text) throws FileProblemsEx {
        server.writeMessageToLog(text);
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to be sent to the server.
     */
    public void sendMessageToServer(String message) {
        server.showMessage(message);
    }

    /**
     * Adds this client to the list of connected clients on the server.
     */
    public void appendToList() {
        server.clientAdd(this);
    }

    /**
     * Removes this client from the list of connected clients on the server.
     */
    public void removeFromList() {
        server.clientRemove(this);
    }

    /**
     * Displays a message on the client view.
     *
     * @param message The message to be displayed on the client view.
     */
    public void showMessage(String message) {
        clientView.printMessage(message);
    }

    /**
     * Checks if the client is currently connected to the server.
     *
     * @return `true` if the client is connected; otherwise, `false`.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Checks if the server is currently working.
     *
     * @return `true` if the server is working; otherwise, `false`.
     */
    public boolean isServerWorking() {
        return server.isServerWorking();
    }

    /**
     * Sets the connected status of the client.
     *
     * @param connected `true` to indicate that the client is connected; `false` otherwise.
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Sets the user associated with this client.
     *
     * @param user The user account to be associated with this client.
     */
    public void setUser(Account user) {
        this.user = user;
    }

    /**
     * Retrieves the user associated with this client.
     *
     * @return The user account associated with this client.
     */
    public Account getUser() {
        return user;
    }

    /**
     * Retrieves the name of the user associated with this client.
     *
     * @return The name of the user associated with this client.
     */
    public String getUserName() {
        return user.getName();
    }
}
