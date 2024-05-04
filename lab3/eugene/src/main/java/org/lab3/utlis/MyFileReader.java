package main.java.org.lab3.utlis;

import main.java.org.lab3.exceptions.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFileReader {
    public static List<String> stringList = new ArrayList<>();

    public static void recursiveRead(String regime, File directory) throws FileException
    {
        if (!directory.getAbsolutePath().contains("java")) throw new FileException("Wrong directory");

        File[] files = directory.listFiles();
        if (files != null) {
            for (var file : files) {
                if (file.isDirectory() && !file.getName().equals("out")) {
                    recursiveRead(regime, file);
                } else if (file.getName().contains(regime)) {
                    readFile(file);
                }
            }
        }
    }

    public static void readFile(File file) throws FileException {
        stringList.add("\n" + file.getName());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringList.add(line);
            }
        } catch (IOException e) {
            throw new FileException("Error reading file: " + file.getName(), e);
        }
    }
}
