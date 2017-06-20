package ua.com.juja.lisql.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ua.com.juja.lisql.Config.PATH_TO_PROPERTIES;


public final class DBProperties {
    private static final File FILE_DB_PROPERTIES = new File(PATH_TO_PROPERTIES +"database.properties");
    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream fileInputStream = new FileInputStream(FILE_DB_PROPERTIES)) {
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DBProperties() {}

    /**
     * Database Parameters
     */
    public static String getServerIP() {
        return PROPERTIES.getProperty("server.ip");
    }

    public static String getServerHost() {
        return PROPERTIES.getProperty("server.port");
    }

    public static int getServerPort() {
        return Integer.parseInt(getServerHost());
    }

    public static String getDatabaseName() {
        return PROPERTIES.getProperty("name");
    }

    public static String getUserName() {
        return PROPERTIES.getProperty("user.name");
    }

    public static String getPassword() {
        return PROPERTIES.getProperty("user.password");
    }

}
