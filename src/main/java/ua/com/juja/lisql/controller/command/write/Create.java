package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.CREATE;

public class Create extends Command {
    public Create(DatabaseManager manager, View view) {
        setTextBuilder(new TextBuilder("create|tableName|column1|column2|...|columnN",
                CREATE.toString()));
    }

    @Override
    protected void process(String command) {

    }
}
