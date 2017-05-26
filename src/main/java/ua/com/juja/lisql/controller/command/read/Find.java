package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

import java.util.List;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes(Message.FIND);
    }

    @Override
    public void process(String command) {

        String tableName = validArgs(command, "find|sample")[1];

        List<String> tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        List<DataSet> tableData = manager.getTableData(tableName);
        printTable(tableData);
    }

    private void printTable(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        view.write(Line.concat(2, "|", row.getValues()));
    }

    private void printHeader(Iterable<String> tableColumns) {
        String header = Line.concat(2, "|", tableColumns);
        if (header.length() > 0)
            header = Line.concat(2, Line.SEPARATOR,
                    Line.HORIZONTAL,
                    header,
                    Line.HORIZONTAL);
        else
            header = "No content in this table!";

        view.write(header);
    }

}
