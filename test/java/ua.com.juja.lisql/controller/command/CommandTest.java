package controller.command;

import controller.ConfigTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import ua.com.juja.lisql.controller.command.CmdException;
import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class CommandTest implements ConfigTest {

    protected View view;
    protected Command command;
    protected DatabaseManager manager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        manager = mock(DatabaseManager.class);
        view = mock(View.class);

        when(manager.canConnect(DB_NAME, USER, PASSWORD)).thenReturn(true);
        when(manager.isConnected()).thenReturn(true);
    }

    @Test
    public abstract void positive() throws CmdException;

    @Test
    public abstract void boundary() throws CmdException;

    @Test
    public abstract void negative() throws CmdException;

    public void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

}
