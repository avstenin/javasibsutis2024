import tools.FileManager;
import tools.PingCommand;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Lab2 {
    public static void main(String[] args) throws IOException {
        boolean running = true;
        while(running) {
            Scanner input = new Scanner(System.in);
            System.out.println("What do you want to do?");
            System.out.println("1) Read ping statistics from a file");
            System.out.println("2) Enter addresses to ping");
            System.out.println("3) Exit");
            int choice = 0;
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (choice == 1) {
                List<String> files = new ArrayList<>();
                FileManager.ReadDirectory(new File("lab2/galetov/resources"), files);
                System.out.println("Files found: " + files.size());
                for(String file : files) {
                    System.out.println(file);
                }
                System.out.println("Enter the name of the file you want to read");
                String fileName = input.next();
                try{
                    FileManager.ReadFile(fileName);
                } catch (Exception e) {
                    System.out.println("The file could not be opened");
                }

            } else if (choice == 2) {
                System.out.println("How many addresses do you want to enter?");
                int dnsCount = 0;
                try {
                    dnsCount = input.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    continue;
                }
                Map<String, Double> addresses = new HashMap<>();

                System.out.println("Enter addresses:");
                String address;
                for (int i = 0; i < dnsCount; ++i) {
                    address = input.next();
                    addresses.put(address, PingCommand.getAverageResponseFromAddress(address, 4));
                }
                for(var add : addresses.keySet()) {
                    if(addresses.get(add) < 0) {
                        System.out.println("Address " + add + " is unreachable");
                    } else {
                        System.out.println("Address " + add + " has an average response time of " + addresses.get(add) + " ms");
                    }
                }
                System.out.println("Do you want to write the results to a file? (y/n)");
                String answer = input.next();
                if(answer.equals("y")) {
                    System.out.println("Enter the name of the file you want to write to");
                    String fileName = input.next();
                    FileManager.WriteAddressesInFile(addresses, fileName);
                }
            } else if (choice == 3) {
                running = false;
            } else {
                System.out.println("Uncorrect choice, try again");
            }
        }
    }
}
