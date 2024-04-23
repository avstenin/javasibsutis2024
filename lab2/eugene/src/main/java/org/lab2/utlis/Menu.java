package main.java.org.lab2.utlis;

import java.util.*;

public class Menu
{
    String nameOS;
    PingChecker pingChecker;

    volatile Map<String, Double> mapOfIpAndAvarangeTime = new HashMap<>();

    public Menu()
    {
        System.out.println("\u001B[31mPlease note: This program may require administrative privileges to perform certain actions. USE SUDO.\u001B[0m");
        this.nameOS = getOSVersion();
        this.pingChecker = new PingChecker(nameOS);
    }

    public void sayHello(){
        System.out.println("Hello, " + System.getProperty("user.name") + "!\nYour OS is -> " + nameOS);
    }

    public void sayBye(){
        System.out.println("Bye, " + System.getProperty("user.name") + "!");
    }

    public void userChoice(){
        Scanner scanner = new Scanner(System.in);


        String menuElement = "What you want to do?\n" +
                "1. Ping addresses\n" +
                "2. Check file from directory\n" +
                "3. Exit";


        while(true){
            switch (validInput(menuElement, scanner)){
                case 1: startPing(scanner); break;
                case 2: break; //TODO;
                case 3: sayBye(); return;
                default:
                    System.out.println("Error.");
            }
        }
    }

    public void startPing(Scanner scanner){
        List<Thread> threads = new ArrayList<>();
        int countOfDNS = validInput("How many addresses do you want to ping?", scanner);
        int countOfPackages = validInput("How many pings per address?",scanner);

        pingAddresses(countOfDNS, countOfPackages, scanner, threads, pingChecker);
        System.out.println("In progress...");
        joinThreads(threads);

        printSortedMap(mapOfIpAndAvarangeTime);

    }

    private static String getOSVersion() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "Windows";
        } else if (osName.contains("mac")) {
            return "Mac";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix") || osName.contains("hp-ux") || osName.contains("irix") || osName.contains("sunos")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

    private int validInput(String question, Scanner scanner){
        int num;
        while (true) {
            System.out.println("\n" + question);
            String input = scanner.nextLine();
            try {
                num = Integer.parseInt(input);
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void printSortedMap(Map<String, Double> map) {
        System.out.println("List size: " + map.size());
        if (!map.isEmpty()) {
            map.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .forEachOrdered(x -> System.out.print("\nPING: " + x.getKey() + " " + "AverangeTime: " + x.getValue()));
            System.out.println(" <- THIS IS MINIMUM");
        }
    }

    private void pingAddresses(int countOfDNS, int countOfPackages, Scanner scanner, List<Thread> threads, PingChecker pingChecker) {
        for (int i = 0; i < countOfDNS; i++) {
            System.out.println("Enter the DNS server address " + (i + 1) + ": ");
            String inputIP = scanner.nextLine();
            threads.add(new Thread(() -> {
                if (pingChecker.checkPingAvailable(inputIP)) {
                    mapOfIpAndAvarangeTime.put(inputIP, pingChecker.getAveragePingTime(inputIP, countOfPackages));
                } else {
                    System.out.println("\u001B[31mERROR. Incorrectly specified IP address: " + inputIP +"\u001B[0m");
                }
            }));
            threads.getLast().start();
        }
    }
    private static void joinThreads(List<Thread> threads) {
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
