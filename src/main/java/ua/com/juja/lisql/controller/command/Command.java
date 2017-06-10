package ua.com.juja.lisql.controller.command;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

public abstract class Command {
    protected static final boolean CONNECTION_REQUIRED = true;

    protected View view;
    protected DatabaseManager manager;

    private boolean isConnectionRequired = CONNECTION_REQUIRED;
    private boolean isHiddenCommand;

    private Attributes attributes = new Attributes("","");

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


    public String sample(){
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

    public String failure(){
        return attributes.failure();
    }

    public String failure(int i){
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

    public abstract void  process(String command);

    public boolean canProcess(String command){
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
    public class Attributes {
        String [] attributes;
        /**
         * Example for command Connect:
         * @param sample "connect|sqlcmd|postgres|HcxbPRi5EoNB"
         * @param description - get from properties command.connect
         * @param cases - OK, FAIL
         */
        public Attributes(String sample, String description, String... cases) {
            attributes = ArrayUtils.addAll(new String[] {sample, description}, cases);
        }

        public Attributes(String[] array) {
            if (array==null || array.length < 2){
                throw new IllegalArgumentException("Must be at least 2 element in array!");
            }
            attributes = array;
        }
        //getters
        public String sample(){
            return getAttribute(0);
        }

        public String format(){
            return Line.split(sample())[0];
        }

        public String description(){
            return getAttribute(1);
        }

        protected String success(){
            return getAttribute(2);
        }

        public String failure(){
            return failure(0);
        }

        /**@param number of failure from 0*/
        public String failure(int number){
            int n = (number > 0 ? number : 0);
            return getAttribute(3+n);
        }

        private String getAttribute(int i) {
            return (attributes==null|| attributes.length < i+1) ? "" :
                    attributes[i];
        }
    }
}
