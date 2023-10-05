package client;

import account.Account;
import server.Server;

public class Client {
    private Account user;
    private ClientView clientView;
    private final Server server;
    private boolean connected;

    public Client(ClientView clientView,Server server) {
        this.server = server;
        this.clientView = clientView;
    }

    public boolean checkUserToConnect() {
        return server.checkConnection(this);
    }
    public void disconnect(){
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
}
