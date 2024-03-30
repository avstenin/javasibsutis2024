import java.io.IOException;
import java.util.*;

public class Program {
    public static void main(String[] args) throws IOException {
        printInterface();
        var os = defineOS();
        requestFromConsole(os);
    }
    public static void printInterface(){
        System.out.println("""
                           1. Use preset(8.8.8.8 8.8.4.4 77.88.8.7)
                           2. Use own ping""");
    }
    public static void requestFromConsole(OS os) throws IOException {
        Scanner console = new Scanner(System.in);
        switch (console.nextInt()){
            case 1:
                ArrayList<PingInfo> pingInfo = new ArrayList<>();
                pingInfo.add(new PingInfo("8.8.8.8", os.ping("8.8.8.8")));
                pingInfo.add(new PingInfo("8.8.4.4", os.ping("8.8.4.4")));
                pingInfo.add(new PingInfo("77.88.8.7", os.ping("77.88.8.7")));
                //https://stackoverflow.com/questions/15326248/sort-an-array-of-custom-objects-in-descending-order-on-an-int-property
                pingInfo.sort(Comparator.comparingDouble(PingInfo::getAverageTime).reversed());
                System.out.println("Ping time | address");
                for (var item : pingInfo){
                    System.out.println(item.averageTime + "\t\t" + item.ip);
                }
                break;
            case 2:
                System.out.println("Print addr");
                console.nextLine();
                var i = console.nextLine();
                os.ping(i);
                break;
            default:
                System.out.println("Incorrect request");
        }
    }
    private static final String osName = System.getProperty("os.name");
    private static OS defineOS(){
        OS os = null;
        switch (osName){ //может откиснуть на винде или маке
            case "Linux":
                os = new Linux();
                break;
            case "Windows 10":
                os = new Windows();
                break;
            case "Mac OS X":
                os = new MacOS();
                break;
            default:
                System.out.println("Undefined Operating System");
                break;
        }
        return os;
    }
}

