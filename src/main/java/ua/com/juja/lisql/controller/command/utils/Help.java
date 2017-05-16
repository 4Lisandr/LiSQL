package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

public class Help extends Command {

    public Help(View view) {
        super(view);
        setAttributes("help","for read help information");
    }

    @Override
    public void process(String command) {
        view.write(Message.HELP.toString());
        for(Command com: Controller.UsersCommand.getAll()){
            if(!com.isHidden())
                view.write(Line.concat("\t", com.format(), " - ", com.description()));
        }
    }

}
