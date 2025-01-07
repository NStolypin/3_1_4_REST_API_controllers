package ru.esplit.first_security_app.util;

public class PersonDoNotDeleteException extends RuntimeException {
    public PersonDoNotDeleteException(String msg) {
        super(msg);
    }
}
