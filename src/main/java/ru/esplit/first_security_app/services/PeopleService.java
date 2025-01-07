package ru.esplit.first_security_app.services;

import ru.esplit.first_security_app.dto.PersonDTO;
import ru.esplit.first_security_app.models.Person;

public interface PeopleService {
    Person findOne(long id);

    long saveUser(Person person);

    Person convertToPerson(PersonDTO personDTO, boolean isApplyId);
}
