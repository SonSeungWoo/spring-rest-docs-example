package me.seungwoo.springrestdocsexample.person;

import lombok.Data;

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

        private String email;
    }
}
