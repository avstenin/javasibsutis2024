package tools;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileManager {
    public static void MapToJson(Map map) {

    }

    public static void ReadJsonDirectory(final File folder, List<String> files){
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ReadJsonDirectory(fileEntry, files);
            } else {
                files.add(fileEntry.getName());
            }
        }
    }
}
