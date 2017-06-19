package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.controller.command.TextBundle;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
        setTextBuilder(new TextBuilder("clear|users", TextBundle.CLEAR.toString(),
                "now table %s is empty", "Couldn't clear table %s", "operation is canceled"));
    }

    @Override
    public void process(String command) {
        String[] args = validArguments(command);
        String table = args[TextBuilder.SAMPLE_TABLE];

        if (view.confirm()) {
            try {
                manager.clear(table);
            } catch (Exception e) {
                view.write(String.format(failure(0), table));
            }
            view.write(String.format(success(), table));
        } else
            view.write(failure(1));
    }
}
