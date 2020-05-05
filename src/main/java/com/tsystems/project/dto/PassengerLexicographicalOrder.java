package com.tsystems.project.dto;

import java.util.Comparator;

/**
 * author Vitalii Nefedov
 */
public class PassengerLexicographicalOrder implements Comparator<PassengerDto> {

    @Override
    public int compare(PassengerDto p1, PassengerDto p2) {
        int firstName = p1.getFirstName().compareTo(p2.getFirstName());
        if (firstName != 0) {
            return firstName;
        }
        int secondName = p1.getSecondName().compareTo(p2.getSecondName());
        if (secondName != 0) {
            return secondName;
        }
        return p1.getBirthDate().compareTo(p2.getBirthDate());
    }
}

