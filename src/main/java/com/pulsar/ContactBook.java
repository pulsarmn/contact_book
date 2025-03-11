package com.pulsar;

import com.pulsar.model.Contact;
import com.pulsar.util.Printer;

import java.util.Arrays;
import java.util.Scanner;

public class ContactBook {

    private final Scanner terminal;
    private final ContactStorage contactStorage;
    private boolean isRunning = false;

    public ContactBook() {
        this(new ContactStorage());
    }

    public ContactBook(ContactStorage contactStorage) {
        this.contactStorage = contactStorage;
        this.terminal = new Scanner(System.in);
    }

    private void addContact() {
        Printer.display("Введите название контакта:");
        Printer.inputRequest();
        String contactName = terminal.nextLine();

        Printer.display("Введите номер телефона:");
        Printer.inputRequest();
        String phoneNumber = terminal.nextLine();

        Printer.display("Введите e-mail контакта:");
        Printer.inputRequest();
        String email = terminal.nextLine();

        Printer.display("Введите группу:");
        Printer.inputRequest();
        String group = terminal.nextLine();

        try {
            Contact contact = new Contact(contactName, phoneNumber, email, group);
            contactStorage.add(contact);
            Printer.displaySuccess("Контакт успешно добавлен!");
        } catch (Exception e) {
            Printer.displayError(e.getMessage());
        }
    }

    private void deleteContact() {
        Printer.display("Введите название контакта:");
        Printer.inputRequest();
        String contactName = terminal.nextLine();

        Printer.display("Введите номер телефона(необязательно):");
        Printer.inputRequest();
        String phoneNumber = terminal.nextLine();

        if (contactName == null || contactName.isBlank()) {
            Printer.displayError("Имя контакта не должно быть пустым!");
            return;
        }

        boolean hasBeenDeleted;

        if (phoneNumber == null || phoneNumber.isBlank()) {
            hasBeenDeleted = contactStorage.deleteAll(contactName);
        } else {
            hasBeenDeleted = contactStorage.delete(contactName, phoneNumber);
        }

        if (hasBeenDeleted) {
            Printer.displaySuccess("Контакт(ы) успешно удален(ы)!");
        } else {
            Printer.displayError("Контакт не был удалён!");
        }
    }

    private void viewContacts() {
        contactStorage.printContacts();
    }

    private enum Command {
        ADD_CONTACT("1"),
        DELETE_CONTACT("2"),
        VIEW_ALL_CONTACTS("3"),
        FIND_CONTACTS("4"),
        FIND_CONTACT("5"),
        PRINT_CONTACTS_BY_GROUP("6"),
        EXIT("0");

        private final String value;

        Command(String value) {
            this.value = value;
        }

        public static Command get(String command) {
            return Arrays.stream(values())
                    .filter(it -> it.value.equals(command))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Некорректный ввод!"));
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
