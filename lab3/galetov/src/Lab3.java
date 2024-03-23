import exceptions.ChoiceException;
import exceptions.EmptyOutDirectoryException;
import exceptions.InputException;
import tools.Lab3Manager;
import tools.PingCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lab3 {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("1) File input");
        System.out.println("2) Console input");
        System.out.println("3) Ping history");
        int choice = input.nextInt();
        Map<String, Double> addresses = new HashMap<>();
        switch (choice) {
            case 1:
                System.out.println("Please wait...");
                Vector<String> addressesNames;
                try{
                    addressesNames = Lab3Manager.getAddressesFromFile();
                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException();
                }

                for(var address : addressesNames) {
                    addresses.put(address, PingCommand.getAverageResponseFromAddress(address, 4));
                    if(addresses.get(address) < 0) {
                        System.out.println("Address " + address + " is unreachable");
                    } else {
                        System.out.println("Address " + address + " has an average response time of " + addresses.get(address) + " ms");
                    }
                }
                Lab3Manager.WriteAddressesInFile(addresses);
                break;
            case 2:
                System.out.println("How many addresses do you want to enter?");
                int dnsCount = 0;
                try {
                    dnsCount = input.nextInt();
                } catch (Exception e) {
                    throw new InputException();
                }
                System.out.println("Enter addresses:");
                String address;
                for (int i = 0; i < dnsCount; ++i) {
                    address = input.next();
                    addresses.put(address, PingCommand.getAverageResponseFromAddress(address, 4));
                }
                System.out.println("Please wait...");
                for(var add : addresses.keySet()) {
                    if(addresses.get(add) < 0) {
                        System.out.println("Address " + add + " is unreachable");
                    } else {
                        System.out.println("Address " + add + " has an average response time of " + addresses.get(add) + " ms");
                    }
                }
                Lab3Manager.WriteAddressesInFile(addresses);
                break;
            case 3:
                List<String> history = new ArrayList<>();
                Lab3Manager.ReadHistory(new File("lab3/galetov/resources/out"), history);
                if(history.isEmpty()) {
                    throw new EmptyOutDirectoryException();
                } else {
                    for(var add : history) {
                        System.out.println(add);
                    }
                }
                break;
            default:
                throw new ChoiceException();

        }
    }
}
