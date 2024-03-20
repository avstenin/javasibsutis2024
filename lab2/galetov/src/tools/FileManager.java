package tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileManager {
    public static void WriteAddressesInFile(Map<String, Double> addresses, String fileName) {
        try(FileWriter writer = new FileWriter("lab2/galetov/resources/" + fileName, false))
        {
            for (var address : addresses.keySet()) {
                writer.write(address + " : " + addresses.get(address) + "\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void ReadDirectory(final File folder, List<String> files){
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ReadDirectory(fileEntry, files);
            } else {
                files.add(fileEntry.getName());
            }
        }
    }

    public static void ReadFile(String fileName){
        try(FileReader reader = new FileReader("lab2/galetov/resources/" + fileName))
        {
            int c;
            while((c=reader.read())!=-1){

                System.out.print((char)c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
