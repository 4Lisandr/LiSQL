package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

/**
 * Lisandr 1.03.2017
 */
public class Create extends Command {

    public Create(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("create", "to create database", Message.Say.SUCCESS_RECORD.toString());
    }

    @Override
    public void process(String command) {

        String[] data = validArgs(command);
        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.create(tableName, dataSet);

        view.write(String.format(success(), dataSet, tableName));
    }
}
