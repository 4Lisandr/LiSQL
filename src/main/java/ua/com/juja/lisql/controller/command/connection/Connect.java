package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

public class Connect extends Command {

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
        isConnectionRequired = false;
        setTextBuilder(new TextBuilder("connect|sqlcmd|postgres|HcxbPRi5EoNB",
            "command.connect"));
    }

    @Override
    public void process(String command) {
        String[] data = validArguments(command);

        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        view.write(manager.canConnect(databaseName, userName, password) ?
            success() :
            String.format(failure(0), databaseName, userName, password));
    }

}
