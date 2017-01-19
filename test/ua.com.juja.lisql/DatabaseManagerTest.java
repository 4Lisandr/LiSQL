import ua.com.juja.lisql.DatabaseManager;

public class DatabaseManagerTest {

    private DatabaseManager manager;

//    @Before
//    public void setup() {
//        manager = new DatabaseManager();
//        manager.connect("lisql", "postgres", "postgres");
//    }
//
//    @Test
//    public void testGetAllTableNames() {
//        String[] tableNames = manager.getTableNames();
//        assertEquals("[test, user]", Arrays.toString(tableNames));
//    }
//
//    @Test
//    public void testGetTableData() {
//        // given
//        manager.clear("user");
//
//        // when
//        DataSet input = new DataSet();
//        input.put("id", 13);
//        input.put("name", "Stiven");
//        input.put("password", "pass");
//        manager.create(input);
//
//        // then
//        DataSet[] users = manager.getTableData("user");
//        assertEquals(1, users.length);
//
//        DataSet user = users[0];
//        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
//        assertEquals("[Stiven, pass, 13]", Arrays.toString(user.getValues()));
//    }
//
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
