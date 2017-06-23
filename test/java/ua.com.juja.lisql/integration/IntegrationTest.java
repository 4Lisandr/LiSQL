package integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.lisql.Main;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.model.PostgreSQLManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new PostgreSQLManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() {
        // given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет, дорогой юзер! Будь добр ввести имя базы данных, пользователя и пароль в формате: connect|database|userName|password\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Спикок команд:\r\n" +
                "\tconnect - подключение к базе данных (connect|sqlcmd|postgres|HcxbPRi5EoNB)\r\n" +
                "\thelp - для получения справки\r\n" +
                "\texit - выход\r\n" +
                "\tlist - список всех баз данных\r\n" +
                "\tfind - найти записи (find|getSample)\r\n" +
                "\tinsert - вставить запись в базу данных (insert|tableName|column1|value1)\r\n" +
                "\tupdate - обновить БД (update|tableName|column1|value1|column2|value2)\r\n" +
                "\tclear - очистить таблицу (clear|users)\r\n" +
                "\tdelete - удалить строку из таблицы с условием (delete|users|id=10)\r\n" +
                "\tcreate - создать БД (create|tableName|column1|column2|...|columnN)\r\n" +
                "\tdrop - удалить БД (drop|users)\r\n" +
                "\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Пока, приходи еще!\r\n", getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет, дорогой юзер! Будь добр ввести имя базы данных, пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда list не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Пока, приходи еще!\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // find|user
                "Вы не можете пользоваться командой 'find|user' пока не подключитесь с помощью комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupported() {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // unsupported
                "Вы не можете пользоваться командой 'unsupported' пока не подключитесь с помощью комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // unsupported
                "Несуществующая команда: unsupported\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // list
                "[user, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // connect
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // find|user
                "--------------------\r\n" +
                "|name|password|id|\r\n" +
                "--------------------\r\n" +
                "--------------------\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("list");
        in.add("connect|test|postgres|postgres");
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // connect sqlcmd
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // list
                "[user, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // connect test
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // list
                "[qwe]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|sqlcmd");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                // connect sqlcmd
                "Неудача! по причине: Неверно количество параметров разделенных знаком '|', ожидается 4, но есть: 2\r\n" +
                "Повтори попытку.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                // exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect_withData() {
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("clear|user");
        in.add("create|user|id|13|name|Stiven|password|*****");
        in.add("create|user|id|14|name|Eva|password|+++++");
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет, дорогой юзер! Будь добр ввести имя базы данных, пользователя и пароль в формате: connect|database|userName|password\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Ошибка по причине: Couldn't getText connection for database:sqlcmd user:postgres. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда clear|user не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда create|user|id|13|name|Stiven|password|***** не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда create|user|id|14|name|Eva|password|+++++ не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда find|user не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Пока, приходи еще!", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет, дорогой юзер! Будь добр ввести имя базы данных, пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Ошибка по причине: Couldn't getText connection for database:sqlcmd user:postgres. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда clear|sadfasd|fsf|fdsf не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Пока, приходи еще!\r\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("create|user|error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет, дорогой юзер! Будь добр ввести имя базы данных, пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Ошибка по причине: Couldn't getText connection for database:sqlcmd user:postgres. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Команда create|user|error не смогла выполниться без подклюкения к БД (connect|database|userName|password)\r\n" +
                "Ошибка по причине: missed connection. Попробуй еще раз.\r\n" +
                "Введи команду (или help для чтения списка команд):\r\n" +
                "Пока, приходи еще!\r\n", getData());
    }
}
