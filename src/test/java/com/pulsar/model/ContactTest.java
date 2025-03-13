package com.pulsar.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactTest {

    private static final String NAME = "Contact Name";
    private static final String PHONE_NUMBER = "8-800-555-35-35";
    private static final String EMAIL = "test@yadnex.ru";
    private static final String GROUP = "Family";

    @ParameterizedTest
    @MethodSource("successfulCreationArgumentFactory")
    void successfulCreationTest(String name, String phoneNumber, String email, String group) {
        Contact contact = new Contact(name, phoneNumber, email, group);
        assertNotNull(contact);
    }

    private static Stream<Arguments> successfulCreationArgumentFactory() {
        return Stream.of(
                Arguments.of(NAME, PHONE_NUMBER, EMAIL, GROUP),
                Arguments.of(NAME, PHONE_NUMBER, null, GROUP)
        );
    }

    @ParameterizedTest
    @MethodSource("failedCreationArgumentFactory")
    void failedCreationTest(String name, String phoneNumber, String group) {
        assertThrows(IllegalArgumentException.class, () -> new Contact(name, phoneNumber, EMAIL, group));
    }

    private static Stream<Arguments> failedCreationArgumentFactory() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, null, GROUP),
                Arguments.of(NAME, null, null),
                Arguments.of(NAME, PHONE_NUMBER, null)
        );
    }
}
