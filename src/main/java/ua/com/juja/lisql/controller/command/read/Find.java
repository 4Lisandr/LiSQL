package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

import java.util.List;
import java.util.Set;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view, CONNECTION_REQUIRED);
        setAttributes("find|", "for search some records", "ok",
            EMessage.FAILED_COUNT.toString(),
            EMessage.TO_MANY_PARAMETERS.toString());
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");

        int sample = "find|sample".split("\\|").length; //2

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
