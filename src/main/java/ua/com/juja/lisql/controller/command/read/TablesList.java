package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.LIST;

public class TablesList extends Command {

    public TablesList(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("list", LIST.toString()));
    }

    @Override
    public void process(String command) {
        view.write(manager.getTableNames().toString());
    }

}
