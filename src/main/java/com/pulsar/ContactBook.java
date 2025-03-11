package com.pulsar;

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
