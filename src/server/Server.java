package server;

import account.Account;
import client.Client;
import client.ClientGUI;
import client.ClientView;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String LOG_PATH = ".\\src\\server\\log.txt";
    private final String IP = "127.0.0.1";
    private final String PORT = "8080";
    private final FileJob storage;
    private boolean isServerWorking;
    private ServerView serverView;
    private final List<Account> whiteList;
    private final List<ClientView> clientGUIList;

    {
        whiteList = new ArrayList<>();
        whiteList.add(new Account("Anton", "1234", IP, PORT));
        whiteList.add(new Account("Anton2", "1234", IP, PORT));
    }

    public Server(FileJob storage) {
        clientGUIList = new ArrayList<>();
        this.serverView = new ServerGUI(this);
        this.storage = storage;
    }

    public String readFromLog() {
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

    public void writeMessageToLog(String text) {
        serverView.printToMessageLog(text);
        storage.write(text, LOG_PATH);
        answerAll(text);
    }
    private void answerAll(String text){
        for (ClientView clientView : clientGUIList) {
            clientView.printMessage(text);
        }
    }
    public void sendMessageToTempLog(String message) {
        serverView.printMessageToTempLog(message);
    }
    public void clientAdd(ClientView clientView){
        clientGUIList.add(clientView);
    }

    public void disconnectAll() {
        clientGUIList.forEach((el)->{
            el.printMessage("Disconnected");
            el.disconnect();
            sendMessageToTempLog("User "+el.getLoginName()+" is disconnected\n");
        });
        clientGUIList.clear();
    }
}
