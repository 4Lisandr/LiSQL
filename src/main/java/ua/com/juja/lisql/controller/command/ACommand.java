package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

public abstract class ACommand extends ViewCommand {

    private DatabaseManager manager;

    public ACommand(DatabaseManager manager, View view) {
        super(view);
        this.manager = manager;
    }

    public DatabaseManager getManager() {
        return manager;
    }
}
