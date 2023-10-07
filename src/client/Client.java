package client;

import account.Account;
import exceptions.AlreadyLoggedEx;
import exceptions.FileProblemsEx;
import server.Server;

public class Client {
    private Account user;
    private ClientView clientView;
    private final Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.server = server;
        this.clientView = clientView;
    }

    public boolean checkUser() throws AlreadyLoggedEx {
        return server.checkConnection(this);
    }

    public void disconnect() {
        setConnected(false);
        clientView.disconnect();
    }


    public String getHistory() throws FileProblemsEx {
        return server.readFromLog();
    }

    public void writeMessage(String text) throws FileProblemsEx {
        server.writeMessageToLog(text);
    }

    public void sendMessageToServer(String message) {
        server.showMessage(message);
    }

    public void appendToList() {
        server.clientAdd(this);
    }

    public void removeFromList() {
        server.clientRemove(this);
    }

    public void showMessage(String message) {
        clientView.printMessage(message);
    }

    public boolean isConnected() {
        return connected;
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
}
