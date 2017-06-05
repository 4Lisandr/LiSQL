package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.controller.command.Message;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes(Message.CLEAR);
    }

    @Override
    public void process(String command) {
        String[] table = validArguments(command, "clear|users");

        if (view.confirm()) {
            try {
                manager.clear(table[1]);
            } catch (Exception e) {
                view.write(String.format(failure(0), table[1]));
            }
            view.write(String.format(success(), table[1]));
        } else
            view.write(failure(1));
    }
}
