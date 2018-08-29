package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.view.Line;

import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;
import static ua.com.juja.lisql.controller.command.TextBundle.OK;

/**
 * getSample; getDescription; successMessage; failureMessages;
 * -------> class OnCase {String takeSuccess; String takeFailure;
 */
public class Content {
    public static final int SUCCESS = 0, SAMPLE_FORMAT = 0;
    public static final int FAILURE = 1, SAMPLE_TABLE = 1;

    private static final String SUCCESS_TEXT = OK.toString();
    private static final String FAIL_TEXT = FAIL.toString();

    private String sample; // шаблон команды
    private String description; //описание
    private String[] cases; //

    /**
     * Example for command Connect:
     *
     * @param sample      "connect|sqlcmd|postgres|HcxbPRi5EoNB"
     * @param description - get getDescription from properties command.connect
     * @param cases       - SUCCESS, FAILURE
     */
    public Content(String sample, String description, String... cases) {
        if (sample == null || description == null)
            throw new IllegalArgumentException("Must not be null getSample or getDescription!");
        this.sample = sample;
        this.description = description;
        this.cases = cases;
    }

    //getters
    protected String getSample() {
        return sample;
    }

    protected String takeFormat() {
        return Line.split(sample)[SAMPLE_FORMAT];
    }

    protected String getDescription() {
        return description;
    }

    protected String takeSuccess() {
        String result = getCases(SUCCESS);
        return result.equals("") ?
                SUCCESS_TEXT :
                result;
    }

    protected String takeFailure() {
        String result = takeFailure(0);
        return result.equals("") ?
                FAIL_TEXT :
                result;
    }

    /**
     * @param number of takeFailure from 0
     */
    protected String takeFailure(int number) {
        int n = (number > 0 ? number : 0);
        return getCases(FAILURE + n);
    }

    private String getCases(int i) {
        if (cases == null || i < 0)
            return "";

        return (cases.length < i + 1) ? "" :
                cases[i];
    }
}