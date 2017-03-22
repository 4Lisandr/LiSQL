package ua.com.juja.lisql.view;

public interface Line {
    String SEPARATOR = System.getProperty("line.separator");
    String PIPE = "\\|";
    String HYPHEN = " -";

    static String[] split(String s) {
        if (s == null || s.length()==0)
            return new String[0];

        return (s.contains(HYPHEN)) ?
            s.split(HYPHEN):
            s.split(PIPE);
    }

    static String concat(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg: args) {
            result.append(arg);
        }
        return result.toString();
    }
    /** For multithreading */
    static String concat(boolean anyFlag, String... args) {
        StringBuffer result = new StringBuffer();
        for (String arg: args) {
            result.append(arg);
        }
        return result.toString();
    }
}
