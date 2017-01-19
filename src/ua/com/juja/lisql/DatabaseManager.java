package ua.com.juja.lisql;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        String database = "lisql";
        String user = "postgres";
        String password = "postgres";

        DatabaseManager manager = new DatabaseManager();

//        manager.connect(database, user, password);
//
//        Connection connection = manager.getConnection();

        // delete
//        manager.clear("user");

        // insert
        DataSet data = new DataSet();
//        data.put("id", 13);
//        data.put("name", "Stiven");
//        data.put("password", "pass");
//        manager.create(data);

        // select
//        String[] tables = manager.getTableNames();
//        System.out.println(Arrays.toString(tables));

        String tableName = "user";

//        DataSet[] result = manager.getTableData(tableName);
//        System.out.println(Arrays.toString(result));

        // update


//        connection.close();
    }

//    public DataSet[] getTableData(String tableName) {
//        try {
//            int size = getSize(tableName);
//
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            DataSet[] result = new DataSet[size];
//            int index = 0;
//            while (rs.next()) {
//                DataSet dataSet = new DataSet();
//                result[index++] = dataSet;
//                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
//                }
//            }
//            rs.close();
//            stmt.close();
//            return result;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new DataSet[0];
//        }
//    }
//
//    private int getSize(String tableName) throws SQLException {
//        Statement stmt = connection.createStatement();
//        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
//        rsCount.next();
//        int size = rsCount.getInt(1);
//        rsCount.close();
//        return size;
//    }
//
//    public String[] getTableNames() {
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
//            String[] tables = new String[100];
//            int index = 0;
//            while (rs.next()) {
//                tables[index++] = rs.getString("table_name");
//            }
//            tables = Arrays.copyOf(tables, index, String[].class);
//            rs.close();
//            stmt.close();
//            return tables;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new String[0];
//        }
//    }
//
//    public void connect(String database, String user, String password) {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Please add jdbc jar to project.");
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/" + database, user,
//                    password);
//        } catch (SQLException e) {
//            System.out.println(String.format("Cant get connection for database:%s user:%s", database, user));
//            e.printStackTrace();
//            connection = null;
//        }
//    }
//
//    private Connection getConnection() {
//        return connection;
//    }
//
//    public void clear(String tableName) {
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("DELETE FROM public." + tableName);
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void create(DataSet input) {
//        try {
//            Statement stmt = connection.createStatement();
//
//            String tableNames = getNameFormated(input, "%s,");
//            String values = getValuesFormated(input, "'%s',");
//
//            stmt.executeUpdate("INSERT INTO public.user (" + tableNames + ")" +
//                    "VALUES (" + values + ")");
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getValuesFormated(DataSet input, String format) {
//        String values = "";
//        for (Object value: input.getValues()) {
//            values += String.format(format, value);
//        }
//        values = values.substring(0, values.length() - 1);
//        return values;
//    }
//
//    public void update(String tableName, int id, DataSet newValue) {
//        try {
//            String tableNames = getNameFormated(newValue, "%s = ?,");
//
//            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id == ?";
//            System.out.println(sql);
//            PreparedStatement ps = connection.prepareStatement(sql);
//
//            int index = 1;
//            for (Object value : newValue.getValues()) {
//                ps.setObject(index, value);
//                index++;
//            }
//            ps.setObject(index, id);
//
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getNameFormated(DataSet newValue, String format) {
//        String string = "";
//        for (String name : newValue.getNames()) {
//            string += String.format(format, name);
//        }
//        string = string.substring(0, string.length() - 1);
//        return string;
//    }
}
