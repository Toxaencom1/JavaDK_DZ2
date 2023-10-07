package client;

public interface ClientView {
    /**
     * Prints a message to the client's user interface.
     *
     * @param text The text of the message to be displayed.
     */
    void printMessage(String text);

    /**
     * Disconnects the client from the server.
     */
    void disconnect();

}
