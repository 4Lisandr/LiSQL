package ua.com.juja.lisql.controller.command;

import org.apache.commons.lang3.ArrayUtils;
import ua.com.juja.lisql.view.Line;

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
