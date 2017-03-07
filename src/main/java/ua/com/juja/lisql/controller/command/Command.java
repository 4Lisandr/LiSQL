package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

/**
 *  Standard Command Class
 */
public abstract class Command {

    private View view;
    private DatabaseManager manager;

    private boolean isConnectionRequired;
    private boolean isHiddenCommand;

    //format; description; successMessage; failureMessages;
    private String[] attributes;

    /**
     * Default constructor reserved
     */
    public Command() {}

    public Command(View view) {
        this();
        this.view = view;
    }

    public Command(DatabaseManager manager, View view) {
        this(view);
        this.manager = manager;
    }

    public Command(DatabaseManager manager, View view, boolean connect) {
        this(manager, view);
        isConnectionRequired = connect;
    }

    /** Getters */
    public View getView() {
        return view;
    }
    public DatabaseManager getManager() {
        return manager;
    }

    public String format(){
        return getAttribute(0);
    }
    public String description(){
        return getAttribute(1);
    }
    public String success(){
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

    /** Setters */
    public void setAttributes(String ... text) {
        if (text != null){
            attributes = new String[text.length];
            System.arraycopy(text, 0, attributes, 0, text.length);
        }
    }

    /**
     * For the properly work you should implement this method in your command
     * */
    public abstract void  process(String command);


    public boolean canProcess(String command){
        return canProcess(command, isConnectionRequired);
    }

    public boolean canProcess(String command, boolean checkConnection){
        boolean isMatches = beginWith(format()).equalsIgnoreCase(beginWith(command));

        return checkConnection ?
                isMatches && isConnected(command) :
                isMatches;
    }

    public String beginWith(String input) {
        if ((input==null)||input.trim().isEmpty())
            return "";
        else
            return input.split("\\|")[0];
    }

    public boolean isConnected(String command) {
        if (!getManager().isConnected()){
            getView().write(String.format(EMessage.DISCONNECTED.toString(), command));
            return false;
        }
        else
            return true;
    }

}
