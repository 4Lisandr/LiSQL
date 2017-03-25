package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class UserConnect extends Command {

    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|HcxbPRi5EoNB";

    public UserConnect(DatabaseManager manager, View view) {
        super(manager, view, !CONNECTION_REQUIRED);
        setAttributes("connect","connect to database",
            Message.SUCCESS.toString());

    }

    @Override
    public void process(String command) {
        String[] data = validArgs(command, COMMAND_SAMPLE);

        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        if (manager.canConnect(databaseName, userName, password))
            view.write(success());
    }

}
