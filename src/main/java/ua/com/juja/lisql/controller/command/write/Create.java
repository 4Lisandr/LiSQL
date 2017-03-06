package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

/**
 * Lisandr 1.03.2017
 */
public class Create extends Command {
    public Create(DatabaseManager manager, View view) {
        super(manager, view, true);
    }

    @Override
    public String format() {
        return "create|";
    }

    @Override
    public String description() {
        return "to create database";
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format(EMessage.ODD_PARAMETERS.toString(), data.length));
        }

        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String value = data[index*2 + 1];

            dataSet.put(columnName, value);
        }
        getManager().create(tableName, dataSet);

        getView().write(String.format(EMessage.SUCCESS_RECORD.toString(), dataSet, tableName));
    }
}
