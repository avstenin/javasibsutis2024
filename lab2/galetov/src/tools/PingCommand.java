package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingCommand {
    public static BufferedReader getReaderFromAddress(String address) throws IOException {
        ProcessBuilder builder = isWindows() ? new ProcessBuilder("cmd.exe", "/c", "ping " + address + " -n 1") :
                new ProcessBuilder("/bin/sh", "-c", "ping " + address + " -c 1");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        PingParser pingParser = new PingParser(isWindows());

        return reader;
    }

    public static double getAverageResponseFromAddress(String address, int count) throws IOException {
        BufferedReader reader;
        PingParser pingParser = new PingParser(isWindows());
        double averageTime = 0;
        for (int i = 0; i < count; ++i) {
            reader = getReaderFromAddress(address);
            averageTime += pingParser.getResponseTime(reader);
        }
        return averageTime / count;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}
