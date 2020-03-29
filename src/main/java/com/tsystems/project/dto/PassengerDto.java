package com.tsystems.project.dto;


import java.time.LocalDate;
import java.util.Objects;

public class PassengerDto {

    private long id;

    private String firstName;

    private String secondName;

    private LocalDate birthDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerDto that = (PassengerDto) o;
        return firstName.equals(that.firstName) &&
                secondName.equals(that.secondName) &&
                birthDate.equals(that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, birthDate);
    }
}
