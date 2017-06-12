package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.UPDATE;


public class Update extends Command {

    public Update(DatabaseManager manager, View view) {
        super(manager, view);
        setTextBuilder(new TextBuilder("update|database", UPDATE.toString()));
        hide();
    }

    @Override
    public void process(String command) {

    }
}
