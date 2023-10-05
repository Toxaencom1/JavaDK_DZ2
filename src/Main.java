import client.Client;
import client.ClientGUI;
import server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        new ClientGUI(server);
    }
}
