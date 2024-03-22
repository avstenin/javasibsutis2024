package tools;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Lab3Manager {
    public static Vector<String> getAddressesFromFile() throws FileNotFoundException {
        Vector<String> addresses = new Vector<>();
        try {
            File file = new File("lab3/galetov/resources/input");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                addresses.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public static void WriteAddressesInFile(Map<String, Double> addresses) {
        String fileName = LocalDate.now().toString() + "_" +
                LocalTime.now().getHour() + "h-" +
                LocalTime.now().getMinute() + "m-" +
                LocalTime.now().getSecond() + "s";
        try(FileWriter writer = new FileWriter("lab3/galetov/resources/out/" + fileName, false))
        {
            for (var address : addresses.keySet()) {
                if(addresses.get(address) < 0){
                    writer.write(address + " : unreachable\n");
                } else {
                    writer.write(address + " : " + addresses.get(address) + "\n");
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void ReadHistory(final File folder, List<String> files){
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ReadHistory(fileEntry, files);
            } else {
                files.add(fileEntry.getName());
            }
        }
    }
}
