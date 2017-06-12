package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

import static ua.com.juja.lisql.controller.command.TextBundle.*;

public abstract class Command {
    protected static final boolean CONNECTION_REQUIRED = true;

    protected View view;
    protected DatabaseManager manager;

    protected boolean isConnectionRequired;
    private boolean isHiddenCommand;

    private TextBuilder textBuilder;

    /**
     * Default constructor reserved
     */
    public Command() {
    }

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

    /**
     * Setters section
     */
    public void setTextBuilder(TextBuilder textBuilder) {
        this.textBuilder = textBuilder;
    }

    protected void hide() {
        isHiddenCommand = true;
    }

    //Getters
    public boolean isHidden() {
        return isHiddenCommand;
    }


    public String sample() {
        return textBuilder.sample();
    }

    public String format() {
        return textBuilder.format();
    }

    public String description() {
        return textBuilder.description();
    }

    protected String success() {
        return textBuilder.success();
    }

    protected String failure() {
        return textBuilder.failure();
    }

    protected String failure(int i) {
        return textBuilder.failure(i);
    }

    /**
     * Work section
     */
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

    protected abstract void process(String command);

    protected boolean canProcess(String command) {
        return beginWith(textBuilder.format()).equalsIgnoreCase(beginWith(command));
    }

    /**
     * Validators section
     **/
    private String beginWith(String command) {
        return ((command == null) || command.trim().isEmpty()) ?
                "" :
                Line.split(command)[0];
    }

    private boolean isConnected(String command) {
        if (!manager.isConnected()) {
            view.write(String.format(DISCONNECTED.toString(), command));
            return false;
        } else
            return true;
    }

    protected String[] validArguments(String command) {
        int target = Line.split(sample()).length;
        String[] result = Line.split(command);

        if (result.length < target)
            throw new IllegalArgumentException(
                    String.format(FAIL_COUNT.toString(), target, result.length));
        if (result.length > target)
            view.write(String.format(TO_MANY_PARAMETERS.toString(), target - 1));
        return result;
    }

    protected String[] validArguments(String command, boolean isEven) {
        String[] result = Line.split(command);
        if (!(result.length % 2 == 0) == isEven)
            throw new IllegalArgumentException(
                    String.format(ODD_PARAMETERS.toString(), result.length));
        return result;
    }
}
