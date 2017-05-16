package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

/**
 * Команда Start запускает заставку-приветствие и приглашение пользователю
 * Безусловный вызов при старте программы
 */
public class Start extends Command {

    private static int instances;

    public Start(View view) {
        super(view);
        instances++;
        setAttributes("run","warming up...", (Message.Say.HELLO + " " + Message.Say.START), "System failure!");
    }

    @Override
    public boolean canProcess(String command) {
        return instances == 1;
    }

    @Override
    public void process(String command) {
        if (canProcess(command))
            view.write(Message.Say.HELLO.toString()," ", Message.Say.START.toString());
    }


    public static boolean isCalled() {
        return instances > 0;
    }

}
