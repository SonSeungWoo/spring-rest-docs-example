package me.seungwoo.springrestdocsexample.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-10-03
 * Time: 10:46
 */
@Data
@Entity
@Builder
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String email;

    private LocalDateTime date;

    public Person() {

    }

    public Person(String name, String email, LocalDateTime date) {
        this.name = name;
        this.email = email;
        this.date = date;
    }
}
