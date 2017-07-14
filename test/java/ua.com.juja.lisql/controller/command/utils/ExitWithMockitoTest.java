package controller.command.utils;

import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.utils.Exit;
import ua.com.juja.lisql.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class ExitWithMockitoTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString() {
        // given
        Command command = new Exit(null, view);
        // when  // then
        assertTrue(command.run("exit"));

    }

    @Test
    public void testCantProcessQweString() {
        // given
        Command command = new Exit(null, view);

        // when // then
        assertFalse(command.run("qwe"));
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
        Mockito.verify(view).write("До скорой встречи!");
    }
}
