import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Windows extends OS {
    @Override
    public double ping(String addr) throws IOException {
        sendPing(addr);
        getPingResult();
        printPingResult();
        return averagePingTime;
    }
    private void sendPing(String addr){
        String[] command = {"ping", "-n", "3", addr};
        try {
            process = new ProcessBuilder(command).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void getPingResult() throws IOException {
        String line, result;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ( (line = reader.readLine()) != null) {
            if (line.startsWith("Average =")){
                int start = line.indexOf("Average = ") + 10;
                int end = line.indexOf("ms", start);
                result = line.substring(start, end);
                this.averagePingTime = Double.parseDouble(result);
            }
        }
    }
    private void printPingResult(){
        System.out.println("Average time of ping of " + getAveragePingTime());
    }
    private Process process;
}
