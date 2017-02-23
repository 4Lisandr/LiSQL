package ua.com.juja.lisql.model;

public interface DatabaseManager {
    //Connection
    void connect(String database, String userName, String password);
    boolean isConnected();
    //Read
    String[] getTableNames();
    String[] getTableColumns(String tableName);
    DataSet[] getTableData(String tableName);
    //Write
    void create(String tableName, DataSet input);
    void update(String tableName, int id, DataSet newValue);
    void clear(String tableName);
}
