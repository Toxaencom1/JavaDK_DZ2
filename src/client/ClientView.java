package client;

public interface ClientView {
    void printMessage(String text);
    void disconnect();
    String getLoginName();

}
