package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

public class Connect extends Command {

    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|HcxbPRi5EoNB";

    public Connect(DatabaseManager manager, View view) {
        super(manager, view, !CONNECTION_REQUIRED);
        setAttributes(Message.CONNECT);
    }

    @Override
    public void process(String command) {
        String[] data = validArguments(command, COMMAND_SAMPLE);

        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        view.write(manager.canConnect(databaseName, userName, password) ?
            success() :
            String.format(failure(0), databaseName, userName, password));
    }

}
