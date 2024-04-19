import network.MobileNetwork;
import network.Network;
import network.Operator;
import network.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lab4 {
    public static void main(String[] args) {
        Network network = new MobileNetwork();
        network.ReadDataFromCsv();

        boolean exit = false;
        while (!exit) {
            System.out.println("MENU");
            System.out.println("1) Get the price of services");
            System.out.println("2) Get the list of operators by service");
            System.out.println("3) Get the list of services by operator");
            System.out.println("4) Exit");

            Scanner input = new Scanner(System.in);
            int option;
            try{
                option = input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            int serviceNumber;
            Map<Operator, Double> prices = null;
            switch (option) {
                case 1:
                    System.out.println("Choose a service");
                    for (int i = 0; i < network.getServices().size(); i++) {
                        System.out.println((i + 1) + ") " + network.getServices().get(i));
                    }
                    try {
                        serviceNumber = input.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        continue;
                    }

                    prices = network.getServicePrices(network.getServices().get(serviceNumber - 1));

                    System.out.println("Prices for " + network.getServices().get(serviceNumber - 1) + ":");
                    for (Operator operator : prices.keySet()) {
                        System.out.println(operator.getName() + ": " + prices.get(operator));
                    }
                    break;
                case 2:
                    System.out.println("Choose a service");
                    for (int i = 0; i < network.getServices().size(); i++) {
                        System.out.println((i + 1) + ") " + network.getServices().get(i));
                    }
                    try {
                        serviceNumber = input.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        continue;
                    }

                    prices = network.getServicePrices(network.getServices().get(serviceNumber - 1));

                    System.out.println("Operators providing the " + network.getServices().get(serviceNumber - 1) + ":");
                    for (Operator operator : prices.keySet()) {
                        System.out.println(operator.getName());
                    }
                    break;
                case 3:
                    System.out.println("Choose a operator");
                    for (int i = 0; i < network.getOperators().size(); i++) {
                        System.out.println((i + 1) + ") " + network.getOperators().get(i));
                    }
                    try {
                        serviceNumber = input.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        continue;
                    }
                    List<Service> services = network.getServices(network.getOperators().get(serviceNumber - 1));
                    for (Service service : services) {
                        System.out.println(service.getName() + ": " + service.getPrice());
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong option");
            }
        }

    }
}
