package com.pulsar.validation;

import com.pulsar.exception.InvalidContactException;
import com.pulsar.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactValidator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.\\w+$";
    private static final String PHONE_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public void validate(Contact contact) {
        List<String> errors = new ArrayList<>();

        if (contact.getName() == null || contact.getName().isBlank()) {
            errors.add("Имя не может быть пустым!");
        }
        if (contact.getPhoneNumber() == null || contact.getPhoneNumber().isBlank() || !contact.getPhoneNumber().matches(PHONE_NUMBER_PATTERN)) {
            errors.add("Недопустимое значение номера телефона: %s".formatted(contact.getPhoneNumber()));
        }
        if (contact.getEmail() != null && !contact.getEmail().matches(EMAIL_PATTERN)) {
            errors.add("Недопустимый формат email: %s".formatted(contact.getEmail()));
        }
        if (contact.getGroup() == null || contact.getGroup().isBlank()) {
            errors.add("Группа не может быть пустой!");
        }

        if (!errors.isEmpty()) {
            throw new InvalidContactException(errors);
        }
    }
}
