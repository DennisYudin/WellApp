package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.console.InputHandler;
import dev.yudin.data.Resolver;
import dev.yudin.entities.WellEquipmentDTO;
import dev.yudin.services.WellService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j
@Component("wellDialogue")
public class WellDialogue implements Dialogue {
    private static final String EXIST_WELL_NAME_MESSAGE = "Well name does not exist in database. Please choose another one.";
    private static final String WELL_NAME_MESSAGE = "Well name (enter well name): ";

    private Console console;
    private WellService wellService;
    private Resolver resolver;

    @Autowired
    public WellDialogue(InputHandler inputHandler,
                        WellService wellService,
                        Resolver resolver) {
        this.console = inputHandler;
        this.wellService = wellService;
        this.resolver = resolver;
    }

    @Override
    public void start(Scanner scanner) {

        System.out.println("Enter names:");

        List<String> names = validateName(scanner);

        System.out.println(createReport(names));
    }

    private List<String> validateName(Scanner scanner) {
        log.debug("Call method validateName()");
        String name;
        List<String> wellNames;
        List<Boolean> existData = new ArrayList<>();
        boolean isGoodInput;
        do {
            name = console.readName(
                    WELL_NAME_MESSAGE,
                    scanner
            );
            existData.clear();
            wellNames = resolver.convert(name);
            for (String wellName : wellNames) {
                isGoodInput = false;
                if (wellService.doesExistValue(wellName)) {
                    isGoodInput = true;
                    existData.add(isGoodInput);
                } else {
                    System.out.println(EXIST_WELL_NAME_MESSAGE);
                    existData.add(isGoodInput);
                }
            }
        } while (existData.contains(false));

        log.debug("wellNames: " + name);
        return wellNames;
    }

    private StringBuilder createReport(List<String> input) {
        StringBuilder resultMessage = new StringBuilder();

        for (String name : input) {
            WellEquipmentDTO wellEquipmentDTO = wellService.getWellWithDetails(name);

            String wellName = wellEquipmentDTO.getName();
            int amountEquipment = wellEquipmentDTO.getAmount();

            if (wellName == null) {
                resultMessage
                        .append("There is no equipment with such name = ").append(name)
                        .append("\n");
            } else {
                resultMessage
                        .append("Well name: ").append(wellName)
                        .append(" - ")
                        .append("Amount equipment: ").append(amountEquipment)
                        .append("\n");
            }
        }
        return resultMessage;
    }
}
