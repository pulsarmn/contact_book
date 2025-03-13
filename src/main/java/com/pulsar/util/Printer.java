package com.pulsar.util;

import java.io.PrintStream;

public class Printer {

    private static final PrintStream OUTPUT = System.out;

    private Printer() {
    }

    public static void displayMainMenu() {
        OUTPUT.println("1. Добавить контакт");
        OUTPUT.println("2. Удалить контакт");
        OUTPUT.println("3. Посмотреть все контакты");
        OUTPUT.println("4. Найти контакты");
        OUTPUT.println("5. Найти контакт");
        OUTPUT.println("6. Посмотреть контакты по группе");
        OUTPUT.println("0. Выход");
    }

    public static void inputRequest() {
        OUTPUT.print("> ");
    }

    public static void displayInputError() {
        displayColoredMessage("Некорректный ввод! Попробуйте ещё раз!", ColorCode.BOLD_RED);
    }

    public static void displayError(String message) {
        displayColoredMessage(message, ColorCode.BOLD_RED);
    }

    public static void displaySuccess(String message) {
        displayColoredMessage(message, ColorCode.BOLD_GREEN);
    }

    private static void displayColoredMessage(String message, ColorCode color) {
        OUTPUT.println(color + message + ColorCode.RESET);
    }

    public static void display(String message) {
        OUTPUT.println(message);
    }

    private enum ColorCode {
        BOLD_RED("\033[1;31m"),
        BOLD_GREEN("\033[1;32m"),
        RESET("\033[0m");

        private final String value;

        ColorCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
