package client;

import account.Account;
import server.Server;

public class Client {
    private Account user;
    private final Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.server = server;
    }

    public boolean checkUserToConnect() {
        return server.checkConnection(this);
    }

    public void disconnect() {
        setConnected(false);
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

    public String getHistory() {
        return server.readFromLog();
    }

    public void sendMessage(String text) {
        server.writeMessageToLog(text);
    }

    public void sendMessageToTempLog(String message) {
        server.sendMessageToTempLog(message);
    }

    public void appendToList(ClientGUI clientGUI) {
        server.clientAdd(clientGUI);
    }

    public boolean isConnected() {
        return connected;
    }
}
