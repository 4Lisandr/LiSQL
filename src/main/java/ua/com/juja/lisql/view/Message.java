package ua.com.juja.lisql.view;

import ua.com.juja.lisql.Config;

public enum Message { //todo реализовать мультиязычность - создать интерфейс, содержащий Enum
    HELLO   (Config.RES.getString("common.hello")),
    START   (Config.RES.getString("common.start")),
    INPUT   (Config.RES.getString("common.input")),
    HELP    (Config.RES.getString("common.help")),

    FAIL    (Config.RES.getString("common.fail")),
    OK      (Config.RES.getString("common.ok")),
    RETRY   (Config.RES.getString("common.try")),
    UNKNOWN (Config.RES.getString("command.unknown")),
    BYE     (Config.RES.getString("common.bye")),
    /**
     * String format here!
     **/
    FAIL_CONNECT      (Config.RES.getString("common.fail.connect")),
    FAIL_COUNT        (Config.RES.getString("common.fail.count")),
    TO_MANY_PARAMETERS(Config.RES.getString("common.fail.many")),
    ODD_PARAMETERS    (Config.RES.getString("common.fail.odd")),
    SUCCESS_RECORD    (Config.RES.getString("common.ok.record")),
    DISCONNECTED      (Config.RES.getString("common.fail.disconnected"));
    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
