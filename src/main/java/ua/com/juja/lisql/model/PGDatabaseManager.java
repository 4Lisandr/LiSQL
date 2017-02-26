package ua.com.juja.lisql.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGDatabaseManager implements DatabaseManager {

    public static final String JDBC_TYPE = "jdbc:postgresql"; // database type (MySQL LiteSQL, etc...)
    public static final String HOST = "localhost";
    public static final int PORT = 5432;
    private Connection connection;
    /*
     * Connection section
     */
    @Override
    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Please add Postgresql JDBC jar to project.");
            e.printStackTrace();
        }
        try {
            String url = JDBC_TYPE + "://" + HOST + ":" + PORT + "/" + database;
            connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(String.format("Can't getText connection for database:%s user:%s", database, user));
                e.printStackTrace();
                connection = null;
            }
    }

    @Override
    public boolean isConnected() {
        return connection!=null;
    }

    private Connection getConnection() {
        return connection;
    }
    /*
     * Read access section
     */
    @Override
    public List<String> getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");

            ArrayList<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        return new ArrayList<>();
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        return new ArrayList<>();
    }

    private int getSize(String tableName) throws SQLException {
        return 0;
    }
    /*
     * Write access section
     */

    @Override
    public void create(String tableName, DataSet input) {

    }

    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(DataSet input) {
        try {
            Statement stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public.user (" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id == ?";
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value: input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }


    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }
}
