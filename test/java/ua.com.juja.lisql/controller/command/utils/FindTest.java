package controller.command.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.read.Find;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DataSetImpl;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(manager, view);
    }

    @Test
    public void testPrintTableData() {
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
        command.run("find|user");

        // then
        shouldPrint("[--------------------, " +
                    "|id|name|password|, " +
                    "--------------------, " +
                    "|12|Stiven|*****|, " +
                    "|13|Eva|+++++|, " +
                    "--------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.run("find");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
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

    @Test
    public void testPrintTableDataWithOneColumn() {
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
}
