package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class Unknown implements Command {

    private View view;

    public Unknown(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write(Message.UNKNOWN+" " + command, "");
    }
}
