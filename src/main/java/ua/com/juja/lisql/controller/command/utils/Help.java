package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

import java.util.List;

import static ua.com.juja.lisql.controller.command.TextBundle.CMD_LIST;
import static ua.com.juja.lisql.controller.command.TextBundle.HELP;

public class Help extends Command {

    public Help(View view) {
        super(view);
        setContent(new Content("help", HELP.toString()));
    }

    @Override
    public void process(String command) {
        view.write(CMD_LIST.toString());
        view.write(getHelp(Controller.UsersCommand.getAll()));
    }

    private String getHelp(List<Command> commands) {
        final String[] result = {""};

        commands.stream().filter(c -> !c.isHidden())
                .forEach(cmd -> {
                    result[0] += Line.concat("\t", cmd.format(), " - ",
                            cmd.description(),
                            cmd.sample().equals(cmd.format()) ?
                                            "" : " (" + cmd.sample() + ")", Line.SEPARATOR);
                        }
                );
        return result[0];
    }
}
