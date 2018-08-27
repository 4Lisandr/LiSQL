package controller.command.connection;

import controller.command.CommandTest;
import org.junit.Test;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.connection.Connect;

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
        shouldPrint(String.format("[" + CONNECTED.toString() + "]", "sqlcmd", "postgres"));
    }


    @Test
    public void negative() throws CmdException {
        command = new Connect(manager, view);
        command.run("connect|qwer|ty|dfgfdg");
        shouldPrint(String.format("[" + FAIL_CONNECT.toString() + "]", "qwer", "ty", "dfgfdg"));
    }

}
