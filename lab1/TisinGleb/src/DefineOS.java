import java.util.logging.Logger;
public class DefineOS {
    private DefineOS() {
        throw new IllegalStateException("Utility class");
    }
    private static final String OS_NAME = System.getProperty("os.name");
    public static OS defineOS(){
        OS os = null;
        switch (OS_NAME){
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
                Logger logger = Logger.getLogger(DefineOS.class.getName());
                logger.info("Undefined operating system");
                break;
        }
        return os;
    }
}
