package ru.esplit.first_security_app.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {
    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Длина имени должна быть от 2 до 100 символов")
    private String username;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;

    @NotEmpty(message = "Пароль не должно быть пустым")
    private String password;
    private List<RoleDTO> roles;

    public List<PersonDTO> getPersonDTOList(List<Person> personList) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList) {
            roles = new ArrayList<>();
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(person.getId());
            personDTO.setUsername(person.getUsername());
            personDTO.setYearOfBirth(person.getYearOfBirth());
            for (Role role : person.getRoles()) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(role.getId());
                roles.add(roleDTO);
            }
            personDTO.setRoles(roles);
            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    public PersonDTO getPersonDTO(Person person) {
        roles = new ArrayList<>();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setUsername(person.getUsername());
        personDTO.setYearOfBirth(person.getYearOfBirth());
        // personDTO.setPassword(person.getPassword());
        for (Role role : person.getRoles()) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roles.add(roleDTO);
        }
        personDTO.setRoles(roles);
        return personDTO;
    }
}
