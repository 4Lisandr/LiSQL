package controller.command;

import org.junit.Test;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.write.Clear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClearTest extends CommandTest {

    @Test
    public void positive() throws CmdException {
        //given
        command = new Clear(manager, view);
        //when
        command.run("clear|test");
        //then
        shouldPrintView("[Таблица usr очищена!]");
    }

    @Test
    public void boundary() {}

    @Test
    public void negative() throws CmdException {

        try {
            command = new Clear(manager, view);
            command.run("clear|");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

}
