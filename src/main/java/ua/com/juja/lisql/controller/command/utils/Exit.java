package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

public class Exit extends Command {

    public Exit(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("exit", "for quit out", EMessage.GOODBYE.toString());
    }

    @Override
    public void process(String command) {
        getView().write(success());
        new Close(getManager(), getView());
    }
}
