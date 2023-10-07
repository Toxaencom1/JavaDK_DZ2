import client.Client;
import client.ClientGUI;
import server.*;

public class Main {
    public static void main(String[] args) {
        FileJob storage = new Storage();
        ServerView serverView = new ServerGUI(storage);
        Server server = serverView.getServer();
        new ClientGUI(server);
        new ClientGUI(server);
    }
}
