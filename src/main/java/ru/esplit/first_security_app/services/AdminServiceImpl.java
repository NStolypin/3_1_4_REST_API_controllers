package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PeopleRepository peopleRepository;
    private final RegistrationService registrationService;

    @Override
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Override
    public Optional<Person> getOnePerson(long id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateUser(long id, Person updatedUser) {
        Optional<Person> personForUpdated = getOnePerson(id);
        if (personForUpdated.isPresent()) {
            updatedUser.setId(id);
            registrationService.registerUser(updatedUser);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteUser(long id, long principalId) {
        if (id != principalId) {
            peopleRepository.deleteById(id);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void createUser(Person person) {
        registrationService.registerUser(person);
    }
}
