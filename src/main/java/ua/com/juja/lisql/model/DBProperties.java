package ua.com.juja.lisql.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ua.com.juja.lisql.Config.PROPERTIES;


public class DBProperties {
    private static final Properties properties = new Properties();
    private static final File FILE_DB_PROPERTIES = new File(PROPERTIES+"database.properties");

    static {
        try (FileInputStream fileInputStream = new FileInputStream(FILE_DB_PROPERTIES)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Database Parameters
     */
    public static String getServerIP() {
        return properties.getProperty("server.ip");
    }

    public static String getServerHost() {
        return properties.getProperty("server.port");
    }

    public static int getServerPort() {
        return Integer.parseInt(getServerHost());
    }

    public static String getDatabaseName() {
        return properties.getProperty("name");
    }

    public static String getUserName() {
        return properties.getProperty("user.name");
    }

    public static String getPassword() {
        return properties.getProperty("user.password");
    }

}
