package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

public abstract class ConnectCommand extends ACommand {

    public ConnectCommand(DatabaseManager manager, View view) {
        super(manager, view);
    }

    public void checkConnect(String command) {
        if (!getManager().isConnected()){
            getView().write(String.format(EMessage.DISCONNECTED.toString(), command));
        }
    }
}
