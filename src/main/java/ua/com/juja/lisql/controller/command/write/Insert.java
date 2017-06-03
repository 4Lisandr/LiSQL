package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

/**
 * Lisandr 1.03.2017
 */
public class Insert extends Command {

    public Insert(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes(Message.INSERT);
    }

    // Sample: insert|tableName|column1|value1
    // if column1 = id, then ID INT PRIMARY KEY NOT NULL,
    // value1 - type detect
    @Override
    public void process(String command) {
        String[] data = validArgs(command);
        String tableName = data[1];

        DataSet dataSet = new DataSetImpl();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.insert(tableName, dataSet);

        view.write(String.format(success(), dataSet, tableName));
    }
}
