package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.ACommand;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.Arrays;

public class List extends ACommand {

    public List(DatabaseManager manager, View view){
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("list");
    }

    @Override
    public void process(String command) {
        String[] tableNames = getManager().getTableNames();

        String message = Arrays.toString(tableNames);

        getView().write(message);
    }
}
