package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingParser {
    private final boolean isWindows;
    public PingParser(boolean isWindows){
        this.isWindows = isWindows;
    }
    /**
     *
     * @param reader
     * @return return double if found otherwise return -1
     */
    public double getResponseTime(BufferedReader reader) throws IOException {
        String line;
        Pattern pattern = isWindows ? Pattern.compile("=(\\d)*[^\\d\\s][^\\d\\s]"):
                                      Pattern.compile("=(\\d)*\\sms");
        Pattern digitPattern = Pattern.compile("(\\d)+");
        while ((line = reader.readLine()) != null) {
            try{
                String msFromLine = FindByPattern(pattern, line);
                String valueFromLine = FindByPattern(digitPattern, msFromLine);
                return Integer.parseInt(valueFromLine);
            }
            catch (Exception ex){}
        }
        return -1;
    }
    private String FindByPattern(Pattern pattern, String str){
        Matcher matcher;
        matcher = pattern.matcher(str);
        matcher.find();
        return matcher.group(0);
    }
}
