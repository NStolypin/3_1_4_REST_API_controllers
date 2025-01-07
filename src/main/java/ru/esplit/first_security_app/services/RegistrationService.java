package ru.esplit.first_security_app.services;

import ru.esplit.first_security_app.models.Person;

public interface RegistrationService {
    long registerUser(Person person);
}