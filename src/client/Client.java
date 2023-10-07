package client;

import account.Account;
import server.Server;

public class Client {
    private Account user;
    private ClientView clientView;
    private final Server server;
    private boolean connected;

    public Client(Server server) {
        this.server = server;
        this.clientView = new ClientGUI(this);
    }

    public boolean checkUserToConnect() {
        return server.checkConnection(this);
    }

    public void disconnect() {
        setConnected(false);
        clientView.disconnect();
    }

    public boolean isServerWorking() {
        return server.isServerWorking();
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getUser() {
        return user;
    }

    public String getUserName() {
        return user.getName();
    }

    public String getHistory() {
        return server.readFromLog();
    }

    public void sendMessage(String text) {
        server.writeMessageToLog(text);
    }

    public void sendMessageToTempLog(String message) {
        server.sendMessageToTempLog(message);
    }

    public void appendToList() {
        server.clientAdd(this);
    }
    public void removeFromList() {
        server.clientRemove(this);
    }

    public boolean isConnected() {
        return connected;
    }

    public void displayMessage(String message) {
        clientView.printMessage(message);
    }
}
