package ua.com.juja.lisql.controller.command;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.Config;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

public abstract class Command {
    protected static final boolean CONNECTION_REQUIRED = true;

    protected View view;
    protected DatabaseManager manager;

    private boolean isConnectionRequired = CONNECTION_REQUIRED;
    private boolean isHiddenCommand;

    protected Attributes attributes = new Attributes("","");

    /** Default constructor reserved*/
    public Command() {}

    public Command(View view) {
        this();
        this.view = view;
        isConnectionRequired = !CONNECTION_REQUIRED;
    }

    public Command(DatabaseManager manager, View view) {
        this(view);
        this.manager = manager;
        isConnectionRequired = CONNECTION_REQUIRED;
    }

    /** @param connect - use constant CONNECTION_REQUIRED */
    public Command(DatabaseManager manager, View view, boolean connect) {
        this(manager, view);
        isConnectionRequired = connect;
    }

    /**
     * Setters section
     * */

    /**
     * @param message - sample, description, cases;
     */
    public void setAttributes(Message message) {
        attributes = new Attributes(message.getCommandAttributes());
    }

    protected void hide() {
        isHiddenCommand = true;
    }

    //Getters
    public boolean isHidden() {
        return isHiddenCommand;
    }


    protected String sample(){
        return attributes.sample();
    }

    public String format(){
        return attributes.format();
    }

    public String description(){
        return attributes.description();
    }

    protected String success(){
        return attributes.success();
    }

    protected String failure(){
        return attributes.failure();
    }

    protected String failure(int i){
        return attributes.failure(i);
    }

    /**
     * Work section
     * */
    public boolean run(String command) throws CmdException {
        if (!canProcess(command)) {
            return false;
        }
        if (isConnectionRequired && !isConnected(command)) {
            throw new CmdException("missed connection", new RuntimeException());
        } else {
            process(command);
            return true;
        }
    }

    protected abstract void  process(String command);

    protected boolean canProcess(String command){
        return beginWith(attributes.format()).equalsIgnoreCase(beginWith(command));
    }

    /**
     * Validators section
     **/
    private String beginWith(String command) {
        return ((command==null)||command.trim().isEmpty()) ?
            "" :
            Line.split(command)[0];
    }

    private boolean isConnected(String command) {
        if (!manager.isConnected()){
            view.write(String.format(Message.DISCONNECTED.toString(), command));
            return false;
        }
        else
            return true;
    }

    protected String[] validArguments(String command, String sample) {
        int target = Line.split(sample).length;
        String[] result = Line.split(command);

        if (result.length < target)
            throw new IllegalArgumentException(
                    String.format(Message.FAIL_COUNT.toString(), target, result.length));
        if (result.length> target)
            view.write(String.format(Message.TO_MANY_PARAMETERS.toString(), target-1));
        return result;
    }

    protected String[] validArguments(String command) {
        String[] data = Line.split(command);
        if (data.length %2 != 0)
            throw new IllegalArgumentException(
                    String.format(Message.ODD_PARAMETERS.toString(), data.length));
        return data;
    }

    /**
     *  sample; description; successMessage; failureMessages;
     * -------> class OnCase {String success; String failure;
     */
    private static class Attributes {
        private static final int SAMPLE = 0;
        private static final int DESCRIPTION = 1;
        private static final int SUCCESS = 2;
        private static final int FAILURE = 3;
        String [] attributes;
        /**
         * Example for command Connect:
         * @param sample "connect|sqlcmd|postgres|HcxbPRi5EoNB"
         * @param description - get from properties command.connect
         * @param cases - OK, FAIL
         */
        private Attributes(String sample, String description, String... cases) {
            this(ArrayUtils.addAll(new String[] {sample, description}, cases));
        }

        private Attributes(String[] array) {
            if (array==null || array.length < 2){
                throw new IllegalArgumentException("Must be at least 2 element in array!");
            }
            attributes = array;
        }

        //getters
        private String sample(){
            return getAttribute(SAMPLE);
        }

        private String format(){
            return Line.split(sample())[0];
        }

        private String description(){
            return getAttribute(DESCRIPTION);
        }

        private String success(){
            return getAttribute(SUCCESS);
        }

        private String failure(){
            return failure(0);
        }

        /**@param number of failure from 0*/
        private String failure(int number){
            int n = (number > 0 ? number : 0);
            return getAttribute(FAILURE +n);
        }

        private String getAttribute(int i) {
            if (attributes==null|| i < 0)
                return "";

            return (attributes.length < i + 1) ? "" :
                    attributes[i];
        }
    }

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
        CONNECT ("connect|sqlcmd|postgres|HcxbPRi5EoNB", "command.connect", OK, FAIL_CONNECT),
//        CONNECT ("command.connect", OK, FAIL_CONNECT),
        HELP    ("command.help"),
        EXIT    ("command.exit", BYE),
        LIST    ("command.list",OK,FAIL),
        FIND    ("command.find", OK,FAIL_COUNT,TO_MANY_PARAMETERS),
        CREATE  ("command.create", SUCCESS_RECORD),
        UPDATE  ("command.update", "to insert database", OK.toString()),

        CLEAR   ("command.clear", "now table %s is empty", "Couldn't clear table %s", "operation is canceled"),
        DELETE  ("command.delete", "row is delete", FAIL.toString()),
        INSERT  ("command.insert", SUCCESS_RECORD),
        DROP    ("command.drop", "database deleted", FAIL.toString(), "operation is canceled"),
        UNKNOWN ("command.unknown");

        private String csvMessage;
        private Attributes attributes;

        Message(String properties) {
            this.csvMessage = Config.RES.getString(properties);
        }

        Message(Attributes attributes) {

        }


        Message(String sample, String description, Message ok, Message failConnect) {
            attributes = new Attributes(sample, Config.RES.getString(description), ok.toString(), failConnect.toString());
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

        private static String[] asStrings(Message... messages){
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
}
