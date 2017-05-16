package ua.com.juja.lisql;

import java.util.ResourceBundle;

public interface Config {
    String PROPERTIES = "src/main/resources/";
    String COMMON = "ua.com.juja.lisql.view.bundle.common";
    ResourceBundle RES = ResourceBundle.getBundle(COMMON);
}
