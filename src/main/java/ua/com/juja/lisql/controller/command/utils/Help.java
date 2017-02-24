package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.ViewCommand;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class Help extends ViewCommand {

    public Help(View view) {
        super(view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("help");
    }

    @Override
    public void process(String command) {
        getView().write(Message.HELP.toString());
    }
}
