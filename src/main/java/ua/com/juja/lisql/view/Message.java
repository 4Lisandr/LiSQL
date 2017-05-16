package ua.com.juja.lisql.view;

import ua.com.juja.lisql.Config;

import java.util.ResourceBundle;

/**
 *  Lisandr 23.02.17
 */
public class Message {
    private static ResourceBundle res = ResourceBundle.getBundle(Config.COMMON);

    public enum Say { //todo реализовать мультиязычность - создать интерфейс, содержащий Enum
        HELLO(res.getString("common.hello")),
        START (res.getString("common.start")),
        INPUT(res.getString("common.input")),
        HELP(res.getString("common.help")),

        FAIL(res.getString("common.fail")),
        OK(res.getString("common.ok")),
        /**
         * String format here!
         **/
        FAIL_CONNECT (res.getString("common.fail.connect")),
        FAIL_COUNT (res.getString("common.fail.count")),
        TO_MANY_PARAMETERS (res.getString("common.fail.many")),
        ODD_PARAMETERS (res.getString("common.fail.odd")),
        SUCCESS_RECORD (res.getString("common.ok.record")),
        DISCONNECTED (res.getString("common.fail.disconnected")),

        RETRY (res.getString("common.try")),
        UNKNOWN (res.getString("command.unknown")),

        BYE (res.getString("common.bye"));

        private final String message;

        Say(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
