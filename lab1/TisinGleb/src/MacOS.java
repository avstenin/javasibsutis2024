import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MacOS extends OS {
    @Override
    public double ping(String addr) throws IOException {
        sendPing(addr);
        getPingResult();
        printPingResult();
        return averagePingTime;
    }
    private void sendPing(String addr){
        String[] command = {"ping", "-c", "3", addr};
        try {
            process = new ProcessBuilder(command).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void getPingResult() throws IOException {
        String line, result;
        String[] pingInfo;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ( (line = reader.readLine()) != null) {
            if (line.startsWith("round-trip")){
                pingInfo = line.substring(line.indexOf("=") + 2).split("/");
                result = pingInfo[1];
                this.averagePingTime = Double.parseDouble(result);
            }
        }
    }
    private void printPingResult(){
        System.out.println("Average time of ping of " + getAveragePingTime());
    }
    private Process process;
}
