/*
package com.tsystems.project.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class InputValidator {

    @Bean
    public InputValidator transferService() {
        return new InputValidator();
    }

    public boolean verifyInputPassenger(String firstName, String lastName) {
        Pattern javaPattern = Pattern.compile("[\\d|\\s*]", Pattern.CASE_INSENSITIVE);
        Matcher first = javaPattern.matcher(firstName);
        Matcher last = javaPattern.matcher(lastName);
        return first.find() || last.find();
    }
}
*/
