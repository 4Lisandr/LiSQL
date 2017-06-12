package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.BYE;
import static ua.com.juja.lisql.controller.command.TextBundle.EXIT;

public class Exit extends Command {

    public Exit(DatabaseManager manager, View view) {
        super(manager, view);
        isConnectionRequired = !CONNECTION_REQUIRED;
        setTextBuilder(new TextBuilder("exit", EXIT.toString(), BYE.toString()));
    }

    @Override
    public void process(String command) {
        view.write(success());
        new Close(manager, view);
    }
}
