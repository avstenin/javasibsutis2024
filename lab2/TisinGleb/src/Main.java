import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void main(String[] args) throws IOException {
        var os = DefineOS.defineOS();
        int pingCount = 0;
        ArrayList<String> data = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        pingCount = getPingCount(in, pingCount);
        getDNSServerNames(in, data);
        in.close();
        System.out.println(ANSI_PURPLE + "Please stand by" + ANSI_RESET);
        String [][] pings = new String[data.size()][pingCount + 1];

        pingDNS(data, pings, pingCount, os);

        createFileWithPingInfo(pings);
    }

    private static void createFileWithPingInfo(String[][] pings) throws IOException {
        FileWriter fileWriter = new FileWriter("lab2/TisinGleb/pings.txt");
        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (String[] row : pings) {
                for (String value : row) {
                    writer.write(value + "\t");
                }
                writer.newLine();
            }
        }
        System.out.println("Data has been written to the file 'pings.txt'");
    }

    private static void pingDNS(ArrayList<String> data, String[][] pings, int pingCount, OS os) {
        for (int i = 0; i < data.size(); ++i)
        {
            pings[i][0] = data.get(i);
            for (int j = 1; j < pingCount + 1; ++j)
            {
                try{
                    InetAddress ipAddress = InetAddress.getByName(pings[i][0]);
                    String ipStr = ipAddress.getHostAddress();
                    var ip = new PingInfo(ipStr, os.ping(ipStr));
                    pings[i][j] = String.valueOf(ip.time);
                } catch (IOException e){
                    System.err.println("Ping error " + pings[i][0] + "is unreachable");
                }
            }
        }
    }

    private static void getDNSServerNames(Scanner in, ArrayList<String> data) {
        System.out.println(ANSI_PURPLE + "Input the number of DNS servers" + ANSI_RESET);
        int pingCount = 0;
        if (in.hasNextInt())
        {
            pingCount = in.nextInt();
            in.nextLine();
        }

        System.out.println(ANSI_PURPLE + "Input website names OR q for stop" + ANSI_RESET);
        int iteration = 0;
        while (iteration < pingCount) {
            if (in.hasNextLine()) {
                String input = in.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
                data.add(input);
            } else break;
            ++iteration;
        }
    }

    private static int getPingCount(Scanner in, int pingCount) {
        System.out.println(ANSI_PURPLE + "Input the number of pings that will be sent to the server(s)" + ANSI_RESET);
        if (in.hasNextInt())
        {
            pingCount = in.nextInt();
            in.nextLine();
        }
        return pingCount;
    }
}