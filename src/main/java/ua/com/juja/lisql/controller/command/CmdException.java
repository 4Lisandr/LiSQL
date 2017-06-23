package ua.com.juja.lisql.controller.command;

public class CmdException extends RuntimeException {
    public CmdException(String message, Throwable cause) {
        super(message, cause);
    }
}
