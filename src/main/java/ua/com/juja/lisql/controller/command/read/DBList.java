package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.Arrays;
import java.util.List;

public class DBList extends Command {

    public DBList(DatabaseManager manager, View view){
        super(manager, view, true);
        setAttributes("list", "for view all databases", "success", "fail");
    }

    @Override
    public void process(String command) {

        List<String> tableNames = getManager().getTableNames();

        String message = Arrays.toString(tableNames.toArray());
        getView().write(message);
    }

}
