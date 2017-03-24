package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("clear|users", "clear table users", "now table is empty", "canceled");
        hide();
    }

    @Override
    public void process(String command) {
        String[] arg = validArgs(command, "clear|users");

        if (confirm())
            manager.clear(arg[1]);
        else
            view.write("Cancelled!");
    }

    private boolean confirm() {
        view.write("Are you sure (Y/N)?");
        return view.read().equals("Y");
    }
}
