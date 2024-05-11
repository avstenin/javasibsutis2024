import org.junit.Assert;
import org.junit.Test;
import org.lab3.exceptions.FileException;
import org.lab3.exceptions.IpException;
import org.lab3.utlis.MyFileReader;
import org.lab3.utlis.MyFileSaver;
import org.lab3.utlis.PingChecker;

import java.io.File;
import java.util.HashMap;

public class Lab3Test {
    @Test(expected = IpException.class)
    public void wrongIP() throws IpException {
        PingChecker pingChecker = new PingChecker(System.getProperty("os.name"));
        pingChecker.checkPingAvailable("wrongIP");
    }

    @Test
    public void validIP() throws IpException {
        PingChecker pingChecker = new PingChecker(System.getProperty("os.name"));
        boolean result = pingChecker.checkPingAvailable("1.1.1.1");
        Assert.assertTrue(result);
    }

    @Test
    public void CorrectPingTimeCalculation(){
        PingChecker pingChecker = new PingChecker(System.getProperty("os.name"));
        double result = pingChecker.getAveragePingTime("1.1.1.1", 1);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void IncorrectPingTimeCalculation(){
        PingChecker pingChecker = new PingChecker(System.getProperty("os.name"));
        double result = pingChecker.getAveragePingTime("beliberda", 1);
        Assert.assertEquals(-1, result, 0.0);
    }

    @Test
    public void IncorrectSaveFile() throws FileException {
        MyFileSaver.save(new Object(),"testOutputFile");
        MyFileReader.recursiveRead("testOutput", new File(System.getProperty("user.dir")));
        Assert.assertEquals(1, MyFileReader.stringList.size());
        MyFileReader.stringList.clear();
    }


    @Test
    public void CorrectSaveFile() throws FileException {
        HashMap<String, Double> testMap = new HashMap<>();
        testMap.put("8.8.8.8", 10.0);
        testMap.put("1.1.1.1", 15.0);
        MyFileSaver.save(testMap,"testOutputFile");
        MyFileReader.recursiveRead("output", new File(System.getProperty("user.dir")));
        Assert.assertFalse(MyFileReader.stringList.isEmpty());
        MyFileReader.stringList.clear();
    }

}
