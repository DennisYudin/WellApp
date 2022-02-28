package dev.yudin.console;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Log4j
@Component
public class InputHandler implements Console {
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";
    private static final String ERROR_MESSAGE = "You should enter an integer value from 0 up to 30";
    private static final String INPUT_NULL_MESSAGE = "Input cannot be null";
    private static final String INPUT_EMPTY_MESSAGE = "Input cannot be empty";

    private static final byte MIN_ALLOWED_VALUE = 0;
    private static final byte MAX_ALLOWED_VALUE = 30;

    @Override
    public int readNumber(String prompt, Scanner scanner) {
        log.debug("Call method readNumber()");
        int value = 0;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();

                validateNumber(value);

                isGoodInput = true;
            } catch (InputMismatchException exception) {
                System.out.println(INCORRECT_INPUT_MESSAGE);
                scanner.nextLine();
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } while (!isGoodInput);
        log.debug("Value: " + value);
        return value;
    }

    private void validateNumber(int input) {
        if (input <= MIN_ALLOWED_VALUE || input > MAX_ALLOWED_VALUE) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    @Override
    public String readName(String prompt, Scanner scanner) {
        log.debug("Call method readName()");
        boolean isGoodInput = false;
        String userInput;
        do {
            System.out.print(prompt);

            userInput = scanner.nextLine();
            try {
                validateByNullOrEmpty(userInput);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);
        log.debug("userInput: " + userInput);
        return userInput;
    }

    private void validateByNullOrEmpty(String input) {
        if (input == null)
            throw new IllegalArgumentException(INPUT_NULL_MESSAGE);
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(INPUT_EMPTY_MESSAGE);
        }
    }
}

