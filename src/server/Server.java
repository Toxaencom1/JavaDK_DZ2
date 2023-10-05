package server;

import account.Account;
import client.Client;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String LOG_PATH = ".\\src\\server\\log.txt";
    private final String IP = "127.0.0.1";
    private final String PORT = "8080";
    private final FileJob storage;
    private boolean isServerWorking;
    private final List<Account> whiteList;

    {
        whiteList = new ArrayList<>();
        whiteList.add(new Account("Anton", "1234", IP, PORT));
        whiteList.add(new Account("Anton2", "1234", IP, PORT));
    }

    public Server() {
        new ServerGUI(this);
        storage = new Storage();
    }

    String readFromLog() {
        return storage.read(LOG_PATH);
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }

    public boolean checkConnection(Client client) {
        if (isServerWorking) {
            return whiteList.contains(client.getUser());
        }
        return false;
    }
}
