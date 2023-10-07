package server;

import account.Account;
import client.Client;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String DEFAULT_NAME = "User";
    public static final String LOG_PATH = ".\\src\\server\\log.txt";
    private final String IP = "127.0.0.1";
    private final String PORT = "8080";
    private final FileJob fileJob;
    private boolean isServerWorking;
    private ServerView serverView;
    private final List<Account> whiteList;
    private final List<Client> clientList;

    {
        whiteList = new ArrayList<>();
        whiteList.add(new Account(DEFAULT_NAME+"1", "1234", IP, PORT));
        whiteList.add(new Account(DEFAULT_NAME+"2", "1234", IP, PORT));
    }

    public Server(FileJob storage) {
        clientList = new ArrayList<>();
        this.serverView = new ServerGUI(this);
        this.fileJob = storage;
    }

    public String readFromLog() {
        return fileJob.read(LOG_PATH);
    }



    public boolean checkConnection(Client client) {
        if (isServerWorking) {
            return whiteList.contains(client.getUser());
        }
        return false;
    }

    public void writeMessageToLog(String text) {
        serverView.printToMessageLog(text);
        fileJob.write(text, LOG_PATH);
        answerAll(text);
    }

    private void answerAll(String text) {
        for (Client client : clientList) {
            client.displayMessage(text);
        }
    }

    public void sendMessageToTempLog(String message) {
        serverView.printMessageToTempLog(message);
    }

    public void clientAdd(Client client) {
        clientList.add(client);
    }
    public void clientRemove(Client client) {
        clientList.remove(client);
    }

    public void disconnectAll() {
        clientList.forEach((el) -> {
            el.displayMessage("Disconnected\n");
            el.disconnect();
            sendMessageToTempLog("User " + el.getUserName() + " is disconnected\n");
        });
        clientList.clear();
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }

}
