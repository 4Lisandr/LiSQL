package ua.com.juja.lisql.view;

/**
 *  Lisandr 23.02.17
 */
public enum Message { //todo реализовать мультиязычность - создать интерфейс, содержащий Enum
    HELLO ("Hello, user!"),
    START ("Enter, please the database name, username and password in the format: connect|database|userName|password"),
    INPUT ("Enter the command (or help for read list of commands):"),
    HELP ("List of commands:"),

    FAIL ("Failure because of:"),
    SUCCESS ("Success!"),
    /**
     *   String format here!
     **/
    FAILED_CONNECT ("Can't connect to database '%s' for user %s and password %s"),
    FAILED_COUNT ("Wrong number of parameters, %s expected, but specified: %s"),
    TO_MANY_PARAMETERS ("Too many parameters were specified, '%s' were processed, but others - ignored!"),
    ODD_PARAMETERS ("There must be an even number of parameters in the format " +
        "create|tableName|column1|value1|column2|value2|...|columnN|valueN', but specified: %s"),
    SUCCESS_RECORD ("The record %s has created successfully in the table %s."),
    DISCONNECTED ("Command %s couldn't executed without connection (connect|database|userName|password)"),

    RETRY ("Try again."),
    UNKNOWN ("Unknown command:"),

    GOODBYE ("Goodbye!");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
