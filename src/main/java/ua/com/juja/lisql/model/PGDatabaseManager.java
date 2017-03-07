package ua.com.juja.lisql.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGDatabaseManager implements DatabaseManager {

    public static final String JDBC_TYPE = "jdbc:postgresql"; // database type (MySQL LiteSQL, etc...)
    public static final String HOST = "localhost";
    public static final int PORT = 5432;
    public static final String SELECT_TABLE_NAME = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
    public static final String SELECT_COLUMNS = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = ";
    public static final String SELECT_ALL = "SELECT * FROM public.";
    public static final String INSERT_FORMAT = "INSERT INTO public.%s (%s) VALUES (%s)";
//    "INSERT INTO public." + tableName + " (" + tableNames + ")" +
//            "VALUES (" + values + ")"
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
                connection = null;
            }
    }

    @Override
    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
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
        return getStrings(SELECT_TABLE_NAME, "table_name" );
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        return getStrings(SELECT_COLUMNS +"'"+tableName+"'", "column_name");
    }

    private List<String> getStrings(String query, String target) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)
             ){

            ArrayList<String> result = new ArrayList<>();

            while (rs.next()) {
                result.add(rs.getString(target));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL + tableName)
             ){

            ArrayList<DataSet> result = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private int getSize(String tableName) throws SQLException {
        return 0;
    }
    /*
     * Write access section
     */
    //todo - debug Create
    @Override
    public void create(String tableName, DataSet input) {
        try (Statement stmt = connection.createStatement())
        {
            String tableNames = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

            stmt.executeUpdate(String.format(INSERT_FORMAT, tableName, tableNames, values));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFormatted(Iterable iterable, String format) {
        String string = "";
        for (Object o: iterable) {
            string += String.format(format, o);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    private String getNameFormatted(DataSet newValue, String format) {
        return getFormatted(newValue.getNames(), format);
    }

    private String getValuesFormatted(DataSet input, String format) {
        return getFormatted(input.getValues(), format);
    }


    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try {
            String tableNames = getNameFormatted(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear(String tableName) {

    }

}
