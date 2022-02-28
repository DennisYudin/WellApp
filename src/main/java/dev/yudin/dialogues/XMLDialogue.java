package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.console.InputHandler;
import dev.yudin.data.XMLReporter;
import dev.yudin.entities.WellDTO;
import dev.yudin.services.WellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component("xmlDialogue")
public class XMLDialogue implements Dialogue {
    private static final String FILE_NAME_MESSAGE = "Please enter file name: ";

    private WellService wellService;
    private XMLReporter xmlReporter;
    private Console console;

    @Autowired
    public XMLDialogue(WellService wellService,
                       XMLReporter xmlReporter,
                       InputHandler inputHandler) {
        this.wellService = wellService;
        this.xmlReporter = xmlReporter;
        this.console = inputHandler;
    }

    @Override
    public void start(Scanner scanner) {

        String userInput = console.readName(
                FILE_NAME_MESSAGE,
                scanner
        );
        List<WellDTO> wellDTOS = wellService.getAllData();

        xmlReporter.create(wellDTOS, userInput);

        System.out.println("XML created!");
    }
}
