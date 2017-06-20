package ua.com.juja.lisql.view;

import java.util.ArrayList;
import java.util.List;

public final class Line {
    public static final String SEPARATOR = System.getProperty("line.separator");
    public static final String PIPE = "\\|";
    public static final String HYPHEN = " -";
    public static final String SEMICOLON = ";";
    public static final String HORIZONTAL = "-";

    private Line() {}

    /**
     * @param s
     * @return
     */
    public static String[] split(String s) {
        if (s == null || s.length() == 0)
            return new String[0];

        return (s.contains(HYPHEN)) ?
                s.split(HYPHEN) :
                s.split(PIPE);
    }

    /**
     * @param args
     * @return
     */
    public static String concat(String... args) {
        return concat(-1, "", args);
    }

    /**
     * @param args
     * @return
     */
    public static String toCSV(String... args) {
        return concat(0, SEMICOLON, args);
    }

    /**
     * @param csvString
     * @return
     */
    public static String[] parseCSV(String csvString) {
        return csvString == null ?
                new String[0] :
                csvString.split(SEMICOLON);
    }

    /**
     * @param bound
     * @param suffix
     * @param args
     * @return
     */
    public static String concat(int bound, String suffix, Iterable args) {
        List<String> list = new ArrayList();
        for (Object o : args) {
            if (o != null)
                list.add(o.toString());
        }
        return list.size() == 0 ? "" :
                concat(bound, suffix, list.toArray(new String[list.size()]));
    }

    /**
     * @param bounds -1 - ignore suffix, 0 - no suffix in begin,
     *               1 - suffix in the begin of line, 2 - suffix in the begin and in the end of line
     * @param suffix
     * @param args
     */
    public static String concat(int bounds, String suffix, String... args) {
        if (args == null || args.length == 0)
            return "";

        if (suffix == null || bounds == -1)
            suffix = "";

        StringBuffer sb = new StringBuffer(suffix);

        for (String arg : args) {
            sb.append(arg).append(suffix);
        }

        return trim(bounds, suffix, sb.toString());
    }

    private static String trim(int bounds, String suffix, String line) {
        int begin = (bounds == 0) ?
                suffix.length() :
                0;

        int end = (bounds == 2) ?
                0 :
                suffix.length();

        return line.substring(begin, line.length() - end);
    }

}
