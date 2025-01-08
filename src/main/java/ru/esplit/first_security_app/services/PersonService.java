package ru.esplit.first_security_app.services;

import java.util.List;

import ru.esplit.first_security_app.dto.PersonDTO;
import ru.esplit.first_security_app.models.Person;

public interface PersonService {

    List<Person> getAllPeople();

    Person findOne(long id);
    
    void createUser(Person person);

    void updateUser(long id, Person updatedUser);

    void deleteUser(long id, long principalId);

    long saveUser(Person person);

    Person convertToPerson(PersonDTO personDTO, boolean isApplyId);
}
