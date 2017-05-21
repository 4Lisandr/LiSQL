package ua.com.juja.lisql.view;

import ua.com.juja.lisql.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    UPDATE  ("command.update","to create database", OK),

    CLEAR   ("command.clear", "now table %s is empty", "Couldn't clear table %s", "canceled"),
    DELETE  ("command.delete"),
    INSERT  ("command.insert"),
    DROP    ("command.drop"),
    UNKNOWN ("command.unknown");

    private String message;

    Message(String message) {
        this.message = Config.RES.getString(message);
    }

    Message(String s, Object... messages) {
        this(s);
        message = Line.concat(Line.PIPE, message, messages);
    }

    @Override
    public String toString() {
        return message;
    }

    public static String[] getCommandAttributes (Message message){
        String a[] = {message.name().toLowerCase()};
        String b[] = message.toString().split(Line.PIPE);

        List <String>list = new ArrayList(Arrays.asList(a));
        list.addAll(Arrays.asList(b));

        return (String[]) list.toArray();
    }
}
