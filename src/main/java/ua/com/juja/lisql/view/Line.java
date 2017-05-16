package ua.com.juja.lisql.view;

import java.util.ArrayList;
import java.util.List;

public final class Line {
    public static final String SEPARATOR = System.getProperty("line.separator");
    public static final String PIPE = "\\|";
    public static final String HYPHEN = " -";
    public static final String HORIZONTAL = "--------------------";

    public static String[] split(String s) {
        if (s == null || s.length() == 0)
            return new String[0];

        return (s.contains(HYPHEN)) ?
                s.split(HYPHEN) :
                s.split(PIPE);
    }

    public static String concat(String... args) {
        return concat("", true, args);
    }

    public static String toCSV(String... args) {
        return concat(",", false, args);
    }

    public static String concat(String suffix, boolean inBegin, Iterable args) {
        List<String> list = new ArrayList();
        for (Object o : args) {
            if (o != null)
                list.add(o.toString());
        }
        return list.size() == 0 ? "" :
                concat(suffix, inBegin, list.toArray(new String[list.size()]));
    }

    public static String concat(String suffix, boolean inBegin, String... args) {
        if (args == null || args.length == 0)
            return "";

        StringBuilder sb = new StringBuilder(
                inBegin ? suffix : ""
        );

        for (String arg : args) {
            sb.append(arg).append(suffix);
        }
        String result = sb.toString();
        return result.substring(0, result.length() - suffix.length());
    }


    /**
     * For multithreading
     */
    public static String concat(boolean anyFlag, String... args) {
        StringBuffer result = new StringBuffer();
        for (String arg : args) {
            result.append(arg);
        }
        return result.toString();
    }
}
