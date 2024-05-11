public class PingInfo{
    PingInfo(String ip, double time){
        //нет инварианта
        this.ip = ip;
        this.time = time;
    }
    public String ip;
    public double time;
    public double getAverageTime(){return time;}
}
