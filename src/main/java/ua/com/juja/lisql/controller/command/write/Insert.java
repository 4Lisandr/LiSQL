package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.INSERT;
import static ua.com.juja.lisql.controller.command.TextBundle.ODD_PARAMETERS;
import static ua.com.juja.lisql.controller.command.TextBundle.SUCCESS_RECORD;

/**
 * Lisandr 1.03.2017
 */
public class Insert extends Command {

    // Sample: insert|tableName|column1|value1 (length> 1 && length%2 == 0)
    public Insert(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("insert|tableName|column1|value1", INSERT.toString(),
                SUCCESS_RECORD.toString()));
    }

    @Override
    public void process(String command) {
        String[] data = validArguments(command, new Validator(
                n -> n % 2 == 0,
                ODD_PARAMETERS.toString(), true));
        String table = data[Content.SAMPLE_TABLE];

        DataSet dataSet = new DataSetImpl();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.insert(table, dataSet);

        view.write(String.format(success(), dataSet, table));
    }
}
