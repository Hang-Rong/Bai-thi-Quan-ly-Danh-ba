package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contact {
    private String name;
    private String phone;
    private String address;
    private String email;
    private String group;
    private LocalDate birthdate;
    private String gender;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Contact(String name, String phone, String address, String email, String group,
                   LocalDate birthdate, String gender) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.group = group;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Phone number: " + phone +
                " , Group: " + group +
                " , Full name: " + name +
                " , Gender: " + gender +
                " , Birthday: " + birthdate.format(DATE_FORMATTER) +
                " , Address: " + address +
                " , Email: " + email;
    }
}
