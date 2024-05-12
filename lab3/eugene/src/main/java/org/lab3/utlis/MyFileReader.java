package org.lab3.utlis;


import org.lab3.exceptions.FileException;

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
            for (var file : files)
            {
                if (file.getName().charAt(0) == '.') continue;
                if (file.isDirectory() && !file.getName().equals("target") &&!file.getName().equals("out")) {
                    recursiveRead(regime, file);
                }
                if (file.getAbsolutePath().contains("lab3"))
                {
                    var lowerCaseFileName = file.getName().toLowerCase();
                    var lowerCaseRegime = regime.toLowerCase();
                    var path = "lab3\\bin\\";

                        if (
                                lowerCaseFileName.contains(".txt") &&
                                lowerCaseFileName.contains(lowerCaseRegime) &&
                                file.getAbsolutePath().toLowerCase().contains(path + lowerCaseRegime)
                        )
                        {
                            readFile(file);
                        }
                }

            }
        }
    }

    public static void readFile(File file) throws FileException
    {
        stringList.add("\n" + file.getName());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringList.add(line);
            }
        } catch (IOException e) {
            throw new FileException("Error reading file: " + file.getName(), e);
        }
    }

    public static String findDir(File root, String name) throws FileException
    {
        String res = find(root, name);
        if (res == null){
            throw new FileException("Directory not found");
        }
        return res;
    }


    private static String find(File root, String name)
    {
        if (root.getName().equals(name))
        {
            return root.getAbsolutePath();
        }

        File[] files = root.listFiles();

        if(files != null)
        {
            for (File f : files)
            {
                if(f.isDirectory())
                {
                    String myResult = find(f, name);
                    if (myResult != null) {
                        return myResult;
                    }
                }
            }
        }
        return null;
    }
}
