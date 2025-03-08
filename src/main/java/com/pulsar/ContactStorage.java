package com.pulsar;

import com.pulsar.model.Contact;

import java.util.*;

public class ContactStorage {

    private final List<Contact> orderedContacts;
    private final Set<Contact> contacts;
    private final Map<String, Contact> groupedContacts;

    public ContactStorage() {
        this.orderedContacts = new ArrayList<>();
        this.contacts = new HashSet<>();
        this.groupedContacts = new HashMap<>();
    }

    public boolean contains(Contact contact) {
        return contact != null && contacts.contains(contact);
    }
}
