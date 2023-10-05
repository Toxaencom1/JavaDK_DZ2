package server;

import java.io.*;

public class Storage implements FileJob{
    @Override
    public void write(String text, String filePath) {
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
            e.printStackTrace();
            System.err.println("Error when writing text to a file: " + e.getMessage());
        }
    }

    @Override
    public String read(String fileName) {
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
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return sb.toString();
    }
}
