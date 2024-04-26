package main.java.org.lab2.utlis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class MyFileSaver
{
    public static void save(Object object, String filename){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename)))
        {
            if (object instanceof Map<?,?>){
                for (var i : ((Map<?, ?>) object).entrySet()){
                    bufferedWriter.write(i.getKey().toString() + ": " + i.getValue().toString());
                    bufferedWriter.newLine();
                }
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
                        bufferedWriter.write(string[0] + " " + string[1]);
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
