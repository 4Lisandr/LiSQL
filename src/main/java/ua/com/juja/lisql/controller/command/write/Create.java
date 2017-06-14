package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.CREATE;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL_COUNT;
import static ua.com.juja.lisql.controller.command.TextBundle.SUCCESS_DATABASE;

public class Create extends Command {
    public Create(DatabaseManager manager, View view) {
        setTextBuilder(new TextBuilder("create|tableName|column1|column2|...|columnN",
                CREATE.toString(), SUCCESS_DATABASE.toString()));
    }
    //todo - debug table creation
    @Override
    protected void process(String command) {
        String[] data = validArguments(command, new Validator(
                n -> n > 2,
                FAIL_COUNT.toString(), true));
        String tableName = data[1];

        DataSet dataSet = new DataSetImpl();

        for (int index = 2; index < data.length; index++) {
            String columnName = data[index];
            dataSet.put(columnName, "");
        }

        manager.create(tableName, dataSet);

        view.write(String.format(success(), tableName));
    }
}
