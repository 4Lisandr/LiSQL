package ua.com.juja.lisql.controller;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.connection.Connect;
import ua.com.juja.lisql.controller.command.read.Find;
import ua.com.juja.lisql.controller.command.read.TablesList;
import ua.com.juja.lisql.controller.command.utils.*;
import ua.com.juja.lisql.controller.command.write.*;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static ua.com.juja.lisql.controller.command.TextBundle.*;

/**
 * Controller of commands, singleton
 */
public final class Controller {
    private static Controller instance;
    private static View view;
    private static DatabaseManager manager;

    private Controller(View view, DatabaseManager manager) {
        Controller.view = view;
        Controller.manager = manager;
        instance = this;
    }

    public static Controller getInstance(View view, DatabaseManager manager) {
        return instance == null ?
                new Controller(view, manager) :
                instance;
    }

    public void run() {
        new Start(view).process("");

        while (!Close.isCalled()) {
            view.write(INPUT.toString());
            UsersCommand.handle(view.read());
        }
    }


    public enum UsersCommand {
        CONNECT(new Connect(manager, view)),
        HELP(new Help(view)),
        EXIT(new Exit(manager, view)),
        LIST(new TablesList(manager, view)),
        FIND(new Find(manager, view)),
        INSERT(new Insert(manager, view)),
        UPDATE(new Update(manager, view)),
        CLEAR(new Clear(manager, view)),
        DELETE(new Delete(manager, view)),
        CREATE(new Create(manager, view)),
        DROP(new Drop(manager, view)),
        UNKNOWN(new Unknown(view)); /* Greed condition - all other commands are Unknown!*/

        private final Command command;

        UsersCommand(Command command) {
            this.command = command;
        }

        public Command getCommand() {
            return command;
        }


        private static void handle(String input) {
            try {
                getAll().stream()
                        .filter(c -> c.runIfReady(input))
                        .findFirst();
            } catch (Exception e) {
                printError(e);
            }
        }

        public static List<Command> getAll() {
            ArrayList<Command> result = new ArrayList<>();
            Stream.of(values())
                    .filter(Objects::nonNull)
                    .forEach(usr -> result.add(usr.getCommand()));
            return result;
        }

        // Принтерор - перенести во вьюху?
        private static void printError(Exception e) {
            String message = e.getMessage();
            view.write(FAIL + " ", message, ". " + RETRY);
        }


        public static void main(String[] arg) {

        }
    }
}
