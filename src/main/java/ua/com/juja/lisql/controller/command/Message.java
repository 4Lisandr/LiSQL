package ua.com.juja.lisql.controller.command;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.Config;
import ua.com.juja.lisql.view.Line;

// Класс тесно связан с командой - нужна инверсия зависимости!
public enum Message {
    HELLO   ("common.hello"),
    START   ("common.start"),
    INPUT   ("common.input"),
    CMD_LIST("common.list"),

    FAIL    ("common.fail"),
    OK      ("common.ok"),
    RETRY   ("common.try"),
    BYE     ("common.bye"),
    /**
     * String format here!
     **/
    FAIL_CONNECT      ("common.fail.connect"),
    FAIL_COUNT        ("common.fail.count"),
    TO_MANY_PARAMETERS("common.fail.many"),
    ODD_PARAMETERS    ("common.fail.odd"),
    SUCCESS_RECORD    ("common.ok.record"),
    DISCONNECTED      ("common.fail.disconnected"),
    /*
    * Block of command interface
    **/
    CONNECT ("command.connect", OK, FAIL_CONNECT),
    HELP    ("command.help"),
    EXIT    ("command.exit", BYE),
    LIST    ("command.list",OK,FAIL),
    FIND    ("command.find", OK,FAIL_COUNT,TO_MANY_PARAMETERS),
    CREATE  ("command.create", SUCCESS_RECORD),
    UPDATE  ("command.update", "to insert database", OK.toString()),

    CLEAR   ("command.clear", "now table %s is empty", "Couldn't clear table %s", "canceled"),
    DELETE  ("command.delete", "row is delete", FAIL.toString()),
    INSERT  ("command.insert", SUCCESS_RECORD),
    DROP    ("command.drop", OK, FAIL),
    UNKNOWN ("command.unknown");

    private String csvMessage;

    Message(String properties) {
        this.csvMessage = Config.RES.getString(properties);
    }

    Message(String properties, String... strings) {
        this(properties);
        String[] attributes = (strings == null) ?
            new String[]{csvMessage}:
            new String[strings.length + 1];

        if (attributes.length>1){
            attributes = ArrayUtils.addAll(new String[]{csvMessage}, strings);
        }

        this.csvMessage = Line.toCSV(attributes);
    }

    Message(String properties, Message... messages) {
        this(properties, asStrings(messages));
    }

    @Override
    public String toString() {
        return csvMessage;
    }

    private static String[] asStrings(Message ... messages){
        String[] result = (messages == null) ?
                new String [0]:
                new String[messages.length];

        for (int i = 0; i < result.length ; i++) {
            result[i] = messages[i].toString();
        }
        return result;
    }
    /**
     * http://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
     * */
    public String[] getCommandAttributes (){
        String[] name = new String[] {this.name().toLowerCase()};
        String split[] = Line.parseCSV(this.toString());

        return ArrayUtils.addAll(name, split);
    }
}
