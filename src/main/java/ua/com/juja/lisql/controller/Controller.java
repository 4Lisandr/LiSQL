package ua.com.juja.lisql.controller;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.connection.UserConnect;
import ua.com.juja.lisql.controller.command.read.DBList;
import ua.com.juja.lisql.controller.command.read.Find;
import ua.com.juja.lisql.controller.command.utils.*;
import ua.com.juja.lisql.controller.command.write.Create;
import ua.com.juja.lisql.controller.command.write.Update;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 *  Controller of commands
 */
public class Controller {
    private static View view;
    private static DatabaseManager manager;

    /**
     * Keep strict order while iterate list of command
     * Command Unknown contains greed condition - all other commands after it detected as Unknown!
     */
    public enum UsersCommand {
        CONNECT (new UserConnect(manager, view)),
        HELP    (new Help(view)),
        EXIT    (new Exit(manager, view)),
        LIST    (new DBList(manager, view)),
        FIND    (new Find(manager, view)),
        CREATE  (new Create(manager, view)),
        UPDATE  (new Update(manager, view)),
//        DELETE(new Delete(manager, view)),
//        INSERT(new Insert(manager, view)),
//        CLEAR(new Clear(manager, view)),
//        DROP (new Drop(manager, view)),
        UNKNOWN (new Unknown(view)); /* Greed condition - all other commands are Unknown!*/

        private final Command command;

        UsersCommand(Command command) {
            this.command = command;
        }

        public Command getCommand() {
            return command;
        }

        public static List<Command> getAll() {
            ArrayList<Command> result = new ArrayList<>();
            for (UsersCommand usr : values()) {
                if (usr!=null)
                    result.add(usr.getCommand());
            }
            return result;
        }

        private static void handler(String input) {
            for (Command command : getAll()) {
                try {
                    if (command.canProcess(input)) {
                        command.run(input);
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


    public Controller(View view, DatabaseManager manager) {
        Controller.view = view;
        Controller.manager = manager;
    }

    public void run() {
        if (!Start.isCalled()){
            new Start(view).process("");
        }

        while (true) {
            String input = view.read();
            UsersCommand.handler(input);
            if (!Close.isCalled()){// Ask user for next step
                view.write(EMessage.INPUT.toString(),"");
            }
            else
                break;
        }
    }

}