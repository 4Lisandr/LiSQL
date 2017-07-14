package controller.command.read;

import controller.command.CommandTest;
import org.junit.Test;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.read.TablesList;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
    @Test
    public void negative() throws CmdException {
        //todo test
        try {
            runTablesList();
        } catch (IllegalArgumentException e) {
            assertEquals(FAIL.toString(), e.getMessage());
        }
    }

    private void runTablesList() throws CmdException {
        command = new TablesList(manager, view);
        command.run("list");
    }


}