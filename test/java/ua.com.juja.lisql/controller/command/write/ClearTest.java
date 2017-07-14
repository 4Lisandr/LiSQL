package controller.command.write;

import controller.command.CommandTest;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.write.Clear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class ClearTest extends CommandTest {

    @Override
    public void init() {
        super.init();
        //given
        command = new Clear(manager, view);
    }

    @Test
    public void positive() throws CmdException {
        //when
        when(view.confirm()).thenReturn(true);
        command.run("clear|test");
        //then
        simplePrint("now table test is empty");
//        shouldPrint("[now table test is empty]");
    }

    @Test
    public void boundary() {}

    @Ignore
    @Test
    public void negative() throws CmdException {

        try {
            command.run("clear|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

}
