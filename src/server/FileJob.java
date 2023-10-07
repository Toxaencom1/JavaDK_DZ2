package server;

import exceptions.FileProblemsEx;

public interface FileJob {
    void write(String text, String filePath) throws FileProblemsEx;
    String read(String fileName) throws FileProblemsEx;
}
