package ua.com.juja.lisql.controller.command;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.view.Line;

import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;
import static ua.com.juja.lisql.controller.command.TextBundle.OK;

/**
 * sample; description; successMessage; failureMessages;
 * -------> class OnCase {String success; String failure;
 */
public class Content {
    public static final int SAMPLE = 0, SAMPLE_FORMAT = 0;
    public static final int DESCRIPTION = 1, SAMPLE_TABLE = 1;
    public static final int SUCCESS = 2;
    public static final int FAILURE = 3;

    private static final String SUCCESS_TEXT = OK.toString();
    private static final String FAIL_TEXT = FAIL.toString();

    String[] attributes;

    /**
     * Example for command Connect:
     *
     * @param sample      "connect|sqlcmd|postgres|HcxbPRi5EoNB"
     * @param description - get description from properties command.connect
     * @param cases       - SUCCESS, FAILURE
     */
    public Content(String sample, String description, String... cases) {
        this(ArrayUtils.addAll(new String[]{sample, description}, cases));
    }

    private Content(String[] array) {
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Must be at least 2 element in array!");
        }
        attributes = array;
    }

    //getters
    protected String sample() {
        return getAttribute(SAMPLE);
    }

    protected String format() {
        return Line.split(sample())[SAMPLE_FORMAT];
    }

    protected String description() {
        return getAttribute(DESCRIPTION);
    }

    protected String success() {
        String result = getAttribute(SUCCESS);
        return result.equals("") ?
                SUCCESS_TEXT :
                result;
    }

    protected String failure() {
        String result = failure(0);
        return result.equals("") ?
                FAIL_TEXT :
                result;
    }

    /**
     * @param number of failure from 0
     */
    protected String failure(int number) {
        int n = (number > 0 ? number : 0);
        return getAttribute(FAILURE + n);
    }

    private String getAttribute(int i) {
        if (attributes == null || i < 0)
            return "";

        return (attributes.length < i + 1) ? "" :
                attributes[i];
    }
}