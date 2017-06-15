package ua.com.juja.lisql.model;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PGDatabaseManager implements DatabaseManager {


    private static final String POSTGRESQL = "postgresql"; // database type (MySQL LiteSQL, etc...)
    private static final String HOST = DBProperties.getServerIP();
    private static final int PORT = DBProperties.getServerPort();

    private static final String SELECT_TABLE_NAME = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
    private static final String SELECT_COLUMNS = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = ";
    private static final String SELECT_ALL = "SELECT * FROM public.";
    private static final String INSERT_FORMAT = "INSERT INTO public.%s (%s) VALUES (%s)";
    private static final String DELETE = "DELETE FROM public.";
    private static final String DELETE_FORMAT = "DELETE FROM %s WHERE %s = %s";
    private static final String DROP = "DROP TABLE public.";


    private static final Logger log = Logger.getLogger(PGDatabaseManager.class);

    //    private static VirtualConnection virtual = null;
    private String[] connectParameters = new String[0];

    /*
      Driver initialisation
      */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            exceptionHandler("Couldn't connect. Please add Postgresql JDBC jar to project.", e);
        }
    }

    /*
     * Open, check and close connection. Set parameters for further connections.
     */
    @Override
    public boolean canConnect(String database, String user, String password) throws DAOException {
        try (Connection connect = connect(database, user, password)) {
            if (connect != null) {
                connectParameters = new String[]{database, user, password};
                return true;
            }
        } catch (NullPointerException | SQLException e) {
            exceptionHandler("Couldn't connect", e);
        }
        return false;
    }

    @Override
    public boolean isConnected() {
        if (connectParameters.length == 0)
            return false;

        try (Connection connection = connect(connectParameters)) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Don't share connection
     */
    private Connection connect(String database, String user, String password) throws DAOException {
        Connection connect;
        try {
            String url = String.format("jdbc:%s://%s:%s/%s", POSTGRESQL, HOST, PORT, database);
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            exceptionHandler(String.format("Couldn't getText connection for database:%s user:%s", database, user), e);
            connect = null;
        }
        return connect;
    }

    private Connection connect(String... parameters) {
        return parameters == null || parameters.length != 3 ? null :
                connect(parameters[0], parameters[1], parameters[2]);
    }


    /*
     * Read access section
     */
    @Override
    public List<String> getTableNames() throws DAOException {
        return getStrings(SELECT_TABLE_NAME, "table_name");
    }

    @Override
    public List<String> getTableColumns(String tableName) throws DAOException {
        return getStrings(SELECT_COLUMNS + "'" + tableName + "'", "column_name");
    }

    private List<String> getStrings(String query, String target) {

        ArrayList<String> result = new ArrayList<>();

        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)
        ) {
            while (rs.next())
                result.add(rs.getString(target));

        } catch (SQLException e) {
            exceptionHandler("Couldn't retrieve string value", e);
        }

        return result;
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws DAOException {
        ArrayList<DataSet> result = new ArrayList<>();

        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL + tableName)
        ) {
            ResultSetMetaData meta = rs.getMetaData();

            while (rs.next()) {
                DataSet dataSet = new DataSetImpl();
                result.add(dataSet);
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    dataSet.put(meta.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            exceptionHandler("SQL", e);
            return new ArrayList<>();
        }

        return result;
    }


    private int getSize(String tableName) throws SQLException {
        return 0;
    }

    /*
     * Write access section
     */

    /**
     *  Example of query CREATE TABLE:
     *  CREATE TABLE public.tableName
     *  CREATE TABLE COMPANY(
     *  ID INT PRIMARY KEY     NOT NULL,
     *  NAME           TEXT    NOT NULL,
     *  ADDRESS        CHAR(50),
     *  SALARY         REAL
     *  );
     **/
    @Override
    public void create(String tableName, DataSet input) throws DAOException {
        String columns = input.getNamesFormatted("%s TEXT,");
        String sql = "CREATE TABLE public."+tableName+"(ID INT PRIMARY KEY NOT NULL," +
                columns+ ");";
        write(sql, tableName);
    }


    @Override
    public void insert(String tableName, DataSet input) throws DAOException {
        String columns = input.getNamesFormatted("%s,");
        String values = input.getValuesFormatted("'%s',");
        String sql = "INSERT INTO public." + tableName + " (" + columns + ")" +
                    "VALUES (" + values + ")";
        write(sql, tableName);
    }


    @Override
    public void update(String tableName, DataSet newValue) throws DAOException {

        Set names = newValue.getNames();
        List values = newValue.getValues();
        String[] columns = (String[]) names.stream().toArray(String[]::new);
        // 0 - имя столбца записи которое проверяется
        // 1 -  имя обновляемого столбца записи
        String sql = "UPDATE public." + tableName + " SET " +
                columns[1] + " = "+values.get(1)+" WHERE "+
                columns[0]+" = "+values.get(0);

        write(sql, tableName);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) throws DAOException {

        String tableNames = newValue.getNamesFormatted("%s = ?,");
        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";

        try (Connection connection = connect(connectParameters);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                statement.setObject(index, value);
                index++;
            }
            statement.setInt(index, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            exceptionHandler("Couldn't update " + tableName, e);
        }
    }

    @Override
    public void delete(String tableName, String[] split) throws DAOException {
        String sql = String.format(DELETE_FORMAT, tableName, split[0], split[1]);
        write(sql, tableName);
    }

    @Override
    public void clear(String tableName) throws DAOException {
        String sql = DELETE + tableName;
        write(sql, tableName);
    }

    @Override
    public void drop(String tableName) {
        String sql = DROP + tableName;
        write(sql, tableName);
    }

    private void write(String sql, String tableName){
        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            exceptionHandler("Couldn't process table " + tableName, e);
        }
    }
    //todo - improve logging
    // https://dzone.com/articles/9-logging-sins-in-your-java-applications?utm_source=Top%205&utm_medium=email&utm_campaign=2017-06-09
    private static void exceptionHandler(String msg, Throwable e) {
        log.error(msg, e);
        throw new DAOException(msg, e);
    }

}
