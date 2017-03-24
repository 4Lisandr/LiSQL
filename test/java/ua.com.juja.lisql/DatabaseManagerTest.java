import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.PGDatabaseManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {
    public static final String DB_NAME = "sqlcmd";
    public static final String USER = "postgres";
    public static final String PASSWORD = "HcxbPRi5EoNB";

    public static final String[] TABLES = {"test", "users", "numbers", "mart2017", "Кирилица"};

    private PGDatabaseManager manager;

    @Before
    public void setup() {
        manager = new PGDatabaseManager();
        manager.canConnect(DB_NAME, USER, PASSWORD);
    }

    @Test
    public void testGetAllTableNames() {
        List<String> tableNames = manager.getTableNames();
        assertEquals(Arrays.asList(TABLES), tableNames);
    }

    @Ignore
    public void testGetTableData() {
        // given
        manager.clear("users");

        // when
        DataSet input = new DataSet();
        input.put("id", 113);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.create("users", input);

        // then
        List<DataSet> users = manager.getTableData("user");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals(Arrays.asList("name", "password", "id"), user.getNames());
        assertEquals(Arrays.asList("Stiven", "pass", "13"), user.getValues());
    }

//    @Test
//    public void testUpdateTableData() {
//        // given
//        manager.clear("user");
//
//        DataSet input = new DataSet();
//        input.put("id", 13);
//        input.put("name", "Stiven");
//        input.put("password", "pass");
//        manager.create(input);
//
//        // when
//        DataSet newValue = new DataSet();
//        newValue.put("password", "pass2");
//        newValue.put("name", "Pup");
//        manager.update("user", 13, newValue);
//
//        // then
//        DataSet[] users = manager.getTableData("user");
//        assertEquals(1, users.length);
//
//        DataSet user = users[0];
//        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
//        assertEquals("[Pup, pass2, 13]", Arrays.toString(user.getValues()));
//    }
}
