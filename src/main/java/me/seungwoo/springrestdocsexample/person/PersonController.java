package me.seungwoo.springrestdocsexample.person;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-10-03
 * Time: 10:44
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final ModelMapper modelMapper;

    private final PersonRepository personRepository;

    @GetMapping
    public List<Person> getPersonAll() {
        List<Person> personList = personRepository.findAll();
        return personList;
    }

    @GetMapping("/{name}")
    public Person getPerson(@PathVariable String name) {
        Optional<Person> person = personRepository.findByName(name);
        return person.orElse(new Person());
    }

    @PostMapping
    public void createPerson(@RequestBody PersonDto.PersonCreate person) {
        personRepository.save(modelMapper.map(person, Person.class));
    }
}
