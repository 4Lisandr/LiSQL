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
        super(manager, view, true);
    }

    @Override
    public String format() {
        return "find|";
    }

    @Override
    public String description() {
        return "for search some records";
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");

        int length = "find|sample".split("\\|").length; //2

        if (data.length< length) // search only one parameter, other parameters ignored
            throw new IllegalArgumentException(
                    String.format(EMessage.FAILED_COUNT.toString(), length, data.length));
        if (data.length> length)
            getView().write(String.format(EMessage.TO_MANY_PARAMETERS.toString(), length-1));

        String tableName = data[1];

        List<String> tableColumns = getManager().getTableColumns(tableName);
        printHeader(tableColumns);

        List<DataSet> tableData = getManager().getTableData(tableName);
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
        getView().write(result);
    }

    private void printHeader(Iterable<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        getView().write("--------------------");
        getView().write(result);
        getView().write("--------------------");
    }

}
