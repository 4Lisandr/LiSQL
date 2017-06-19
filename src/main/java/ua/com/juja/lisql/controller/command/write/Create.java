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

import static ua.com.juja.lisql.controller.command.TextBuilder.SAMPLE_TABLE;

public class Create extends Command {
    public Create(DatabaseManager manager, View view) {
        setTextBuilder(new TextBuilder("create|tableName|column1|column2|...|columnN",
                CREATE.toString(), SUCCESS_DATABASE.toString()));
    }

    //todo - debug table creation
    @Override
    protected void process(String command) {
        String[] args = validArguments(command,
                new Validator(n -> n > 2, FAIL_COUNT.toString(), true));

        String table = args[SAMPLE_TABLE];
        manager.create(table, makeDataSet(args));

        view.write(String.format(success(), table));
    }

    private DataSet makeDataSet(String[] data) {
        DataSet dataSet = new DataSetImpl();
        for (int index = SAMPLE_TABLE + 1; index < data.length; index++) {
            String columnName = data[index];
            dataSet.put(columnName, "");
        }
        return dataSet;
    }
}
