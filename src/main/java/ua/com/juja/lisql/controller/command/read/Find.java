package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

import java.util.List;
import java.util.Set;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("find|", "for search some records", "ok",
            Message.FAILED_COUNT.toString(),
            Message.TO_MANY_PARAMETERS.toString());
    }

    @Override
    public void process(String command) {

        String[] data = Line.split(command);

        int sample = Line.split("find|sample").length; //2

        if (data.length< sample) // search only one parameter, other parameters ignored
            throw new IllegalArgumentException(
                    String.format(failure(0), sample, data.length));
        if (data.length> sample)
            view.write(String.format(failure(1), sample-1));

        String tableName = data[1];

        List<String> tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        List<DataSet> tableData = manager.getTableData(tableName);
        printTable(tableData);
    }


    //todo - remove view methods in view module
    private void printTable(List<DataSet> tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Set<Object> values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }

    private void printHeader(Iterable<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

}
