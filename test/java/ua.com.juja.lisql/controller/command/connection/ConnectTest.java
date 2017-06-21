package controller.command.connection;

import controller.command.CommandTest;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.connection.Connect;

import static org.junit.Assert.assertEquals;
import static ua.com.juja.lisql.controller.command.TextBundle.CONNECTED;
import static ua.com.juja.lisql.controller.command.TextBundle.FAIL_CONNECT;

public class ConnectTest extends CommandTest {

    @Test
    public void positive() throws CmdException {
        //given
        command = new Connect(manager, view);
        //when
        command.run("connect|sqlcmd|postgres|HcxbPRi5EoNB");
        //then
        shouldPrint(String.format(CONNECTED.toString(), "sqlcmd", "postgres"));
    }

    @Ignore
    @Override
    public void boundary() throws CmdException {}

    @Ignore //todo negative test connect
    @Test
    public void negative() throws CmdException {
        try {
            command = new Connect(manager, view);
            command.run("connect|qwer|ty");
//            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertEquals(String.format(FAIL_CONNECT.toString(), "qwer", "ty", ""), e.getMessage());
        }
    }

}
