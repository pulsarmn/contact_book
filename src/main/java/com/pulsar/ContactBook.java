package com.pulsar;

import com.pulsar.exception.InvalidContactException;
import com.pulsar.model.Contact;
import com.pulsar.util.Printer;
import com.pulsar.validation.ContactValidator;

import java.util.*;

public class ContactBook {

    private final Scanner terminal;
    private final ContactStorage contactStorage;
    private final ContactValidator contactValidator;
    private boolean isRunning = false;

    private static final String INPUT_CONTACT_NAME = "Введите название контакта:";

    public ContactBook() {
        this(new ContactStorage());
    }

    public ContactBook(ContactStorage contactStorage) {
        this.contactStorage = contactStorage;
        this.terminal = new Scanner(System.in);
        this.contactValidator = new ContactValidator();
    }

    public void start() {
        isRunning = true;
        while (isRunning) {
            Printer.displayMainMenu();
            Printer.inputRequest();

            Command command;

            try {
                command = Command.get(terminal.nextLine());
            } catch (IllegalArgumentException e) {
                Printer.displayError(e.getMessage());
                continue;
            }

            processCommand(command);
        }
    }

    private void processCommand(Command command) {
        switch (command) {
            case ADD_CONTACT -> addContact();
            case DELETE_CONTACT -> deleteContact();
            case VIEW_ALL_CONTACTS -> viewContacts();
            case FIND_CONTACTS -> findContacts();
            case FIND_CONTACT -> findSingleContact();
            case PRINT_CONTACTS_BY_GROUP -> findContactsByGroup();
            case EXIT -> {
                isRunning = false;
                contactStorage.clear();
                terminal.close();
            }
        }
    }

    private void addContact() {
        Printer.display(INPUT_CONTACT_NAME);
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

            contactValidator.validate(contact);
            contactStorage.add(contact);

            Printer.displaySuccess("Контакт успешно добавлен!");
        } catch (InvalidContactException e) {
            e.getErrors().forEach(Printer::displayError);
        } catch (Exception e) {
            Printer.displayError(e.getMessage());
        }
    }

    private void deleteContact() {
        Printer.display(INPUT_CONTACT_NAME);
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

    private void findContacts() {
        Printer.display(INPUT_CONTACT_NAME);
        Printer.inputRequest();
        String contactName = terminal.nextLine();

        List<Contact> contacts;

        try {
            contacts = contactStorage.findByName(contactName);
        } catch (Exception e) {
            Printer.displayError(e.getMessage());
            return;
        }

        if (contacts.isEmpty()) {
            Printer.displayError("Контакты не найдены!");
        } else {
            Printer.displaySuccess("Результат поиска:");
            contacts.forEach(System.out::println);
        }
    }

    private void findSingleContact() {
        Printer.display("Введите номер телефона:");
        Printer.inputRequest();
        String phoneNumber = terminal.nextLine();

        Optional<Contact> contact;

        try {
            contact = contactStorage.findByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            Printer.displayError(e.getMessage());
            return;
        }

        if (contact.isEmpty()) {
            Printer.displayError("Контакт по номеру %s не найден!".formatted(phoneNumber));
        } else {
            Printer.displaySuccess("Результат поиска:");
            contact.ifPresent(System.out::println);
        }
    }

    private void findContactsByGroup() {
        Printer.display("Введите название группы:");
        Printer.inputRequest();
        String groupName = terminal.nextLine();

        List<Contact> contacts = contactStorage.findAllByGroup(groupName);
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
