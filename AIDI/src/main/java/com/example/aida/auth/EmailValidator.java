package com.example.aida.auth;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
@Component
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //ToDo: Regex to validate email
        return true;
    }
}
