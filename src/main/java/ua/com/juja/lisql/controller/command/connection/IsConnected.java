package ua.com.juja.lisql.controller.command.connection;

import ua.com.juja.lisql.controller.command.ACommand;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class IsConnected extends ACommand {

    public IsConnected(DatabaseManager manager, View view){
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return !getManager().isConnected();
    }

    @Override
    public void process(String command) {
        getView().write(String.format(Message.DISCONNECTED.toString(), command));
    }

}
