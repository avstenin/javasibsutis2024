package exceptions;

public class EmptyOutDirectoryException extends Exception{
    public EmptyOutDirectoryException() {
        super("Empty history directory");
    }
}
