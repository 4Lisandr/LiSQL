package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.Line;
import ua.com.juja.lisql.view.View;

import java.util.function.Predicate;

import static ua.com.juja.lisql.controller.command.TextBundle.*;

public abstract class Command {
    protected static final boolean CONNECTION_REQUIRED = true;

    protected View view;
    protected DatabaseManager manager;

    protected boolean isConnectionRequired;
    private boolean isHiddenCommand;
    //todo - refactor into interface Content -> Content(Container c)
    private Content content;

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
    public void setContent(Content content) {
        this.content = content;
    }

    protected void hide() {
        isHiddenCommand = true;
    }

    //Getters
    public boolean isHidden() {
        return isHiddenCommand;
    }


    public String sample() {
        return content.sample();
    }

    public String format() {
        return content.format();
    }

    public String description() {
        return content.description();
    }

    protected String success() {
        return content.success();
    }

    protected String failure() {
        return content.failure();
    }

    protected String failure(int i) {
        return content.failure(i);
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
        return beginWith(content.format()).equalsIgnoreCase(beginWith(command));
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

    //todo - проверить stringFormat
    public String[] validArguments(String command, Validator... validators) {
        String[] result = Line.split(command);
        int target = Line.split(sample()).length;

        for (Validator v : validators) {
            Predicate<Integer> p = v.getPredicate();
            if (!p.test(result.length)) {
                if (v.isException)
                    throw new IllegalArgumentException(
                            String.format(v.getReport(), result.length));
                else
                    view.write(String.format(v.getReport(), target - 1));
            }
        }
        return result;
    }

    protected String[] validArguments(String command, int atLeast) {
        String[] result = Line.split(command);

        if (result.length < atLeast)
            throw new IllegalArgumentException(
                    String.format(FAIL_COUNT.toString(), atLeast, result.length));
        return result;
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

    public class Validator {
        Predicate<Integer> predicate;
        String report;
        boolean isException;

        public Validator(Predicate<Integer> predicate, String report, boolean isException) {
            this.predicate = predicate;
            this.report = report;
            this.isException = isException;
        }

        public Predicate<Integer> getPredicate() {
            return predicate;
        }

        public String getReport() {
            return report;
        }

        public boolean isException() {
            return isException;
        }
    }
}
