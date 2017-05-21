package ua.com.juja.lisql;

import ua.com.juja.lisql.view.UTF8Control;

import java.util.ResourceBundle;

public interface Config {
    String PROPERTIES = "src/main/resources/";
    String BUNDLE = "ua.com.juja.lisql.view.bundle.";
    ResourceBundle RES = ResourceBundle.getBundle(BUNDLE+"common", new UTF8Control());
}
