package com.pulsar.model;

import java.util.Objects;

public class Contact {

    private String name;
    private String phone;
    private String email;
    private String group;

    public Contact(String name, String phone, String email, String group) {
        this.email = email;
        this.group = group;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.getName()) && phone.equals(contact.getPhone()) && email.equals(contact.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, group);
    }

    @Override
    public String toString() {
        return "%s | %s | %s".formatted(name, phone, email);
    }
}
