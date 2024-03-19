import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class lab1 {
    public static void main(String[] args)throws Exception{
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        String str;   
        double sum;
        ping test = new ping();
        for(int i = 0; i < test.n; i++){
            sum = 0;
            test.ip[i] = input.next();
            ProcessBuilder builder = new ProcessBuilder("sh", "-c", "ping " + test.ip[i] +" -c 3");
            builder.command();
            builder.redirectErrorStream(true);
            Process proc = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while(true){
                str = (reader.readLine());
                if(str == null) break;
                if(str.contains("time=")){
                    int start_ind = str.indexOf("time=") + 5;
                    int end_ind = str.indexOf("ms");
                    String buf = str.substring(start_ind, end_ind);
                    sum += Double.parseDouble(buf);
                }
            }
            test.res[i] = sum / test.n;
            System.out.println("res - " + test.res[i]);
        }
        test.buble_sort(test.res, test.ip);
        for (int i = 0; i < test.n; i++){
            System.out.println("ip - " + test.ip[i] + " time - " + test.res[i]);
        }
    }
}
class ping {
    int n = 3;
    public double [] res = new double[n];
    public String [] ip = new String[n];
    void buble_sort(double [] res, String [] ip){
        for(int i = 0; i < n; i ++){
            for(int j = 0; j < n; j ++){
                if (res[i] < res[j]){
                    double tmp = res[i];                
                    res[i] = res[j];
                    res[j] = tmp;
                    String tmp_ip = ip[i];                
                    ip[i] = ip[j];
                    ip[j] = tmp_ip;
                }         
            }
        }
    }    
}
