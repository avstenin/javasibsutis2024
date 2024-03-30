import java.io.IOException;

public abstract class OS{
    public double getAveragePingTime(){return averagePingTime;}
    public abstract double ping(String s) throws IOException;
    protected double averagePingTime;
}

