package org.example;

import java.util.*;

public class Main
{
    private static volatile Map<String, Double> mapOfIpAndAvarangeTime = new HashMap<>();

    public static void main(String[] args)
    {
        System.out.println("\u001B[31mPlease note: This program may require administrative privileges to perform certain actions. USE SUDO.\u001B[0m");

        List<Thread> threads = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String osVersion = getOSVersion();
        PingChecker pingChecker = new PingChecker(osVersion);

        System.out.println("Hello, " + System.getProperty("user.name") + "!\nYour OS is -> " + osVersion);

        int countOfDNS = validInput("How many addresses do you want to ping?", scanner);
        int countOfPackages = validInput("How many pings per address?",scanner);

        pingAddresses(countOfDNS, countOfPackages, scanner, threads, pingChecker);

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

    private static int validInput(String question, Scanner scanner){
        int count;
        while (true) {
            System.out.println("\n" + question);
            String input = scanner.nextLine();
            try {
                count = Integer.parseInt(input);
                return count;
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

    private static void pingAddresses(int countOfDNS, int countOfPackages, Scanner scanner, List<Thread> threads, PingChecker pingChecker) {
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
