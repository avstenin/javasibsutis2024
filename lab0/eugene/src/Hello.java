import java.io.DataOutputStream;
import java.io.OutputStream;

public class Hello {
    public static void main(String[] args)
    {
        try(OutputStream outputStream = new DataOutputStream(System.out))
        {
            outputStream.write("Hello world".getBytes());
        }
        catch (Exception ignored){
        }
    }
}
