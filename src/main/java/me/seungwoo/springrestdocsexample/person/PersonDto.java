package me.seungwoo.springrestdocsexample.person;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Created by Leo.
 * User: sonseungwoo
 * Date: 2019-10-03
 * Time: 14:00
 */
public class PersonDto {

    @Data
    public static class PersonCreate {
        private String name;

        @Size(max = 50)
        private String email;
    }
}
