package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.Content.SAMPLE_TABLE;
import static ua.com.juja.lisql.controller.command.TextBundle.DELETE;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;

public class Delete extends Command {
    public Delete(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("delete|users|id=10", DELETE.toString(),
                "row is delete", FAIL.toString()));
    }

    @Override
    public void process(String command) {
        String[] args = validArguments(command);
        if (view.confirm()) {
            String table = args[SAMPLE_TABLE];
            String condition = args[SAMPLE_TABLE+1];
            try {
                manager.delete(table, condition.split("="));
            } catch (Exception e) {
                view.write(String.format(failure(0), table));
            }
            view.write(String.format(success(), table));
        } else
            view.write(failure(1));
    }
}
