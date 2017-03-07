package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.PGDatabaseManager;
import ua.com.juja.lisql.view.Console;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class Connect extends Command {

    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|HcxbPRi5EoNB";

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("connect|","подключение к БД",
            EMessage.SUCCESS.toString(),EMessage.FAILED_COUNT.toString());
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");
        if (data.length != sample()) {
            throw new IllegalArgumentException(
                String.format(failure(0), sample(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        getManager().connect(databaseName, userName, password);

        if (isConnected(databaseName))
            getView().write(success(),"");
    }

    /** Some overhead just for avoid magic number 4 */
    private int sample() {
        return COMMAND_SAMPLE.split("\\|").length;
    }

    // todo не выводить "успех" при падении соединения
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new PGDatabaseManager();
        new Connect(manager, view ).process("connect|database|userName|password");
    }
}
