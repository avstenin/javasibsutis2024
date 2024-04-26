package main.java.org.lab2.utlis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MyFileReader
{

    public static void recursiveRead(File directory){
        File[] files = directory.listFiles();

        if (files != null)
        {
            for (var file : files){
                if (file.isDirectory()){
                    recursiveRead(file);
                }
                else if (file.getName().contains("PingAverageTime")){
                    readFile(file);
                }
            }
        }
    }


    private static void readFile(File file) {
        System.out.println("\n\u001B[31mREAD FILE!!!\u001B[0m\n" );
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            while (bufferedReader.ready())
            {
                System.out.println(bufferedReader.readLine());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\n\u001B[31mSTOP READ FILE!!!\u001B[0m\n" );
    }
}
