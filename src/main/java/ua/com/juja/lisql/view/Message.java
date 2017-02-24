package ua.com.juja.lisql.view;

/**
 *  Lisandr 23.02.17
 */
public enum Message { //todo реализовать мультиязычность
    HELLO ("Привет юзер!"),
    START ("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password"),
    INPUT ("Введи команду (или help для помощи):"),

    FAIL ("Неудача! по причине:"),
    SUCCESS ("Успех!"),
    /**
     *   String format here!
     **/
    FAILED_COUNT ("Неверное число параметров, разделенных знаком '|', ожидается %s, но указано: %s"),
    DISCONNECTED ("Невозможно выполнить комманду '%s' без подключения (connect|dbName|password)"),

    RETRY ("Повтори попытку."),
    UNKNOWN ("Несуществующая команда:"),

    GOODBYE ("Пока!");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
