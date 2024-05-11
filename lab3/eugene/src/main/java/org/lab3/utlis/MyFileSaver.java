package org.lab3.utlis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyFileSaver
{
    public static void save(Object object, String filename) {
        boolean isItGlobalHistory = filename.contains("globalHistory");

        String prefix = MyFileReader.findDir(new File(System.getProperty("user.dir")), "output");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(prefix + "\\" +  filename + ".txt", isItGlobalHistory))) {
            if (isItGlobalHistory) {
                String[] formattedDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()).split("_");
                bufferedWriter.write("Date: " + formattedDate[0] + "\nTime: " + formattedDate[1] + "\n");
            }
            if (object instanceof Map<?, ?>) {
                for (var i : ((Map<?, ?>) object).entrySet()) {
                    bufferedWriter.write(i.getKey().toString() + ": " + i.getValue().toString() + "\n");
                }
                bufferedWriter.newLine();
            } else if (object instanceof Iterable<?>) {
                for (var i : (Iterable) object) {
                    bufferedWriter.write(i.toString());
                    bufferedWriter.newLine();
                }
            } else if (object.getClass().isArray() && object instanceof String[][]) {
                for (String[] string : (String[][]) object) {
                    if (string[0] != null) {
                        bufferedWriter.write(string[0] + ": " + string[1]);
                        bufferedWriter.newLine();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
