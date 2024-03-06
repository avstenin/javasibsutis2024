import tools.PingCommand;
import tools.PingParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("How many addresses do you want to enter?");
        int dnsCount;
        try{
            dnsCount = Integer.parseInt(input.next());
        } catch (Exception ex){
            System.out.println("Uncorrect input! Expected: integer");
            return;
        }

        String address = "mail.ru";
        BufferedReader reader = PingCommand.getReaderFromAddress(address);
        PingParser pingParser = new PingParser(PingCommand.isWindows());
        double time = pingParser.getResponseTime(reader);
        System.out.println(dnsCount);
    }
}
