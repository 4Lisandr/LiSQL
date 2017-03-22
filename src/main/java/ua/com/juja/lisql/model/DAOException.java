package ua.com.juja.lisql.model;

class DAOException extends RuntimeException {

    DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
