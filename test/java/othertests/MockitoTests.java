package othertests;

/**
 *  Thanks to habrauser @denixx https://habrahabr.ru/post/243155/
 */
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTests {
    @Test
    public void iterator_will_return_hello_world() {
        //подготавливаем
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello ").thenReturn("World");
        //выполняем
        String result = i.next() + "" + i.next();
        //сравниваем
        assertEquals("Hello World", result);
    }

    @Test
    public void with_arguments() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo("Test")).thenReturn(1);
        assertEquals(1, c.compareTo("Test"));
    }

    @Test
    public void with_unspecified_arguments() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        assertEquals(-1, c.compareTo(new String[]{"s5.01", null}));
    }

    @Test(expected = IOException.class)
    public void OutputStreamWriter_rethrows_an_exception_from_OutputStream()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        doThrow(new IOException()).when(mock).close();
        osw.close();
    }

    @Test
    public void OutputStreamWriter_Closes_OutputStream_on_Close()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        osw.close();
        verify(mock).close();
    }

    @Test
    public void OutputStreamWriter_Buffers_And_Forwards_To_OutputStream()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        osw.write('a');
        osw.flush();
        // не можем делать так, потому что мы не знаем,
        // насколько длинным может быть массив
        // verify(mock).write(new byte[]{'a'},0,1);

        BaseMatcher<byte[]> arrayStartingWithA = new BaseMatcher<byte[]>() {
            @Override
            public void describeTo(Description description) {
                // пустота
            }

            // Проверяем, что первый символ - это A
            @Override
            public boolean matches(Object item) {
                byte[] actual = (byte[]) item;
                return actual[0] == 'a';
            }
        };
        // проверяем, что первый символ массива - это A, и что другие два аргумента равны 0 и 1.
        verify(mock).write(argThat(arrayStartingWithA), eq(0), eq(1));
    }
}