import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

//  Google          8.8.8.8         8.8.4.4
//  Quad9	        9.9.9.9	        149.112.112.112
//  OpenDNS Home	208.67.222.222	208.67.220.220
//  Cloudflare	    1.1.1.1         1.0.0.1
//  CleanBrowsing	185.228.168.9	185.228.169.9
//  Alternate DNS	76.76.19.19     76.223.122.150
//  AdGuard DNS	    94.140.14.14	94.140.15.15

public class lab2 {
    public static void main(String[] args)throws Exception{
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        String str;   
        double sum;
        //System.out.println("Введите количество DNS-серверов");
        ping test = new ping();
        //test.create_dir();
        if (test.n > 0) System.out.println("Введите последовательно " + test.n + " ip-адреса");
        for(int i = 0; i < test.n; i++){
            int count = i + 1;
            System.out.println("Введите " + count + "-й ip-адрес");
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
            System.out.println("Среднее время отклика - " + test.res[i]);     
        }
        test.buble_sort(test.res, test.ip);
        for (int i = 0; i < test.n; i++){
            System.out.println("Ip - " + test.ip[i] + " Среднее время отклика - " + test.res[i]);
            test.res_and_ip.put(test.ip[i], test.res[i]);        
            //System.out.println(test.res_and_ip);
            //test.write_to_file(test.res_and_ip, i);
        }

        test.transition(test.res, test.ip, test.n);

    }
}
class ping {
    Scanner input = new Scanner(System.in);
    
    //int n = input.nextInt();
    int n =3;   
    public HashMap <String, Double> res_and_ip = new HashMap<>();
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

    void transition(double [] mas1, String [] mas2, int n){
        Object [][] result = new Object[n][3];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 2; j++){
                if (j == 1){
                    result[i][j] = mas2[i];
                }
                if (j == 2){
                    result[i][j] = mas1[i];
                }
            }
        }
    }

    /*void create_dir(){
        try{
            ProcessBuilder build = new ProcessBuilder("sh","-c", "mkdir resourses");
            build.command();
            build.redirectErrorStream(true);
            build.start();
        } catch(IOException e) {
            e.printStackTrace();
        }     
    }*/

}
