package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.CONNECT;
import static ua.com.juja.lisql.controller.command.TextBundle.CONNECTED;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL_CONNECT;


public class Connect extends Command {

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
        isConnectionRequired = false;
        setContent(new Content("connect|sqlcmd|postgres|HcxbPRi5EoNB",
                CONNECT.toString(), CONNECTED.toString(), FAIL_CONNECT.toString()));
    }

    @Override
    public void process(String command) {
        String[] data = validArguments(command);

        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        view.write(manager.canConnect(databaseName, userName, password) ?
                String.format(success(), databaseName, userName):
                String.format(failure(), databaseName, userName, password));
    }

}
