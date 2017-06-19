package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.LIST;

public class TablesList extends Command {

    public TablesList(DatabaseManager manager, View view) {
        super(manager, view);
        setTextBuilder(new TextBuilder("list", LIST.toString()));
    }

    @Override
    public void process(String command) {
        view.write(manager.getTableNames().toString());
    }

}
