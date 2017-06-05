package ua.com.juja.lisql.model;

import java.sql.SQLException;
import java.util.List;
/**
 * DAO interface for CRUD operations with databases
 * */
public interface DatabaseManager {
    //Connection
    boolean canConnect(String database, String userName, String password) throws DAOException;
    boolean isConnected();
    //Read
    List<String> getTableNames() throws DAOException;
    List<String> getTableColumns(String tableName) throws DAOException;
    List<DataSet> getTableData(String tableName) throws DAOException;


    //Write
    void create(String tableName, DataSet input) throws DAOException;
    void insert(String tableName, DataSet input) throws DAOException;
    void update(String tableName, int id, DataSet newValue) throws DAOException;
    void delete(String s, String[] split) throws SQLException;
    void clear(String tableName) throws DAOException;

}
