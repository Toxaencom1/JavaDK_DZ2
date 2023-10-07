import client.Client;
import client.ClientGUI;
import server.*;

public class Main {
    public static void main(String[] args) {
        FileJob storage = new Storage();
        Server server = new Server(storage);
        new Client(server);
        new Client(server);
    }
}
