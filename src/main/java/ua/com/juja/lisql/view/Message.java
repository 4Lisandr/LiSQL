package ua.com.juja.lisql.view;

/**
 *
 */
public enum Message {
    HELLO ("Привет юзер!"),
    START ("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password"),
    INPUT ("Введи команду (или help для помощи):"),
    FAIL ("Неудача! по причине:"),
    RETRY ("Повтори попытку."),
    GOODBYE ("Пока!");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
