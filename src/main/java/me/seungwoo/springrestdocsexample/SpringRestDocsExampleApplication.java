package me.seungwoo.springrestdocsexample;

import me.seungwoo.springrestdocsexample.person.Person;
import me.seungwoo.springrestdocsexample.person.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringRestDocsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestDocsExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository personRepository) {
        return args -> {
            personRepository.save(new Person("seungwoo", "seungwoo@test.com", LocalDateTime.now()));
            List<Person> persons = personRepository.findAll();
            System.out.println(persons);
        };
    }

}
