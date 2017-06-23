package controller.model;

import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.VirtualDatabaseManager;

public class VirtualManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new VirtualDatabaseManager();
    }
}
