package dev.yudin.dao.impl;

import dev.yudin.config.AppConfigTest;
import dev.yudin.data.Generator;
import dev.yudin.entities.Well;
import dev.yudin.entities.WellEquipmentDTO;
import dev.yudin.exceptions.DAOException;
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
class WellDAOImplTest {
    private static final String SQL_SELECT_WELL_ID = "SELECT id FROM wells WHERE name = ?";
    private static final String SQL_SELECT_ALL_WELLS_ID = "SELECT id FROM wells";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WellDAOImpl wellDAO;

    @Autowired
    private Generator generator;

    @Test
    void getByName_ShouldReturnWell_WhenInputIsWellName() {

        Well actualWell = wellDAO.getByName("Well-1");
        Well expectedWell = new Well(1, "Well-1");

        assertEquals(expectedWell, actualWell);
    }

    @Test
    void getByName_ShouldThrowDataNotFoundException_WhenInputIsDoesNotExistName() {

        assertThrows(DataNotFoundException.class, () -> wellDAO.getByName("DoesNotExistName"));
    }

    @Test
    void save_ShouldSaveWell_WhenInputIsWellObject() {

        Well newWell = new Well(0, "Well-13");

        wellDAO.save(newWell);

        String checkName = "Well-13";

        int expectedId = 6;
        Integer actualId = jdbcTemplate.queryForObject(
                SQL_SELECT_WELL_ID,
                Integer.class,
                checkName
        );
        assertEquals(expectedId, actualId);
    }

    @Test
    void save_ShouldDoNothing_WhenInputIsAlreadyExistedWell() {

        Well existedWell = new Well(0, "Well-5");

        wellDAO.save(existedWell);

        List<Integer> actualIDs = jdbcTemplate.queryForList(
                SQL_SELECT_ALL_WELLS_ID,
                Integer.class
        );
        int expectedSize = 5;
        int actualSize = actualIDs.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getId_ShouldReturnId_WhenInputIsWellName() {

        int expectedId = 1;
        int actualId = wellDAO.getId("Well-1");

        assertEquals(expectedId, actualId);
    }

    @Test
    void getId_ShouldThrowDAOException_WhenInputIsDoesNotExistName() {

        assertThrows(DAOException.class, () -> wellDAO.getId("DoesNotExistName"));
    }

    @Test
    void getWellWithDetails_ShouldReturnWellNameAndAmountEquipments_WhenInputIsWellName() {

        WellEquipmentDTO expected = new WellEquipmentDTO("Well-1", 2);
        WellEquipmentDTO actual = wellDAO.getWellWithDetails("Well-1");

        assertEquals(expected, actual);
    }

    @Test
    void findAll_ShouldReturnAllWells_WhenCallMethod() {

        List<Well> expectedWells = new ArrayList<>();
        {
            expectedWells.add(new Well(1, "Well-1"));
            expectedWells.add(new Well(2, "Well-2"));
            expectedWells.add(new Well(3, "Well-3"));
            expectedWells.add(new Well(4, "Well-4"));
            expectedWells.add(new Well(5, "Well-5"));
        }
        List<Well> actualWells = wellDAO.findAll();

        int expectedSize = 5;
        int actualSize = actualWells.size();

        assertEquals(expectedSize, actualSize);
        assertTrue(expectedWells.containsAll(actualWells));
    }
}
