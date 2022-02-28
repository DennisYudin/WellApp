package dev.yudin.dao.impl;

import dev.yudin.config.AppConfigTest;
import dev.yudin.dao.EquipmentDAO;
import dev.yudin.data.Generator;
import dev.yudin.entities.Equipment;
import dev.yudin.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@Sql(scripts = {
        "file:src/test/resources/createTables.sql",
        "file:src/test/resources/populateTables.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "file:src/test/resources/dropTables.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EquipmentDAOImplTest {
    private static final String SQL_SELECT_EQUIPMENT_ID = "SELECT id FROM equipments WHERE name = ?";
    private static final String SQL_SELECT_ALL_EQUIPMENT_ID = "SELECT id FROM equipments";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EquipmentDAO equipmentDAO;

    @Autowired
    private Generator generator;

    @Test
    void getByName_ShouldReturnEquipment_WhenInputIsEquipmentName() {

        Equipment actualWell = equipmentDAO.getByName("EQ0033");
        Equipment expectedWell = new Equipment(1, "EQ0033", 1);

        assertEquals(expectedWell, actualWell);
    }

    @Test
    void getByName_ShouldThrowDataNotFoundException_WhenInputIsDoesNotExistName() {

        assertThrows(DataNotFoundException.class, () -> equipmentDAO.getByName("DoesNotExistName"));
    }

    @Test
    void save_ShouldSaveWell_WhenInputIsWellObject() {

        Equipment newEquipment = new Equipment(0, "EQ0013", 1);

        equipmentDAO.save(newEquipment);

        String checkName = "EQ0013";

        int expectedId = 6;
        Integer actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_EQUIPMENT_ID,
                Integer.class,
                checkName
        );
        System.out.println(equipmentDAO.getByName("EQ0013"));
        assertEquals(expectedId, actualId);
    }

    @Test
    void save_ShouldDoNothing_WhenInputIsAlreadyExistedWell() {

        Equipment existedEquipment = new Equipment(0, "EQ0033", 1);

        equipmentDAO.save(existedEquipment);

        List<Integer> actualIDs = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_EQUIPMENT_ID,
                Integer.class
        );
        int expectedSize = 5;
        int actualSize = actualIDs.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllByName_ShouldReturnAllEquipments_WhenInputIsWellName() {

        List<Equipment> expectedEquipments = new ArrayList<>();
        {
            expectedEquipments.add(new Equipment(1, "EQ0033", 1));
            expectedEquipments.add(new Equipment(3, "EQ0035", 1));
        }
        List<Equipment> actualEquipments = equipmentDAO.findAllByName("Well-1");

        int expectedSize = 2;
        int actualSize = actualEquipments.size();

        assertEquals(expectedSize, actualSize);
        assertTrue(expectedEquipments.containsAll(actualEquipments));
    }

    @Test
    void findAllByName_ShouldReturnEmptyList_WhenInputIsWellNameWithoutEquipments() {

        List<Equipment> actualEquipments = equipmentDAO.findAllByName("Well-3");

        int expectedSize = 0;
        int actualSize = actualEquipments.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllByName_ShouldReturnEmptyList_WhenInputIsDoesNotExistWellName() {

        List<Equipment> actualEquipments = equipmentDAO.findAllByName("DoesNotExistName");

        int expectedSize = 0;
        int actualSize = actualEquipments.size();

        assertEquals(expectedSize, actualSize);
    }
}
