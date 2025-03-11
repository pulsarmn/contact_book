package com.pulsar;

import com.pulsar.model.Contact;
import com.pulsar.util.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class ContactStorage {

    private final List<Contact> orderedContacts;
    private final Set<Contact> contacts;
    private final Map<String, List<Contact>> groupedContacts;

    private static final String EMPTY_CONTACT_NAME = "Имя контакта не может быть пустым!";
    private static final String EMPTY_PHONE_NUMBER = "Номер телефона не может быть пустым!";

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

    public List<Contact> findByName(String contactName) {
        validate(contactName, EMPTY_CONTACT_NAME);

        return contacts.stream()
                .filter(contact -> contact.getName().toLowerCase().contains(contactName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Contact> findByPhoneNumber(String phoneNumber) {
        validate(phoneNumber, EMPTY_PHONE_NUMBER);

        return contacts.stream()
                .filter(contact -> contact.getPhone().equals(phoneNumber))
                .findFirst();
    }

    public boolean delete(String contactName, String phoneNumber) {
        validate(contactName, EMPTY_CONTACT_NAME);
        validate(phoneNumber, EMPTY_PHONE_NUMBER);

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

    public boolean deleteAll(String contactName) {
        validate(contactName, EMPTY_CONTACT_NAME);

        boolean deleted = false;

        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();

            if (contact.getName().equals(contactName)) {
                orderedContacts.remove(contact);
                groupedContacts.get(contact.getGroup()).remove(contact);
                iterator.remove();
                deleted = true;
            }
        }

        return deleted;
    }

    public void printContacts() {
        Iterator<Contact> iterator = orderedContacts.iterator();

        if (iterator.hasNext()) {
            Printer.displaySuccess("Список ваших контактов:");
        } else {
            Printer.displayError("Телефонная книга пуста!");
        }

        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            System.out.println(contact);
        }
    }

    public void printByGroup(String groupName) {
        validate(groupName, "Имя группы не может быть пустым!");

        List<Contact> contacts = groupedContacts.get(groupName);
        if (contacts == null) {
            Printer.displayError("Группа %s не существует".formatted(groupName));
        } else if (contacts.isEmpty()) {
            Printer.displayError("В группе %s нет контактов".formatted(groupName));
        } else {
            Iterator<Contact> iterator = contacts.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }

    public boolean contains(Contact contact) {
        return contact != null && contacts.contains(contact);
    }

    private void validate(String param, String message) {
        if (param == null || param.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public void clear() {
        orderedContacts.clear();
        contacts.clear();
        groupedContacts.clear();
    }
}
