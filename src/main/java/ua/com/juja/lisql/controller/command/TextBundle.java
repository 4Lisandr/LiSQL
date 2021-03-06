package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.view.UTF8Control;

import java.util.ResourceBundle;

public enum TextBundle {
    HELLO("common.hello"),
    START("common.start"),
    INPUT("common.input"),
    CMD_LIST("common.list"),
    FAIL("common.fail"),
    OK("common.ok"),
    RETRY("common.try"),
    BYE("common.bye"),
    /**
     * String takeFormat here!
     **/
    CONNECTED("common.is.connected"),
    FAIL_CONNECT("common.fail.connect"),
    FAIL_COUNT("common.fail.count"),
    TO_MANY_PARAMETERS("common.fail.many"),
    ODD_PARAMETERS("common.fail.odd"),
    SUCCESS_RECORD("common.ok.record"),
    SUCCESS_DATABASE("common.ok.database"),
    DISCONNECTED("common.fail.disconnected"),
    /*
    * Block of command interface
    **/
    CONNECT("command.connect"),
    HELP("command.help"),
    EXIT("command.exit"),
    LIST("command.list"),
    FIND("command.find"),
    CREATE("command.create"),
    UPDATE("command.update"),
    CLEAR("command.clear"),
    DELETE("command.delete"),
    INSERT("command.insert"),
    DROP("command.drop"),
    UNKNOWN("command.unknown");

    public static final String PATH_BUNDLE_COMMON = "ua.com.juja.lisql.view.bundle.common";
    private String text;

    TextBundle(String resource) {
        text = ResourceBundle.getBundle(
                PATH_BUNDLE_COMMON, new UTF8Control()).getString(resource);
    }

    @Override
    public String toString() {
        return text;
    }
}
