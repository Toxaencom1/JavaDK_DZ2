package server;

public interface FileJob {
    void write(String text, String filePath);
    String read(String fileName);
}
