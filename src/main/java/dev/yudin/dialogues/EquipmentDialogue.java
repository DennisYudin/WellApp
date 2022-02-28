package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.data.Generator;
import dev.yudin.entities.Equipment;
import dev.yudin.entities.Well;
import dev.yudin.services.EquipmentService;

import dev.yudin.services.WellService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Log4j
@Component("equipmentDialogue")
public class EquipmentDialogue implements Dialogue {
    private static final String EQUIPMENT_MESSAGE = "Amount equipment (enter value from 1 up to 30): ";
    private static final String WELL_NAME_MESSAGE = "Well name (enter well name): ";
    private static final String EXIST_WELL_NAME_MESSAGE = "Well name already exist in database. Please choose another one.";

    private WellService wellService;
    private EquipmentService equipmentService;
    private Console console;
    private Generator generator;

    @Autowired
    public EquipmentDialogue(WellService wellService,
                             EquipmentService equipmentService,
                             Console inputHandler,
                             Generator generator) {
        this.wellService = wellService;
        this.equipmentService = equipmentService;
        this.console = inputHandler;
        this.generator = generator;
    }

    @Override
    public void start(Scanner scanner) {
        int amountEquipment = console.readNumber(
                EQUIPMENT_MESSAGE,
                scanner
        );
        scanner.nextLine();

        String name = validateName(scanner);

        Well newWell = new Well(0, name);

        wellService.save(newWell);

        int wellId = wellService.getId(newWell.getName());

        saveEquipment(amountEquipment, wellId);

        System.out.println("Done!");
    }

    private String validateName(Scanner scanner) {
        log.debug("Call method validateName()");
        String name;
        boolean isGoodInput = false;
        do {
            name = console.readName(
                    WELL_NAME_MESSAGE,
                    scanner
            );
            if (!wellService.doesExistValue(name)) {
                isGoodInput = true;
            } else {
                System.out.println(EXIST_WELL_NAME_MESSAGE);
            }
        } while (!isGoodInput);
        log.debug("Name: " + name);
        return name;
    }

    private void saveEquipment(int amount, int foreignId) {
        log.debug("Call method saveEquipment()");
        for (int i = 0; i < amount; i++) {
            String equipmentName;
            boolean isGoodInput = false;
            do {
                equipmentName = generator.create();

                if (!equipmentService.doesExistValue(equipmentName)) {
                    isGoodInput = true;
                }
            } while (!isGoodInput);

            Equipment newEquipment = new Equipment(0, equipmentName, foreignId);

            equipmentService.save(newEquipment);
        }
    }
}
