package server;

import exceptions.FileProblemsEx;

public interface FileJob {
    /**
     * Writes(append) the specified text to the specified file.
     *
     * @param text     The text to be written to the file.
     * @param filePath The path to the file where the text will be written.
     * @throws FileProblemsEx If there are problems writing the text to the file.
     */
    void write(String text, String filePath) throws FileProblemsEx;
    /**
     * Reads the contents of the specified file and returns them as a string.
     *
     * @param fileName The name of the file to be read.
     * @return The contents of the file as a string.
     * @throws FileProblemsEx If there are problems reading the file.
     */
    String read(String fileName) throws FileProblemsEx;
}
