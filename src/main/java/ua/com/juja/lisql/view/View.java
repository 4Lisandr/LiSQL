package ua.com.juja.lisql.view;

import java.util.List;

/*
* Lisandr
* */
public interface View {

    String read();

    void write(String message);
    void write(String... messages);
    void write(List<List<?>> table);

    boolean confirm();

}
