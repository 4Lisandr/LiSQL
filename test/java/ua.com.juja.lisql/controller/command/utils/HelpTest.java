package controller.command.utils;

import controller.command.CommandTest;
import org.junit.Before;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.utils.Help;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class HelpTest extends CommandTest {


    @Before
    @Override
    public void init() {
        super.init();
        command = new Help(view);
    }

    @Override
    public void positive() throws CmdException {
        testCanProcessWithoutParameters();
        testProcess();
    }

    @Override
    public void negative() throws CmdException {
        testCantProcessWithWrongCommand();
    }

    private void testCanProcessWithoutParameters() {
        //when
        boolean result = command.canProcess("help");
        //then
        assertTrue(result);
    }


    private void testProcess() {
        //when
        command.runIfReady("help");
        //then
        verify(view).write("Список команд:");
    }

    private void testCantProcessWithWrongCommand() {
        //when
        boolean result = command.canProcess("helpCommand");
        //then
        assertFalse(result);
    }

}
