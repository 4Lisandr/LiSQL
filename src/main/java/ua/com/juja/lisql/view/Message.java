package ua.com.juja.lisql.view;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.Config;
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
    UPDATE  ("command.update"/*, "to create database", OK*/),

    CLEAR   ("command.clear"/*, "now table %s is empty", "Couldn't clear table %s", "canceled"*/),
    DELETE  ("command.delete"),
    INSERT  ("command.insert"),
    DROP    ("command.drop"),
    UNKNOWN ("command.unknown");

    private String csvMessage;

    Message(String properties) {
        this.csvMessage = Config.RES.getString(properties);
    }

    Message(String properties, Message... messages) {
        this(properties);
        String[] attributes = (messages == null) ?
            new String[]{csvMessage}:
            new String[messages.length + 1];

        if (attributes.length>1){
            attributes = ArrayUtils.addAll(new String[]{csvMessage}, asStrings(messages));
        }

        this.csvMessage = Line.toCSV(attributes);
    }

    @Override
    public String toString() {
        return csvMessage;
    }

    private String[] asStrings(Message ... messages){
        String[] res = (messages == null) ?
                new String [0]:
                new String[messages.length];

        for (int i = 0; i < res.length ; i++) {
            res[i] = messages[i].toString();
        }
        return res;
    }
    /**
     * http://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
     * */
    public String[] getCommandAttributes (){
        String[] name = new String[] {name().toLowerCase()};
        String split[] = Line.parseCSV(toString());

        return ArrayUtils.addAll(name, split);
    }
}
