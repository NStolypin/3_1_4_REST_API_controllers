package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import ru.esplit.first_security_app.models.Person;

public interface AdminService {

    List<Person> getAllPeople();

    Optional<Person> getOnePerson(long id);

    void updateUser(long id, Person updatedUser);

    void deleteUser(long id, long principalId);

    void createUser(Person person);
}
