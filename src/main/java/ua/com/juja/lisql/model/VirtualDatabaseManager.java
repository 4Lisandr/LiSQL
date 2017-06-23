package ua.com.juja.lisql.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VirtualDatabaseManager implements DatabaseManager {

    public static final String BASE_NAME = "virtual";
    private List <DataSet> data = new ArrayList<>();
    private boolean isConnected = false;

    @Override
    public boolean canConnect(String database, String userName, String password) throws DAOException {
        validateDB(database);
        return isConnected = true;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    private void validateDB(String database) {
        if (!BASE_NAME.equals(database)) {
            throw new UnsupportedOperationException("Only for '"+BASE_NAME+"' database, but you try to work with: " + database);
        }
    }

    @Override
    public List<String> getTableNames() {
        return Arrays.asList("user", "test");
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        return Arrays.asList("name", "password", "id");
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        return data;
    }

    @Override
    public void create(String tableName, DataSet input) {
        data.add(input);
    }

    @Override
    public void insert(String tableName, DataSet input) throws DAOException {

    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (DataSet row: data) {
            if(row.get("id").equals(id)){
                row.update(newValue);
            }
        }
    }

    @Override
    public void update(String tableName, DataSet newValue) throws DAOException {

    }


    @Override
    public void delete(String s, String[] split) throws SQLException {

    }


    @Override
    public void clear(String tableName) {
        data = new ArrayList<>();
    }

    @Override
    public void drop(String s) {
        data = null;
    }

}
