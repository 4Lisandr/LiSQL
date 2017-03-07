package ua.com.juja.lisql;


import ua.com.juja.lisql.controller.MainController;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.PGDatabaseManager;
import ua.com.juja.lisql.view.Console;
import ua.com.juja.lisql.view.View;

/*
* Lisandr 2017
* */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new PGDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}