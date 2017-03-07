package ua.com.juja.lisql.model;

import java.util.List;

public interface DatabaseManager {
    //Connection
    void connect(String database, String userName, String password);
    void disconnect();
    boolean isConnected();
    //Read
    List<String> getTableNames();
    List<String> getTableColumns(String tableName);
    List<DataSet> getTableData(String tableName);
    //Write
    void create(String tableName, DataSet input);
    void update(String tableName, int id, DataSet newValue);
    void clear(String tableName);

}
