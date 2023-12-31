package server;

import exceptions.FileProblemsEx;

import java.io.*;

public class Storage implements FileJob{
    /**
     * Writes(append) the specified text to the specified file.
     *
     * @param text     The text to be written to the file.
     * @param filePath The path to the file where the text will be written.
     * @throws FileProblemsEx If there are problems writing the text to the file.
     */
    @Override
    public void write(String text, String filePath) throws FileProblemsEx {
        try {
            File file = new File(filePath);
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Log updated");
        } catch (IOException e) {
            throw new FileProblemsEx("Error when writing text to a file: " + e.getMessage());
        }
    }
    /**
     * Reads the contents of the specified file and returns them as a string.
     *
     * @param fileName The name of the file to be read.
     * @return The contents of the file as a string.
     * @throws FileProblemsEx If there are problems reading the file.
     */
    @Override
    public String read(String fileName) throws FileProblemsEx {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new FileProblemsEx("An error occurred while reading the file: " + e.getMessage());

        }
        return sb.toString();
    }
}
