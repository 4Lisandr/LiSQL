package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.ViewCommand;
import ua.com.juja.lisql.view.View;

public class Close extends ViewCommand {

    private static boolean isCalled;

    public Close(View view) {
        super(view);
        isCalled = true;
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
