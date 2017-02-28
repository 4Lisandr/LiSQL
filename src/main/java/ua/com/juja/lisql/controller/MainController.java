package ua.com.juja.lisql.controller;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.connection.Connect;
import ua.com.juja.lisql.controller.command.read.DBList;
import ua.com.juja.lisql.controller.command.read.Find;
import ua.com.juja.lisql.controller.command.utils.*;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

/**
 *  Controller of commands
 */
public class MainController {
    private static View view;
    private static DatabaseManager manager;

    /**
     * Keep strict order while iterate list of command
     * Command Unknown contains greed condition - all other commands after it detected as Unknown!
     */
    private enum UsersCommands implements Command {
        CONNECT (new Connect(manager, view)),
        HELP    (new Help(view)),
        EXIT    (new Exit(manager, view)),
        LIST    (new DBList(manager, view)),
        FIND    (new Find(manager, view)),
//        CREATE(new Create(manager, view)),
//        UPDATE(new Update(manager, view)),
//        DELETE(new Delete(manager, view)),
//        INSERT(new Insert(manager, view)),
//        CLEAR(new Clear(manager, view)),
//        DROP (new Drop(manager, view)),
        UNKNOWN (new Unknown(view)); /* Greed condition - all other commands are Unknown!*/

        private final Command command;

        UsersCommands(Command command) {
            this.command = command;
        }

        public Command getCommand() {
            return command;
        }

        @Override
        public boolean canProcess(String strCommand) {
            return command.canProcess(strCommand);
        }

        @Override
        public void process(String strCommand) {
            command.process(strCommand);
        }

        private static void handler(String input) {
            for (Command command : values()) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    printError(e);
                    break;
                }
            }
        }


        private static void printError(Exception e) {
            String message = e.getMessage();
            Throwable cause = e.getCause();
            if (cause != null) {
                message += " " + cause.getMessage();
            }
            view.write(EMessage.FAIL + " " + message, EMessage.RETRY.toString());
        }
    }


    public MainController(View view, DatabaseManager manager) {
        MainController.view = view;
        MainController.manager = manager;
    }

    public void run() {
        if (!Start.isCalled()){
            new Start(view).process("");
        }

        while (true) {
            String input = view.read();
            UsersCommands.handler(input);
            if (!Close.isCalled()){// Ask user for next step
                view.write(EMessage.INPUT.toString(),"");
            }
            else
                break;
        }
    }

}
