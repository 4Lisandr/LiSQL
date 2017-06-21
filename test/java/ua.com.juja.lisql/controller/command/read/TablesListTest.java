package controller.command.read;

import controller.command.CommandTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.read.TablesList;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL;

/**
 *
 */
public class TablesListTest extends CommandTest {

    @Override
    @Test
    public void positive() throws CmdException {

        List<String> data = new LinkedList<>();
        data.add("user");
        data.add("user1");

        when(manager.getTableNames()).thenReturn(data);
        runTablesList();
        shouldPrint("[[user, user1]]");
    }

    @Override
    @Ignore
    public void boundary() throws CmdException {

    }

    @Override
    @Test
    public void negative() throws CmdException {
        try {
            runTablesList();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(FAIL.toString(), e.getMessage());
        }
    }

    private void runTablesList() throws CmdException {
        command = new TablesList(manager, view);
        command.run("list");
    }

    @Captor
    private ArgumentCaptor<List<String>> listTable;

    @Override
    public void shouldPrint(String expected) {
        verify(view, atMost(2)).write(listTable.capture().toString(), anyObject());
        assertEquals(expected, listTable.getAllValues().toString());
    }
}