package dev.yudin.console;

import java.util.Scanner;

public interface Console {

    int readNumber(String prompt, Scanner scanner);

    String readName(String prompt, Scanner scanner);
}
