package ua.com.juja.lisql.controller;

import ua.com.juja.lisql.controller.command.*;
import ua.com.juja.lisql.controller.command.connection.Connect;
import ua.com.juja.lisql.controller.command.connection.IsConnected;
import ua.com.juja.lisql.controller.command.utils.Exit;
import ua.com.juja.lisql.controller.command.utils.ExitException;
import ua.com.juja.lisql.controller.command.utils.Unknown;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

/**
 *  Controller of commands
 */
public class MainController {
    private View view;
    private Command[] commands;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[] {
                new Connect(manager, view),
                new IsConnected(manager, view),
//                new Help(view),
                new Exit(view),
//                new List(manager, view),
//                new Clear(manager, view),
//                new Create(manager, view),
//                new Find(manager, view),
                new Unknown(view)};

    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }
    }

    private void doWork() {

        view.write(Message.HELLO.toString(), Message.START.toString());

        while (true) {
            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write(Message.INPUT.toString(),"");
        }
    }


    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write(Message.FAIL + " "+ message, Message.RETRY.toString());
    }
}
