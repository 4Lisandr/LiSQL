package ua.com.juja.lisql.view;

/**
 *  Lisandr 23.02.17
 */
public enum EMessage { //todo реализовать мультиязычность - создать интерфейс, содержащий Enum
    HELLO ("Привет юзер!"),
    START ("Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password"),
    INPUT ("Введи команду (или help для помощи):"),
    HELP ("Помощь близко, вы там держитесь..."),

    FAIL ("Неудача! по причине:"),
    SUCCESS ("Успех!"),
    /**
     *   String format here!
     **/
    FAILED_COUNT ("Неверное число параметров, разделенных знаком '|', ожидается %s, но указано: %s"),
    TO_MANY_PARAMETERS ("Задано слишком много параметров, обработано '%s', остальные - проигнорированны"),
    ODD_PARAMETERS ("Должно быть четное количество параметров в формате " +
        "create|tableName|column1|value1|column2|value2|...|columnN|valueN', но указано: '%s'"),
    SUCCESS_RECORD ("Запись %s была успешно создана в таблице '%s'."),
    DISCONNECTED ("Невозможно выполнить комманду '%s' без подключения (connect|database|userName|password)"),

    RETRY ("Повтори попытку."),
    UNKNOWN ("Несуществующая команда:"),

    GOODBYE ("Пока!");

    private final String message;

    EMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
