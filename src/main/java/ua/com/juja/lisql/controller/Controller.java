package ua.com.juja.lisql.controller;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.connection.*;
import ua.com.juja.lisql.controller.command.read.*;
import ua.com.juja.lisql.controller.command.write.*;
import ua.com.juja.lisql.controller.command.utils.*;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Controller of commands, singleton
 */
public final class Controller {
    private static Controller instance;
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
        CLEAR(new Clear(manager, view)),
//        DELETE(new Delete(manager, view)),
//        INSERT(new Insert(manager, view)),
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
                    if (command.run(input))
                        break;
                } catch (Exception e) {
                    printError(e);
                    break;
                }
            }
        }
    }

    private Controller(View view, DatabaseManager manager) {
        Controller.view = view;
        Controller.manager = manager;
        instance = this;
    }

    public static Controller getInstance(View view, DatabaseManager manager) {
        return (instance==null)?
                new Controller(view, manager):
                instance;
    }

    public void run() {
        new Start(view).process("");

        while (!Close.isCalled()) {
            view.write(Message.INPUT.toString(),"");
            String input = view.read();
            UsersCommand.handler(input);
        }

    }

    private static void printError(Exception e) {
        String message = e.getMessage();
        view.write(Message.FAIL+" ", message,". "+Message.RETRY);
    }

}
