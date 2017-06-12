package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.CMD_LIST;
import static ua.com.juja.lisql.controller.command.TextBundle.HELP;

public class Help extends Command {

    public Help(View view) {
        super(view);
        setTextBuilder(new TextBuilder("help", HELP.toString()));
    }

    @Override
    public void process(String command) {
        view.write(CMD_LIST.toString());
        for(Command com: Controller.UsersCommand.getAll()){
            if(!com.isHidden()){
                view.write(Line.concat("\t", com.format(), " - ", com.description()));
            }
        }
    }

}
