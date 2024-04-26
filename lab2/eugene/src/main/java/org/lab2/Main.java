package main.java.org.lab2;



import main.java.org.lab2.utlis.MyFileReader;
import main.java.org.lab2.utlis.MyFileSaver;
import main.java.org.lab2.utlis.PingChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main
{

    public static void main(String[] args)
    {
        PingChecker pingChecker = new PingChecker("windows");
        Map<String, Double> map = new HashMap<>();
        String[][] strings = new String[5][2];
        strings[0][0] = "5.5.5.5";
        strings[0][1] = "55";
        strings[1][0] = "5.5.5.5";
        strings[1][1] = "54";


        int cnt = 0;
        map.put("8.8.8.8", pingChecker.getAveragePingTime("8.8.8.8", 5));
        map.put("1.1.1.1", pingChecker.getAveragePingTime("1.1.1.1", 5));
        MyFileSaver.save(map, "PingAverageTime" + cnt++);
        MyFileSaver.save(strings, "PingAverageTime" + cnt++);

        MyFileReader.recursiveRead(new File(System.getProperty("user.dir")));

    }

}
