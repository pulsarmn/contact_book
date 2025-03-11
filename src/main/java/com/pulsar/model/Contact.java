package com.pulsar.model;

import java.util.Objects;

public class Contact {

    private String name;
    private String phoneNumber;
    private String email;
    private String group;

    public Contact(String name, String phoneNumber, String email, String group) {
        validate(name);
        validate(phoneNumber);
        validate(group);

        this.email = email;
        this.group = group;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void validate(String param) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException("Невозможно создать контакт с такими параметрами!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.getName()) && phoneNumber.equals(contact.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, group);
    }

    @Override
    public String toString() {
        return "%s | %s | %s | %s".formatted(name, phoneNumber, email, group);
    }
}
