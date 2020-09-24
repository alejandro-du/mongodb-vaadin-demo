package com.example;

import org.springframework.data.annotation.Id;

public class Student {

    @Id
    private Integer regNumber;
    private String firstName;
    private String lastName;

    public Student() {
    }

    public Student(Integer regNumber, String firstName, String lastName) {
        this.regNumber = regNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
