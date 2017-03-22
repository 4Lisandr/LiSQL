package ua.com.juja.lisql.controller.command.connection;


import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.PGDatabaseManager;
import ua.com.juja.lisql.view.Console;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

/**
 *
 */
public class UserConnect extends Command {

    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|HcxbPRi5EoNB";

    public UserConnect(DatabaseManager manager, View view) {
        super(manager, view);
        setAttributes("connect|","подключение к БД",
            Message.SUCCESS.toString(), Message.FAILED_COUNT.toString());

    }

    @Override
    public void process(String command) {

        String[] data = command.split(Line.SPLITTER);
        if (data.length != sample()) {
            throw new IllegalArgumentException(
                String.format(failure(0), sample(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        if (manager.canConnect(databaseName, userName, password))
            view.write(success(),"");
    }

    /** Some overhead just for avoid magic number 4 */
    private int sample() {
        return COMMAND_SAMPLE.split("\\|").length;
    }

    // todo не выводить "успех" при падении соединения
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new PGDatabaseManager();
        new UserConnect(manager, view ).process("connect|database|userName|password");
    }
}
