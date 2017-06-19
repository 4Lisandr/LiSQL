package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.FAIL_COUNT;
import static ua.com.juja.lisql.controller.command.TextBundle.UPDATE;


public class Update extends Command {

    public Update(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("update|tableName|column1|value1|column2|value2",
                UPDATE.toString()));
    }

    @Override
    public void process(String command) {
        String[] data = validArguments(command, new Validator(
                n -> n == 6,
                FAIL_COUNT.toString(), true));
        String table = data[Content.SAMPLE_TABLE];

        DataSet dataSet = new DataSetImpl();
        for (int index = 2; index < (data.length / 2); index += 2) {
            String columnName = data[index];
            String value = data[index + 1];

            dataSet.put(columnName, value);
        }
        manager.update(table, dataSet);

        view.write(String.format(success(), dataSet, table));
    }
}
