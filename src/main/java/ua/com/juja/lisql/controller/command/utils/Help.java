package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

import java.util.List;

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
        view.write(getHelp(Controller.UsersCommand.getAll()));
    }

    private String getHelp(List<Command> commands) {
        String result = "";
        for (Command cmd : commands) {
            if (cmd.isHidden()) {
                continue;
            }
            String sample = cmd.sample().equals(cmd.format()) ?
                    "" :
                    " (" + cmd.sample() + ")";

            result += Line.concat("\t", cmd.format(), " - ", cmd.description(), sample, Line.SEPARATOR);
        }
        return result;
    }
}
