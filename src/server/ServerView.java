package server;

public interface ServerView {
    /**
     * Prints a message to the user interface.
     *
     * @param message The text of the message to be displayed.
     */
    void printMessage(String message);

    /**
     * Retrieves the associated server instance.
     *
     * @return The server instance associated with this view.
     */
    Server getServer();

}
