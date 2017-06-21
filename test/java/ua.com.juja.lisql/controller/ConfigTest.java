package controller;

import ua.com.juja.lisql.model.DBProperties;

public interface ConfigTest {
    String DB_NAME = DBProperties.getDatabaseName();
    String USER = DBProperties.getUserName();
    String PASSWORD = DBProperties.getPassword();
}
