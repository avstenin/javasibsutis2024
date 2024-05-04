package main.java.org.lab3.utlis;

import main.java.org.lab3.exceptions.FileException;
import main.java.org.lab3.exceptions.IpException;
import main.java.org.lab3.exceptions.ParseException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu
{
    private int count;
    private final String OS_NAME;
    private PingChecker pingChecker;
    private Scanner scanner;

    volatile Map<String, Double> mapOfIpAndAverageTime = new HashMap<>();



    public Menu()
    {
        this.scanner = new Scanner(System.in);
        this.OS_NAME = getOSVersion();
        this.pingChecker = new PingChecker(OS_NAME);
    }

    public void sayHello(){
        System.out.println("Hello, " + System.getProperty("user.name") + "!\nYour OS is -> " + OS_NAME);
    }

    public void sayBye(){
        System.out.println("Bye, " + System.getProperty("user.name") + "!");
    }

    public void userChoice(){



        String menuElement = "What you want to do?\n" +
                "1. Ping addresses\n" +
                "2. Check file from directory\n" +
                "3. Check global history\n" +
                "4. Exit";


        while(true)
        {
            switch (validInput(menuElement)){
                case 1: startPing(); addToGlobalHistory(); break;
                case 2: checkFileFromDirectory(); break;
                case 3: checkGlobalHistory(); break;
                case 4: sayBye(); return;
                default:
                    System.out.println("Error.");
            }
            MyFileReader.stringList.clear();
            mapOfIpAndAverageTime.clear();
        }
    }

    private void checkFileFromDirectory(){
        startRead("Out");
        MyFileReader.stringList.forEach(System.out::println);
    }

    private void checkGlobalHistory() {
        try
        {
            MyFileReader.readFile(new File(System.getProperty("user.dir") + "\\lab3\\eugene\\src\\main\\java\\org\\lab3\\bin\\output\\globalHistory"));
            MyFileReader.stringList.forEach(System.out::println);
        }
        catch (FileException e) {
            System.out.println("History not found");
        }

    }


    private void addToGlobalHistory(){
        if (!mapOfIpAndAverageTime.isEmpty()){
            MyFileSaver.save(mapOfIpAndAverageTime, "globalHistory");
        }
    }

    private void startRead(String regime){
        System.out.println("Enter directory for scan. If you want to choose current directory, just press ENTER");
        String directory = scanner.nextLine();
        try
        {
            MyFileReader.recursiveRead(regime, new File(Objects.equals(directory, "") ? System.getProperty("user.dir") : directory));
        }
        catch (FileException fileNotFoundException){
            System.out.println("Directory not found! Try again!");
        }

    }


    private void startPing()
    {
        List<Thread> threads = new ArrayList<>();
        String regimeOutput = "KeyboardOutput";
        if (validInput("1. From File\n2. From keyboard") == 1)
        {
            regimeOutput = "FileOutput";
            startRead("input");
            pingFromFileInput(threads);
        }
        else
        {
            int countOfDNS = validInput("How many addresses do you want to ping?");
            int countOfPackages = validInput("How many pings per address?");
            pingFromKeyboardInput(countOfDNS, countOfPackages, threads, pingChecker);

        }
        System.out.println("In progress...");
        joinThreads(threads);
        printSortedMap(mapOfIpAndAverageTime);

        if (!mapOfIpAndAverageTime.isEmpty() && validInput("Do you want to save your result?\n1. Yes\n2. No") == 1) {

            String formattedDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            String fileName = regimeOutput + "_" + formattedDate + count++ + ".txt";

            MyFileSaver.save(mapOfIpAndAverageTime, fileName);
        }
    }

    private void createPingThread(List<Thread> threads, String inputIP, int countOfPackages, PingChecker pingChecker) {
        threads.add(new Thread(() -> {
            try
            {
                if (pingChecker.checkPingAvailable(inputIP))
                {
                    mapOfIpAndAverageTime.put(inputIP, pingChecker.getAveragePingTime(inputIP, countOfPackages));
                }
            }
            catch (IpException e) {
                System.out.println("\u001B[31mERROR. Incorrectly specified IP address: " + inputIP +"\u001B[0m");
            }
        }));
        threads.get(threads.size() - 1).start();
    }

    private void pingFromFileInput(List<Thread> threads) throws ParseException
    {
        try{
            for (String line : MyFileReader.stringList) {
                if (line.contains("\n")) continue;
                var out = line.split(" ");
                createPingThread(threads, out[0], Integer.parseInt(out[1]), pingChecker);
            }
        }
        catch (ParseException e)
        {
            System.out.println("\u001BError. Change file format.\u001B[0m");
        }

    }

    private void pingFromKeyboardInput(int countOfDNS, int countOfPackages, List<Thread> threads, PingChecker pingChecker) {
        for (int i = 0; i < countOfDNS; i++) {
            System.out.println("Enter the DNS server address " + (i + 1) + ": ");
            String inputIP = scanner.nextLine();
            createPingThread(threads, inputIP, countOfPackages, pingChecker);
        }
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

    private int validInput(String question){
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
