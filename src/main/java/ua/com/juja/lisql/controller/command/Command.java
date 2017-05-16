package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.Message;
import ua.com.juja.lisql.view.View;

/**
 *  Standard Command Class
 */
public abstract class Command {

    protected View view;
    protected DatabaseManager manager;

    /** Specify this value in the constructor of your command*/
    private boolean isConnectionRequired = true;
    /** Variable for system or inactive command*/
    private boolean isHiddenCommand;

    protected static final boolean CONNECTION_REQUIRED = true;
    protected static final boolean HIDDEN_COMMAND = true;

    //format; description; successMessage; failureMessages;
    private String[] attributes;

    /**
     * Default constructor reserved
     */
    public Command() {}

    public Command(View view) {
        this();
        this.view = view;
        isConnectionRequired = false;
    }

    public Command(DatabaseManager manager, View view) {
        this(view);
        this.manager = manager;
        isConnectionRequired = true;
    }

    /** @param connect - use constant CONNECTION_REQUIRED */
    public Command(DatabaseManager manager, View view, boolean connect) {
        this(manager, view);
        isConnectionRequired = connect;
    }

    /**
     * Getters
     * */
    public String format(){
        return getAttribute(0);
    }
    public String description(){
        return getAttribute(1);
    }

    protected String success(){
        return getAttribute(2);
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

    public boolean isHidden() {
        return isHiddenCommand;
    }

    /** Setters */
    public void setAttributes(String ... text) {
        if (text != null){
            attributes = new String[text.length];
            System.arraycopy(text, 0, attributes, 0, text.length);
        }
    }

    protected void hide() {
        isHiddenCommand = HIDDEN_COMMAND;
    }


    public boolean run(String command) throws CmdException {
        if (canProcess(command))
            if (!isConnectionRequired || isConnected(command)){
                process(command);
                return true;
            }
            else throw new CmdException("missed connection", new RuntimeException());

        return false;
    }

    /**
     * For the properly work you should implement this method in your command
     * */
    public abstract void  process(String command);

    public boolean canProcess(String command){
        return beginWith(format()).equalsIgnoreCase(beginWith(command)) &&
                validate(command); //
    }
    // check numbers of parameters
    protected boolean validate(String command){
        return true;
    }

    private String beginWith(String input) {
        return ((input==null)||input.trim().isEmpty()) ?
            "" :
            Line.split(input)[0];
    }
    // if connection issue
    private boolean isConnected(String command) {
        if (!manager.isConnected()){
            view.write(String.format(Message.DISCONNECTED.toString(), command));
            return false;
        }
        else
            return true;
    }

    // < 2 - Exception (-1), == 2 - OK (0), > 2 Warning (1)
    // ODD EVEN others condition( command, condition){
    //  condition.check
    // }
    protected String[] validArgs (String command, String sample) {
        int target = Line.split(sample).length;
        String[] data = Line.split(command);

        if (data.length < target)
            throw new IllegalArgumentException(
                    String.format(Message.FAIL_COUNT.toString(), target, data.length));
        if (data.length> target)
            view.write(String.format(Message.TO_MANY_PARAMETERS.toString(), target-1));
        return data;
    }

    protected String[] validArgs (String command) {
        String[] data = Line.split(command);
        if (data.length %2 != 0)
            throw new IllegalArgumentException(
                    String.format(Message.ODD_PARAMETERS.toString(), data.length));
        return data;
    }
}
