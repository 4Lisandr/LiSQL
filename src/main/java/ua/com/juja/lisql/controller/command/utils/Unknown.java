package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.ViewCommand;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

public class Unknown extends ViewCommand {

    public Unknown(View view){
        super(view);
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        getView().write(EMessage.UNKNOWN + " " + command, "");
    }
}
