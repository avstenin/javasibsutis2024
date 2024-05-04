package main.java.org.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingChecker
{
    private final boolean FLAG;

    public PingChecker(String osName) {
        FLAG = osName.toLowerCase().contains("win");
    }

    public boolean checkPingAvailable(String addr) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", FLAG ? "-n" : "-c", "1", addr);
            Process proc = processBuilder.start();
            int returnVal = proc.waitFor();
            return returnVal == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    private Double winSplit(String line){
        return Double.parseDouble(line.split("=")[2].replaceAll("\\D+", ""));
    }

    private Double otherSplit(String line){
        return Double.parseDouble(line.split("/")[4]);
    }

    public double getAveragePingTime(String ipAddress, int pingCount)
    {
        double averagePingTime = 0;
        ProcessBuilder processBuilder = new ProcessBuilder("ping", FLAG ? "-n" : "-c", String.valueOf(pingCount), ipAddress);
        try(BufferedReader r =
                    new BufferedReader(
                            new InputStreamReader(
                                    processBuilder
                                            .start()
                                            .getInputStream())))
        {

            String line, lastLine = "";
            while ((line = r.readLine()) != null) {
                lastLine = line;
            }
            averagePingTime = FLAG ? winSplit(lastLine) : otherSplit(lastLine);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return averagePingTime;
    }


}
