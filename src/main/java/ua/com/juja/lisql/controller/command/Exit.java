package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class Exit implements Command {

    private View view;

    public Exit(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("exit");
    }

    @Override
    public void process(String command) {
        view.write(Message.GOODBYE.toString());
        throw new ExitException();
    }
}
