package main.java.org.lab3.utlis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyFileSaver
{
    public static void save(Object object, String filename)
    {

        filename = System.getProperty("user.dir") + "\\lab3\\eugene\\src\\main\\java\\org\\lab3\\bin\\output\\" + filename;

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true)))
        {
            if (object instanceof Map<?,?>)
            {
                if (filename.contains("globalHistory")){
                    String[] formattedDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()).split("_");
                    bufferedWriter.write("Date: " + formattedDate[0] + "\nTime: " + formattedDate[1] + "\n");
                }
                for (var i : ((Map<?, ?>) object).entrySet())
                {
                    bufferedWriter.write(i.getKey().toString() + ": " + i.getValue().toString() + "\n");
                }
                bufferedWriter.newLine();
            }
            else if (object instanceof Iterable<?>){
                for (var i : (Iterable) object){
                    bufferedWriter.write(i.toString());
                    bufferedWriter.newLine();
                }
            }
            else if (object.getClass().isArray() && object instanceof String[][]){
                for (String[] string : (String[][]) object) {
                    if (string[0] != null) {
                        bufferedWriter.write(string[0] + ": " + string[1]);
                        bufferedWriter.newLine();
                    }

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
