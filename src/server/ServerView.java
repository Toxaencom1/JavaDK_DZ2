package server;

public interface ServerView {
    void displayMessage(String text);

    void printMessageToTempLog(String message);

    Server getServer();

}
