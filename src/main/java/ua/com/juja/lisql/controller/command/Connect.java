package ua.com.juja.lisql.controller.command;


import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Connect implements Command {

    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|HcxbPRi5EoNB";

    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(
                    String.format("Ќеверно количество параметров разделенных " +
                                    "знаком '|', ожидаетс€ %s, но есть: %s",
                            count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);

        view.write("”спех!");
    }

    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }

}
