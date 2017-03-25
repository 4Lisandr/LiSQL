package ua.com.juja.lisql.model;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGDatabaseManager implements DatabaseManager {


    private static final String POSTGRESQL = "postgresql"; // database type (MySQL LiteSQL, etc...)
    private static final String HOST = "localhost";
    private static final int PORT = 5432;

    private static final String SELECT_TABLE_NAME = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
    private static final String SELECT_COLUMNS = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = ";
    private static final String SELECT_ALL = "SELECT * FROM public.";
    private static final String INSERT_FORMAT = "INSERT INTO public.%s (%s) VALUES (%s)";
    private static final String DELETE = "DELETE FROM public.";

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
    public boolean canConnect(String database, String user, String password) {
        try(Connection connect = connect(database, user, password)){
            if (connect!= null){
                connectParameters = new String[]{database, user, password};
                return true;
            }
        } catch (NullPointerException | SQLException e) {
            exceptionHandler("Couldn't connect",e);
        }
        return false;
    }
    @Override
    public boolean isConnected() {
        if (connectParameters.length == 0)
            return false;

        try (Connection connection = connect(connectParameters))
        {
            return connection!= null;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * Don't share connection
     * */
    private Connection connect(String database, String user, String password){
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

    private Connection connect(String ... parameters){
        return parameters == null || parameters.length != 3 ? null :
            connect(parameters[0], parameters[1], parameters[2]);
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
        return getStrings(SELECT_COLUMNS+"'"+tableName+"'", "column_name");
    }

    private List<String> getStrings(String query, String target) {

        ArrayList<String> result = new ArrayList<>();

        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)
             )
        {
            while (rs.next())
                result.add(rs.getString(target));

        } catch (SQLException e) {
            exceptionHandler("Couldn't retrieve string value",e);
        }

        return result;
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        ArrayList<DataSet> result = new ArrayList<>();

        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL + tableName)
             )
        {
            ResultSetMetaData meta = rs.getMetaData();

            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result.add(dataSet);
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    dataSet.put(meta.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            exceptionHandler("SQL",e);
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
    //todo - debug Create
    @Override
    public void create(String tableName, DataSet input) {
        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement())
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

        String tableNames = getNameFormatted(newValue, "%s = ?,");
        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";

        try (Connection connection = connect(connectParameters);
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear(String tableName) {
        try (Connection connection = connect(connectParameters);
             Statement stmt = connection.createStatement();
        ){
            stmt.executeUpdate(DELETE + tableName);
        } catch (SQLException e) {
            exceptionHandler("Could not clear table "+ tableName, e);
        }
    }

    private static void exceptionHandler(String msg, Throwable e) {
        log.error(msg, e);
        throw new DAOException(msg, e);
    }

}
