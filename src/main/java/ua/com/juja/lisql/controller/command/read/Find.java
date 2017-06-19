package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.*;

import static ua.com.juja.lisql.controller.command.TextBundle.FIND;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("find|sample", FIND.toString()));
    }

    @Override
    public void process(String command) {

        String tableName = validArguments(command)[Content.SAMPLE_TABLE];

        List<List<?>> table = new ArrayList<>();
        List<String> tableColumns = manager.getTableColumns(tableName);
        List<DataSet> tableData = manager.getTableData(tableName);
        table.add(tableColumns);
        putData(tableData, table);

        view.write(table);
    }

    private void putData(List<DataSet> tableData, List<List<?>> table) {
        for (DataSet row : tableData) {
            table.add(row.getValues());
        }
    }

}
