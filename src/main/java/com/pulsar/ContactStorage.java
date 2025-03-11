package com.pulsar;

import com.pulsar.exception.ContactNotFoundException;
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

    public boolean delete(String contactName, String phoneNumber) throws ContactNotFoundException {
        if (contactName == null || phoneNumber == null || contactName.isBlank() || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Имя контакта и номер телефона не могут быть пустыми!");
        }

        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();

            if (contact.getName().equals(contactName) && contact.getPhone().equals(phoneNumber)) {
                orderedContacts.remove(contact);
                groupedContacts.get(contact.getGroup()).remove(contact);
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean contains(Contact contact) {
        return contact != null && contacts.contains(contact);
    }
}
