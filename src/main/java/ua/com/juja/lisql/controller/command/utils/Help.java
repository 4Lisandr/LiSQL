package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

public class Help extends Command {

    public Help(View view) {
        super(view);
        setAttributes("help","for read help information");
    }

    @Override
    public void process(String command) {
    // А если вызвать сообщение полиморфно, с учетом локации?
    // Класс message содержит поле HELP  <String> EMessage.getHelp()
        getView().write(EMessage.HELP.toString());
    }

}
