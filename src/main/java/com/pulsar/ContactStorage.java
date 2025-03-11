package com.pulsar;

import com.pulsar.model.Contact;
import com.pulsar.util.Printer;

import java.util.*;

public class ContactStorage {

    private final List<Contact> orderedContacts;
    private final Set<Contact> contacts;
    private final Map<String, List<Contact>> groupedContacts;

    public ContactStorage() {
        this.orderedContacts = new ArrayList<>();
        this.contacts = new HashSet<>();
        this.groupedContacts = new HashMap<>();
    }

    public void add(Contact contact) {
        if (contains(contact)) {
            Printer.displayError("Данный контакт уже существует");
        } else {
            orderedContacts.add(contact);
            contacts.add(contact);
            groupedContacts.computeIfAbsent(contact.getGroup(), k -> new ArrayList<>())
                    .add(contact);
        }
    }

    public boolean contains(Contact contact) {
        return contact != null && contacts.contains(contact);
    }
}
