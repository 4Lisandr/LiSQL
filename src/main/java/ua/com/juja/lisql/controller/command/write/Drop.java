package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Drop extends Command {
    public Drop(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes(Message.DROP);
    }

    @Override
    public void process(String command) {
        String[] table = validArguments(command, "drop|users");
        if (view.confirm()) {
            try {
                manager.drop(table[1]);
            } catch (Exception e) {
                view.write(String.format(failure(0), table[1]));
            }
            view.write(String.format(success(), table[1]));
        } else
            view.write(failure(1));
    }
}
