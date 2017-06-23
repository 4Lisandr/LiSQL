package controller.command.utils;

import org.junit.Test;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.utils.Exit;
import ua.com.juja.lisql.view.Console;
import ua.com.juja.lisql.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class ExitTest {
    View view = new Console();

    @Test
    public void testCanProcessExitString() {
        // given
        Command command = new Exit(null, view);

        // when
        boolean canProcess = command.run("exit");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        // given
        Command command = new Exit(null,  view);

        // when
        boolean canProcess = command.run("qwe");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_thowsExitException() {
        // given
        Command command = new Exit(null, view);

        // when
        try {
            command.run("exit");
            fail("Expected ExitException");
        } catch (Exception e) {
            // do nothing
        }

        // then
//        assertEquals("До скорой встречи!\n", view.getContent());
        // throws ExitException
    }
}
