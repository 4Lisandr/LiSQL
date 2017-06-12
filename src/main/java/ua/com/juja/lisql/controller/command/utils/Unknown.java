package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.UNKNOWN;

public class Unknown extends Command {

    public Unknown(View view) {
        super(view);
        setTextBuilder(new TextBuilder("", UNKNOWN.toString()));
        hide();
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override // A non-existent command was successfully detected and rendered harmless
    public void process(String command) {
        view.write(description(), " ", command);
    }
}
