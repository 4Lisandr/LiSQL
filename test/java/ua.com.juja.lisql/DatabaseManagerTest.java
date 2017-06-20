import org.junit.Before;
import org.junit.Test;
import ua.com.juja.lisql.model.DBProperties;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.PostgreSQLManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {
    public static final String DB_NAME = DBProperties.getDatabaseName();
    public static final String USER = DBProperties.getUserName();
    public static final String PASSWORD = DBProperties.getPassword();

    public static final String[] TABLES = {"test", "users", "user", "Кирилица", "table2"};

    private PostgreSQLManager manager;

    @Before
    public void setup() {
        manager = new PostgreSQLManager();
        manager.canConnect(DB_NAME, USER, PASSWORD);
    }

    @Test
    public void testGetAllTableNames() {
        List<String> tableNames = manager.getTableNames();
        assertEquals(Arrays.asList(TABLES), tableNames);
    }

    @Test
    public void testGetTableData() {
        // given
        manager.clear("users");

        // when
        DataSet input = new DataSetImpl();
        input.put("id", 113);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insert("users", input);

        // then
        List<DataSet> users = manager.getTableData("users");
        assertEquals(1, users.size());

        DataSet user = users.get(0);
        assertEquals("[name, password, id]", user.getNames().toString());
        assertEquals("[Stiven, pass, 113]", user.getValues().toString());
    }

    @Test
    public void testUpdateTableData() {
        // given
        manager.clear("user");

        DataSet[] input = new DataSet[5];
        for (int i = 0; i < input.length; i++) {
            input[i] = new DataSetImpl();
            input[i].put("id", -13+i);
            input[i].put("name", "Stiven"+i);
            input[i].put("password", "pass"+i);
            input[i].put("active", i%2==0);
            manager.insert("user", input[i]);
        }


        // when
        DataSet newValue = new DataSetImpl();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        newValue.put("active", true);
        manager.update("user", -13, newValue);

        // then
        List <DataSet> users = manager.getTableData("user");
        assertEquals(5, users.size());

        DataSet user = users.get(4);
        assertEquals("[name, password, id, active]", user.getNames().toString());
        assertEquals("[Pup, pass2, -13, true]", user.getValues().toString());
    }
}
