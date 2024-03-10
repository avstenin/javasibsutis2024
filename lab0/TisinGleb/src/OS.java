import java.io.IOException;

abstract class OS{
    public double getAveragePingTime(){return averagePingTime;}
    public abstract double ping(String s) throws IOException;
    protected double averagePingTime;
}

