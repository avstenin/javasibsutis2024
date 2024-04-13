import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//  Google          8.8.8.8         8.8.4.4
//  Quad9	        9.9.9.9	        149.112.112.112
//  OpenDNS Home	208.67.222.222	208.67.220.220
//  Cloudflare	    1.1.1.1         1.0.0.1
//  CleanBrowsing	185.228.168.9	185.228.169.9
//  Alternate DNS	76.76.19.19     76.223.122.150
//  AdGuard DNS	    94.140.14.14	94.140.15.15

public class lab2 {
  public static void main(String[] args) throws Exception {
    @SuppressWarnings("resource") Scanner input = new Scanner(System.in);
    String str;
    double sum;
    System.out.println("Введите количество DNS-серверов");
    ping test = new ping();
    test.create_dir();
    if (test.n > 0)
      System.out.println("Введите последовательно " + test.n + " ip-адреса");
    for (int i = 0; i < test.n; i++) {
      int count = i + 1;
      System.out.println("Введите " + count + "-й ip-адрес");
      sum = 0;
      test.result[i][0] = input.next();
      ProcessBuilder builder =
          new ProcessBuilder("sh", "-c", "ping " + test.result[i][0] + " -c 3");
      builder.command();
      builder.redirectErrorStream(true);
      Process proc = builder.start();
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(proc.getInputStream()));
      while (true) {
        str = (reader.readLine());
        if (str == null)
          break;
        if (str.contains("time=")) {
          int start_ind = str.indexOf("time=") + 5;
          int end_ind = str.indexOf("ms");
          String buf = str.substring(start_ind, end_ind);
          sum += Double.parseDouble(buf);
        }
      }
      test.result[i][1] = sum / test.ping_count;
      System.out.println("Среднее время отклика - " + test.result[i][1]);
      test.write_to_file(i);
    }
    test.scan_dir(test.name_dir);
    //       System.out.println("numb files - "+ test.files.length);
    //                System.out.println("numb files - "+ test.numb_files);

    test.read_from_file();

    if (test.numb_files > 1)
      test.buble_sort();

    test.print_mass_info(test.mass_from_file);
  }
}
class ping {
  Scanner input = new Scanner(System.in);
  int n = input.nextInt();
  int ping_count = 3;
  int numb_files;
  String name_dir = "./resourses";
  File[] files;
  Object[][] result = new Object[n][2];
  Object[][] mass_from_file;
  public double[] res;
  public String[] ip;

  void buble_sort() {
    res = new double[numb_files];
    ip = new String[numb_files];
    for (int i = 0; i < numb_files; i++) {
      ip[i] = (String)mass_from_file[i][0];
      res[i] = (Double.valueOf((String)(mass_from_file[i][1])));
    }
    for (int i = 0; i < numb_files; i++) {
      for (int j = 0; j < 2; j++) {
        if (res[i] < res[j]) {
          double tmp = res[i];
          res[i] = res[j];
          res[j] = tmp;
          String tmp_ip = ip[i];
          ip[i] = ip[j];
          ip[j] = tmp_ip;
        }
      }
    }
    for (int i = 0; i < numb_files; i++) {
      mass_from_file[i][1] = res[i];
      mass_from_file[i][0] = ip[i];
    }
  }

  void create_dir() {
    try {
      ProcessBuilder build = new ProcessBuilder("sh", "-c", "mkdir resourses");
      build.command();
      build.redirectErrorStream(true);
      build.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void scan_dir(String name) {
    File dir = new File(name);
    files = dir.listFiles();
    numb_files = files.length;
  }

  void write_to_file(int j) {
    Date data = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dataString = sdf.format(data);
    try {
      FileWriter writer = new FileWriter("./resourses/" + dataString, false);
      writer.write(result[j][0].toString() + ' ' + result[j][1].toString() +
                   '\n');
      writer.flush();
      writer.close();
    } catch (IOException e) {
      System.err.println();
    }
  }

  void read_from_file() {
    mass_from_file = new Object[numb_files][2];
    for (int i = 0; i < numb_files; i++) {
      try (FileReader reader = new FileReader(files[i])) {
        int f;
        int j = 0;
        String str = new String();
        while ((f = reader.read()) != -1) {
          if ((char)f == ' ') {
            mass_from_file[i][j] = str;
            str = "";
            j = 1;
          } else if ((char)f == '\n') {
            mass_from_file[i][j] = str;
            j = 0;
          } else {
            str = str + (char)f;
          }
        }
        reader.close();
      } catch (IOException e) {
        System.err.println();
      }
    }
  }

  void print_mass_info(Object mass[][]) {
    for (int i = 0; i < numb_files; i++) {
      System.out.println("Ip - " + mass[i][0] + " Среднее время отклика - " +
                         mass[i][1]);
    }
  }
}
