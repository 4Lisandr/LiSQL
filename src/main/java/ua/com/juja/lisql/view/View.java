package ua.com.juja.lisql.view;
/*
* Lisandr
* */
public interface View {

    String read();

    void write(String message);
    void write(String... messages);

    boolean confirm();
}
