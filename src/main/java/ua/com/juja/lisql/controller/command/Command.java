package ua.com.juja.lisql.controller.command;

/**
 *  Standard Command Interface
 */
public interface Command {

    boolean canProcess(String command);

    void  process(String command);
}
