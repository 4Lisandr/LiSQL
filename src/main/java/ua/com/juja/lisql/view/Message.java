package ua.com.juja.lisql.view;

/**
 *  Lisandr 23.02.17
 */
public enum Message { //todo реализовать мультиязычность
    HELLO ("Привет юзер!"),
    START ("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password"),
    INPUT ("Введи команду (или help для помощи):"),
    FAIL ("Неудача! по причине:"),
    RETRY ("Повтори попытку."),
    GOODBYE ("Пока!"),
    UNKNOWN ("Несуществующая команда:");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
