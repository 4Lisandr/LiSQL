package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Update extends Command {

    public Update(DatabaseManager manager, View view) {
        super(manager, view, CONNECTION_REQUIRED);
        setAttributes("update|");
        hide();
    }

    @Override
    public void process(String command) {

    }
}
