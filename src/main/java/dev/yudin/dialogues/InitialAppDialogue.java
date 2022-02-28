package dev.yudin.dialogues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InitialAppDialogue implements Dialogue {
    private static final String START_DIALOGUE = "Please choose a letter what do you want to do my Lord: \n"
            + "-------------------------------------------------------------\n"
            + "'a' if you want to add equipment to well \n"
            + "'b' if you want to see all information about equipments on wells \n"
            + "'c' if you want to create xml report about all information in database \n";
    private static final String USER_INPUT_MESSAGE = "Enter [a, b, or c]: ";
    private static final String REPEAT_MESSAGE = "Enter [yes] if you want to try again";
    private static final String USER_ANSWER = "Answer: ";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";
    private static final String ERROR_MESSAGE = "Unfortunately there is no such option.";

    private Dialogue equipmentDialogue;
    private Dialogue wellDialogue;
    private Dialogue xmlDialogue;

    private Map<String, Dialogue> dialogs = new HashMap<>();

    @Autowired
    public InitialAppDialogue(@Qualifier("equipmentDialogue") Dialogue equipmentDialogue,
                              @Qualifier("wellDialogue")Dialogue wellDialogue,
                              @Qualifier("xmlDialogue") Dialogue xmlDialogue) {
        this.equipmentDialogue = equipmentDialogue;
        this.wellDialogue = wellDialogue;
        this.xmlDialogue = xmlDialogue;
    }

    @Override
    public void start(Scanner scanner) {

        String userAnswer;
        do {
            System.out.print(START_DIALOGUE);

            initializeDialogues();

            startDialogue(scanner);

            System.out.println(REPEAT_MESSAGE);
            System.out.print(USER_ANSWER);

            userAnswer = scanner.nextLine().toLowerCase();
            System.out.println();

        } while ("yes".equals(userAnswer));
    }

    private void initializeDialogues() {
        dialogs.put("a", equipmentDialogue);
        dialogs.put("b", wellDialogue);
        dialogs.put("c", xmlDialogue);
    }

    private void startDialogue(Scanner scanner) {

        List<String> options = new ArrayList<>();

        options.addAll(dialogs.keySet());

        int counter = 0;
        String userInput;
        do {
            System.out.print(USER_INPUT_MESSAGE);

            userInput = scanner.nextLine().toLowerCase();

            if (!options.contains(userInput)) {
                counter++;
                System.out.println(INCORRECT_INPUT_MESSAGE);
            }
            if (counter == 10) {
                throw new IllegalArgumentException(ERROR_MESSAGE);
            }
        } while (!options.contains(userInput));

        dialogs.get(userInput).start(scanner);
    }
}
