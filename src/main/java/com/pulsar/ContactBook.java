package com.pulsar;

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
}
