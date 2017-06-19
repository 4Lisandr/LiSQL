package ua.com.juja.lisql.view;

import java.util.List;
import java.util.Map;

/*
* Lisandr
* */
public interface View {

    String read();

    void write(String message);
    void write(String... messages);
    void write(Map<String, List<String>> table);

    boolean confirm();

}
