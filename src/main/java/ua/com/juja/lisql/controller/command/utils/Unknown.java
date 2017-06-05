package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Message;
import ua.com.juja.lisql.view.View;

public class Unknown extends Command {

    public Unknown(View view){
        super(view);
        setAttributes(Message.UNKNOWN);
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
