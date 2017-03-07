package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.view.EMessage;
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
        setAttributes("start","warming up...", (EMessage.HELLO + " " + EMessage.START), "System failure!");
    }

    @Override
    public boolean canProcess(String command) {
        return instances == 1;
    }

    @Override
    public void process(String command) {
        getView().write(EMessage.HELLO.toString(), EMessage.START.toString());
    }


    public static boolean isCalled() {
        return instances > 0;
    }

}
