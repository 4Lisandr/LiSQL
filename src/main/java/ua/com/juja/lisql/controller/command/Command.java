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
        return content.getSample();
    }

    public String format() {
        return content.takeFormat();
    }

    public String description() {
        return content.getDescription();
    }

    protected String success() {
        return content.takeSuccess();
    }

    protected String failure() {
        return content.takeFailure();
    }

    protected String failure(int i) {
        return content.takeFailure(i);
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

    public boolean canProcess(String command) {
        return beginWith(content.takeFormat()).equalsIgnoreCase(beginWith(command));
    }

    /**
     * Validators section
     **/
    private boolean isConnected(String command) {
        if (!manager.isConnected()) {
            view.write(String.format(DISCONNECTED.toString(), command));
            return false;
        } else
            return true;
    }

    private String beginWith(String command) {
        return ((command == null) || command.trim().isEmpty()) ?
                "" :
                Line.split(command)[0];
    }

    /**
     * Returns an array of valid arguments from the
     *
     * @command from the received string, the number of arguments
     * strictly matches the pattern
     */
    public String[] takeArguments(String command) {
        int expected = Line.split(sample()).length;
        return takeArguments(command, expected);
    }

    /**
     * @param isEvenCount can be null if we don't care add or even number of arguments
     */
    public String[] takeArguments(String command, int expected, Boolean isEvenCount) {
        String[] actual = Line.split(command);
        Validator.atLeastCheck(actual.length, expected);
        if (isEvenCount == null)
            return actual;

//        Validator.even(isEvenCount).check(actual.length, -1);
        Validator.evenCheck(actual.length, isEvenCount);
        return actual;
    }

    public String[] takeArguments(String command, int expected) {
        String[] actual = Line.split(command);
        try {
            Validator.atLeast(expected).check(actual.length, expected);
            Validator.noMore(expected).check(actual.length, expected);
        } catch (IllegalArgumentException e) {
            view.write(e.getMessage());
        } catch (RuntimeException e) {
            view.write(e.getMessage());
        }
        return actual;
    }


    public static class Validator {
        Predicate<Integer> predicate;
        String report;
        boolean isException;

        public Validator(Predicate<Integer> predicate, String report, boolean isException) {
            this.predicate = predicate;
            this.report = report;
            this.isException = isException;
        }

        public static Validator atLeast(int expected) {
            return new Validator(n -> n >= expected, FAIL_COUNT.toString(), true);
        }

        public static Validator noMore(int expected) {
            return new Validator(n -> n <= expected, TO_MANY_PARAMETERS.toString(), false);
        }


        public static Validator even(boolean isEven) {
            return new Validator(n -> n % 2 == 0 || !isEven, ODD_PARAMETERS.toString(), true);
        }


        public static void atLeastCheck(int actual, int expected) {
            Validator.atLeast(expected).check(actual, expected);
        }

        public static void evenCheck(int length, Boolean isEvenCount) {
            Validator.even(isEvenCount).check(length, -1);
        }

        /**
         * @param expected = -1 for Validator even
         */
        public void check(int actual, int expected) {
            if (!predicate.test(actual)) {
                if (!isException || expected == -1) {
                    throw new RuntimeException((String.format(report, expected)));
                } else {
                    throw new IllegalArgumentException(
                            String.format(report, expected, actual));
                }
            }
        }
    }
}
