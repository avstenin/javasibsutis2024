package org.lab3.exceptions;

import java.io.IOException;

public class FileException extends Exception {
    public FileException(String message){
        super(message);
    }

    public FileException(String s, IOException e) {
        super(s, e);
    }
}
