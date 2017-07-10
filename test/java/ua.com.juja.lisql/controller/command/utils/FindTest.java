package controller.command.utils;

import controller.command.CommandTest;
import org.junit.Before;
import org.junit.Ignore;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.read.Find;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class FindTest extends CommandTest {

    @Before
    @Override
    public void init() {
        super.init();
        command = new Find(manager, view);
    }

    @Override
    public void positive() throws CmdException {
        testPrintEmptyTableData();
        testPrintTableDataWithOneColumn();
        testPrintTableData();
    }

    @Ignore
    @Override
    public void boundary() throws CmdException {

    }

    @Ignore
    @Override
    public void negative() throws CmdException {
//        testCantProcessFindWithoutParametersString();
    }

    private void testPrintTableData() {
        // given
        when(manager.getTableColumns("user"))
                .thenReturn(Arrays.asList("id", "name", "password"));

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        List<DataSet> data = Arrays.asList(user1, user2);
        when(manager.getTableData("user"))
                .thenReturn(data);

        // when
        if(command.run("find|user")){
            // then
            shouldPrint("[--------------------, " +
                    "|id|name|password|, " +
                    "--------------------, " +
                    "|12|Stiven|*****|, " +
                    "|13|Eva|+++++|, " +
                    "--------------------]");
        }
        else {

            System.out.println("Error!");
        }

    }

    private void testPrintEmptyTableData() {
        // given
        when(manager.getTableColumns("user"))
                .thenReturn(Arrays.asList("id", "name", "password"));

        when(manager.getTableData("user")).thenReturn(new ArrayList<DataSet>());

        // when
        command.run("find|user");

        // then
        shouldPrint("[--------------------, " +
                "|id|name|password|, " +
                "--------------------, " +
                "--------------------]");
    }

    private void testPrintTableDataWithOneColumn() {
        // given
        when(manager.getTableColumns("test"))
                .thenReturn(Arrays.asList("id"));

        DataSet user1 = new DataSetImpl();
        user1.put("id", 12);

        DataSet user2 = new DataSetImpl();
        user2.put("id", 13);

        List<DataSet> data = Arrays.asList(user1, user2);
        when(manager.getTableData("test")).thenReturn(data);

        // when
        command.run("find|test");

        // then
        shouldPrint("[--------------------, " +
                "|id|, " +
                "--------------------, " +
                "|12|, " +
                "|13|, " +
                "--------------------]");
    }

    private void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.run("find");

        // then
        assertFalse(canProcess);
    }
}
