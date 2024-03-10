public class PingInfo{
    PingInfo(String ip, double time){
        //нет инварианта
        this.ip = ip;
        this.averageTime = time;
    }
    public String ip;
    public double averageTime;
    public double getAverageTime(){return averageTime;}
}
