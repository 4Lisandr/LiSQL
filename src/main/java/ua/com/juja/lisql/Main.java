package ua.com.juja.lisql;

import ua.com.juja.lisql.controller.Controller;
import ua.com.juja.lisql.model.PostgreSQLManager;
import ua.com.juja.lisql.view.Console;

/*
* SQL command line manager by Lisandr 2019, a training project under the Juja.com.ua
* */
public final class Main {

    public static void main(String[] args) {

        Controller.getInstance(
                new Console(),
                new PostgreSQLManager())
                .run();
    }
}