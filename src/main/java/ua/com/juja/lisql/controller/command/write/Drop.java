package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.DROP;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;

/**
 *
 */
public class Drop extends Command {
    public Drop(DatabaseManager manager, View view) {
        super(manager, view);
        setTextBuilder(new TextBuilder("drop|users", DROP.toString(),
        "database deleted", FAIL.toString(), "operation is canceled"));
    }

    @Override
    public void process(String command) {
        String[] table = validArguments(command);
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
