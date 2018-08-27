package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.Content.SAMPLE_TABLE;
import static ua.com.juja.lisql.controller.command.TextBundle.CREATE;
import static ua.com.juja.lisql.controller.command.TextBundle.SUCCESS_DATABASE;

public class Create extends Command {
    //todo - if(sample.contains("...") --> split("...") --> expected && sample%2==0 )
    public Create(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("create|tableName|column1|column2|...|columnN",
                CREATE.toString(), SUCCESS_DATABASE.toString()));
    }

    @Override
    protected void process(String command) {
        String[] args = validArguments(command, 3, null);

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
