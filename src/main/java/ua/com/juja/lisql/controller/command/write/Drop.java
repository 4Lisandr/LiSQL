package ua.com.juja.lisql.controller.command.write;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.Content;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.DROP;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;

public class Drop extends Command {
    public Drop(DatabaseManager manager, View view) {
        super(manager, view);
        setContent(new Content("drop|users", DROP.toString(),
                "database deleted", FAIL.toString(), "operation is canceled"));
    }

    @Override
    public void process(String command) {
        String[] args = takeArguments(command);
        String table = args[Content.SAMPLE_TABLE];

        //todo перенести логику подтверждения из вьюхи в контроллер.
        // а что если юзеру понадобится удалить сразу несколько таблиц?
        dropTable(table);
    }

    private void dropTable(String table) {
        if (manager.getTableNames().contains(table)) {
            if (view.confirm()) {
                try {
                    manager.drop(table);
                } catch (Exception e) {
                    view.write(String.format(failure(0), table));
                }
                view.write(String.format(success(), table));
            } else {
                view.write(failure(1));
            }
        } else{
            view.write("Table " + table + " das not exist in current database. Use command \"list\"");
        }
    }
}
