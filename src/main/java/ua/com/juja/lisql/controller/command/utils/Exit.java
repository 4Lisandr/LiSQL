package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class Exit extends Command {

    public Exit(DatabaseManager manager, View view) {
        super(manager, view, !CONNECTION_REQUIRED);
        setAttributes(Message.EXIT);
    }

    @Override
    public void process(String command) {
        view.write(success());
        new Close(manager, view);
    }
}
