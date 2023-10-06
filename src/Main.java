import client.Client;
import client.ClientGUI;
import server.*;

public class Main {
    public static void main(String[] args) {
        FileJob storage = new Storage();
        Server server = new Server(storage);
        new ClientGUI(server);
        new ClientGUI(server);
    }
}
