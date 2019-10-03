package me.seungwoo.springrestdocsexample.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-10-03
 * Time: 10:48
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);
}
