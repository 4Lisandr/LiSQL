package controller.command.utils;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.write.Clear;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void positive() {
        // when
        command.run("clear|user");
        // then
        verify(manager).clear("user");
        verify(view).write("Таблица user была успешно очищена.");
    }

    @Test
    public void negative() {
        assertFalse(command.run("clear"));
    }

}
