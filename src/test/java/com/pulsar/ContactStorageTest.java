package com.pulsar;


import com.pulsar.model.Contact;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ContactStorageTest {

    private ContactStorage contactStorage;

    private static final String NAME = "Valid Name";
    private static final String PHONE_NUMBER = "8-800-555-35-35";
    private static final String EMAIL = "test@gmail.com";
    private static final String GROUP = "Family";
    private static final Contact VALID_CONTACT = new Contact(NAME, PHONE_NUMBER, EMAIL, GROUP);
    private static List<Contact> VALID_CONTACTS;

    @BeforeAll
    static void beforeAll() {
        VALID_CONTACTS = Stream.of(
                new Contact("User_1", "8-900-666-36-36", "test1@gmail.com", "test"),
                new Contact("User_2", "8-900-777-37-37", "test2@gmail.com", "Work"),
                new Contact("User_3", "8-900-333-53-53", "test3@gmail.com", "test"),
                new Contact("User_1", "8-900-222-32-32", "test4@gmail.com", "Family"),
                VALID_CONTACT
        ).collect(Collectors.toList());
    }

    @BeforeEach
    void init() {
        contactStorage = new ContactStorage();
    }

    @Test
    void addContactToStorageTest() {
        contactStorage.add(VALID_CONTACT);
        assertTrue(contactStorage.contains(VALID_CONTACT));
    }

    @Test
    void successfulFindingByName() {
        VALID_CONTACTS.forEach(contactStorage::add);
        List<Contact> contacts = contactStorage.findByName("User_1");
        assertThat(contacts).hasSize(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    void failedFindingByName(String name) {
        assertThrows(IllegalArgumentException.class, () -> contactStorage.findByName(name));
    }

    @Test
    void successfulFindingByPhoneNumber() {
        VALID_CONTACTS.forEach(contactStorage::add);
        Optional<Contact> contact = contactStorage.findByPhoneNumber(PHONE_NUMBER);
        assertTrue(contact.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    void failedFindingByPhoneNumber(String phoneNumber) {
        VALID_CONTACTS.forEach(contactStorage::add);
        assertThrows(IllegalArgumentException.class, () -> contactStorage.findByPhoneNumber(phoneNumber));
    }

    @Test
    void failedFindingByPhoneNumber() {
        VALID_CONTACTS.forEach(contactStorage::add);
        Optional<Contact> contact = contactStorage.findByPhoneNumber("7-777-777-77-77");
        assertTrue(contact.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("successfulDeletionArgumentsFactory")
    void successfulDeletionByNameAndPhoneNumber(String contactName, String phoneNumber) {
        VALID_CONTACTS.forEach(contactStorage::add);
        boolean deleted = contactStorage.delete(contactName, phoneNumber);
        assertTrue(deleted);
    }

    private static Stream<Arguments> successfulDeletionArgumentsFactory() {
        return VALID_CONTACTS.stream()
                .map(contact -> Arguments.of(contact.getName(), contact.getPhoneNumber()));
    }

    @ParameterizedTest
    @MethodSource("failedDeletionArgumentsFactory")
    void failedDeletionByNameAndPhoneNumber(String contactName, String phoneNumber) {
        VALID_CONTACTS.forEach(contactStorage::add);
        boolean deleted = contactStorage.delete(contactName, phoneNumber);
        assertFalse(deleted);
    }

    private static Stream<Arguments> failedDeletionArgumentsFactory() {
        return Stream.of(
                Arguments.of("Invalid Name", "0-000-000-00-00"),
                Arguments.of("Some name", "9-999-999-99-99")
        );
    }

    @ParameterizedTest
    @MethodSource("failedDeletionByEmptyArgumentsFactory")
    void failedDeletionByEmptyNameAndPhoneNumber(String contactName, String phoneNumber) {
        VALID_CONTACTS.forEach(contactStorage::add);
        assertThrows(IllegalArgumentException.class, () -> contactStorage.delete(contactName, phoneNumber));
    }

    private static Stream<Arguments> failedDeletionByEmptyArgumentsFactory() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("   ", "   "),
                Arguments.of("   ", ""),
                Arguments.of("", "   ")
        );
    }

    @Test
    void successfulDeleteAllByName() {
        VALID_CONTACTS.forEach(contactStorage::add);
        boolean deleted = contactStorage.deleteAll("User_1");
        assertTrue(deleted);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first invalid name", "second invalid name"})
    void failedDeleteAllByName(String contactName) {
        VALID_CONTACTS.forEach(contactStorage::add);
        boolean deleted = contactStorage.deleteAll(contactName);
        assertFalse(deleted);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    void failedDeleteAllByEmptyName(String contactName) {
        VALID_CONTACTS.forEach(contactStorage::add);
        assertThrows(IllegalArgumentException.class, () -> contactStorage.deleteAll(contactName));
    }

    @Test
    void successfulFindAllByGroup() {
        VALID_CONTACTS.forEach(contactStorage::add);
        List<Contact> contacts = contactStorage.findAllByGroup("test");
        assertThat(contacts).hasSize(2);
    }

    @Test
    void failedFindAllByGroup() {
        VALID_CONTACTS.forEach(contactStorage::add);
        List<Contact> contacts = contactStorage.findAllByGroup("invalid group");
        assertThat(contacts).isNullOrEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void failedFindAllByEmptyGroup(String groupName) {
        VALID_CONTACTS.forEach(contactStorage::add);
        assertThrows(IllegalArgumentException.class, () -> contactStorage.findAllByGroup(groupName));
    }
}
