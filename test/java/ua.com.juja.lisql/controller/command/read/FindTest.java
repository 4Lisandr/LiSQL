package controller.command.read;

import controller.command.CommandTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
//        testPrintEmptyTableData();
//        testPrintTableDataWithOneColumn();
//        testPrintTableData();
    }


    @Ignore
    @Override
    public void negative() throws CmdException {
//        testCantProcessFindWithoutParametersString();
    }

    private void testPrintEmptyTableData() {
        // given
        when(manager.getTableColumns("user"))
                .thenReturn(Arrays.asList("id", "name", "password"));

        when(manager.getTableData("user")).thenReturn(new ArrayList<DataSet>());

        // when
        command.runIfReady("find|user");

        // then
//        verify(view).write("[[id, name, password]]");
        shouldPrint("[[id, name, password]]");
    }

    @Test
    public void testPrintTableData() {
        //given
        when(manager.getTableColumns("users"))
                .thenReturn(Arrays.asList("id", "username", "password"));

        DataSet user1 = new DataSetImpl();
        user1.put("id", 10);
        user1.put("username", "Allan");
        user1.put("password", "*****");

        DataSet user2 = new DataSetImpl();
        user2.put("id", 11);
        user2.put("username", "Martial");
        user2.put("password", "+++===");

        when(manager.getTableData("users")).thenReturn(Arrays.asList(user1, user2));
        //when
        command.runIfReady("find|users");
        //then
        shouldPrint("[+--+--------+--------+\n" +
                "|id|username|password|\n" +
                "+--+--------+--------+\n" +
                "|10|Allan   |*****   |\n" +
                "+--+--------+--------+\n" +
                "|11|Martial |+++===  |\n" +
                "+--+--------+--------+]");
    }


    private void testPrintTableData2() {
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
        if (command.runIfReady("find|user")) {
            // then
            shouldPrint("[--------------------, " +
                    "|id|name|password|, " +
                    "--------------------, " +
                    "|12|Stiven|*****|, " +
                    "|13|Eva|+++++|, " +
                    "--------------------]");
        } else {

            System.out.println("Error!");
        }

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
        command.runIfReady("find|test");

        // then
        String expected = "[--------------------, " +
                "|id|, " +
                "--------------------, " +
                "|12|, " +
                "|13|, " +
                "--------------------]";

        shouldPrint(expected);
    }

    private void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.runIfReady("find");

        // then
        assertFalse(canProcess);
    }
}
