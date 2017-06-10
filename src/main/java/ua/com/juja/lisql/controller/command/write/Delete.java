package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

/**
 *  Delete record from database
 */
public class Delete extends Command {
    public Delete(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes(Message.DELETE);
    }

    @Override
    public void process(String command) {
        String[] table = validArguments(command, "delete|users|id=10");
        if (view.confirm()) {
            try {
                manager.delete(table[1], table[2].split("="));
            } catch (Exception e) {
                view.write(String.format(failure(0), table[1]));
            }
            view.write(String.format(success(), table[1]));
        } else
            view.write(failure(1));
    }
}
