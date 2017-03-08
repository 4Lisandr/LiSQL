package ua.com.juja.lisql.view;

public interface Line {
    String SEPARATOR = System.getProperty("line.separator");
    String SPLITTER = "\\|";

    static String concat(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg: args) {
            result.append(arg);
        }
        return result.toString();
    }
    /** For multithreading */
    static String multiConcat(String... args) {
        StringBuffer result = new StringBuffer();
        for (String arg: args) {
            result.append(arg);
        }
        return result.toString();
    }
}
