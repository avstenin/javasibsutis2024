import tools.PingParser;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    private final static int SERVERS_COUNT = 3;
    private final static int PING_COUNT = 4;

    public static void main(String[] args) throws IOException {

        Map<String, Double> addresses = new HashMap<>();
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < SERVERS_COUNT; ++i){
            System.out.println("Enter the address of the DNS" + (i + 1) + " server");
            addresses.put(input.next(), 0.0);
        }

        String userFriendlyMessage = "Please wait";
        for(var address : addresses.keySet()){
            userFriendlyMessage += '.';
            System.out.println(userFriendlyMessage);
            ProcessBuilder builder = isWindows() ? new ProcessBuilder("cmd.exe", "/c", "ping " + address + " -n 1" ):
                                                   new ProcessBuilder("/bin/sh", "-c", "ping " + address + " -c 1");
            builder.redirectErrorStream(true);

            double averageResponse = 0;
            for(int j = 0; j < PING_COUNT; ++j){
                Process process = builder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                PingParser pingParser = new PingParser(isWindows());

                averageResponse += pingParser.getResponseTime(reader);
            }
            addresses.put(address, averageResponse / PING_COUNT);
        }

        Map<String, Double> sortedAddressesByValue = addresses.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                                                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                                (entry1, entry2) -> entry2, LinkedHashMap::new));

        Set<String> setKeys = sortedAddressesByValue.keySet();
        List<String> listKeys = new ArrayList<String>(setKeys);
        Collections.reverse(listKeys);

        System.out.println("Average response time:");
        for(var key : listKeys){
            if(addresses.get(key) < 0){
                System.out.println("Failed to apply ping for DNS: " + key);
                continue;
            }
            System.out.println(key + " - " + addresses.get(key));
        }
    }
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}