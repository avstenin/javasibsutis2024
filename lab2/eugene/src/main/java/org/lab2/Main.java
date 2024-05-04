package main.java.org.lab2;


import main.java.org.lab2.utlis.Menu;
import main.java.org.lab2.utlis.MyFileReader;
import main.java.org.lab2.utlis.MyFileSaver;
import main.java.org.lab2.utlis.PingChecker;

import java.io.File;
import java.util.ArrayList;

public class Main
{

    private static int cnt = 0;

    public static void main(String[] args)
    {

        Menu menu = new Menu();
        menu.sayHello();
        menu.userChoice();

        testForArray();
        //testForArrayList();
    }


    private static void  testForArray(){

            System.out.println("WAIT.... THIS IS TEST FOR ARRAY");
            PingChecker pingChecker = new PingChecker(System.getProperty("os.name").toLowerCase());
            String[][] strings = new String[5][2];
            strings[0][0] = "8.8.8.8";
            strings[0][1] = String.valueOf(pingChecker.getAveragePingTime("8.8.8.8", 5));
            strings[1][0] = "1.1.1.1";
            strings[1][1] = String.valueOf(pingChecker.getAveragePingTime("1.1.1.1", 5));

            MyFileSaver.save(strings, "PingAverageTimeTest" + cnt++);
            MyFileReader.recursiveRead(new File(System.getProperty("user.dir")));

    }


    private static void testForArrayList()
    {
            System.out.println("WAIT.... THIS IS TEST FOR ARRAY List");
            PingChecker pingChecker = new PingChecker("windows");

            ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

            ArrayList<String> row1 = new ArrayList<>();
            row1.add("8.8.8.8");
            row1.add(String.valueOf(pingChecker.getAveragePingTime("8.8.8.8", 5)));
            arrayList.add(row1);

            ArrayList<String> row2 = new ArrayList<>();
            row2.add("1.1.1.1");
            row2.add(String.valueOf(pingChecker.getAveragePingTime("1.1.1.1", 5)));
            arrayList.add(row2);

            MyFileSaver.save(arrayList, "PingAverageTimeTest" + cnt++);
            MyFileReader.recursiveRead(new File(System.getProperty("user.dir")));
    }



}
