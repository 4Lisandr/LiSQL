package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

public class Close extends Command {

    private static boolean isCalled;

    public Close(DatabaseManager manager, View view) {
        super(manager, view);
        isCalled = true;
        setAttributes("close", "shutdown...", "shutdown...", "System failure!");
    }

    @Override
    public boolean canProcess(String command) {
        return isCalled;
    }

    @Override
    public void process(String command) {
        // Корректно закрыть программу
    }

    public static boolean isCalled() {
        return isCalled;
    }
}
