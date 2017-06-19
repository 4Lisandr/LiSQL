package ua.com.juja.lisql;


import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.PostgreSQLManager;
import ua.com.juja.lisql.view.Console;
import ua.com.juja.lisql.view.View;

/*
* Lisandr 2017
* */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new PostgreSQLManager();

        Controller controller = Controller.getInstance(view, manager);
        controller.run();
    }
}