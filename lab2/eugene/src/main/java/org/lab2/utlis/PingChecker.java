package main.java.org.lab2.utlis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class PingChecker
{
    private final boolean FLAG;

    public PingChecker(String osName) {
        FLAG = osName.toLowerCase().contains("win");
    }

    public boolean checkPingAvailable(String addr){
        try
        {
            InetAddress address = InetAddress.getByName(addr); // на linux или macOS запускать с SUDO!!
            return address.isReachable(3000);
        } catch (IOException exc){
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
        ProcessBuilder processBuilder = FLAG ?
                new ProcessBuilder("cmd.exe", "/c", "ping", ipAddress, "-n", String.valueOf(pingCount))
                :
                new ProcessBuilder("ping", ipAddress, "-c", String.valueOf(pingCount));

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
