package dev.yudin.dao.impl;

import dev.yudin.dao.EquipmentDAO;
import dev.yudin.entities.Equipment;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.DataNotFoundException;
import dev.yudin.mappers.EquipmentRowMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j
@Repository
public class EquipmentDAOImpl implements EquipmentDAO {
    private static final String SQL_SELECT_EQUIPMENT_BY_NAME = "SELECT * FROM equipments WHERE name = ?";
    private static final String SQL_SAVE_EQUIPMENT = "INSERT INTO equipments (name, well_id) VALUES (?, ?)";
    private static final String SQL_FIND_ALL_BY_NAME = "" +
            "SELECT equipments.id, equipments.name, equipments.well_id \n" +
            "FROM equipments\n" +
            "JOIN wells\n" +
            "ON equipments.well_id = wells.id\n" +
            "WHERE wells.name = ?";

    private static final String ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD = "Error during call the method getByName()";
    private static final String EMPTY_RESULT_MESSAGE = "There is no such equipment with such name = ";
    private static final String ERROR_MESSAGE_FOR_SAVE_EQUIPMENT_METHOD = "Error during call the method saveNewEquipment()";
    private static final String ERROR_MESSAGE_FOR_FIND_ALL_METHOD_METHOD = "Error during call the method findAllByName()";

    private final JdbcTemplate jdbcTemplate;
    private EquipmentRowMapper equipmentRowMapper;

    @Autowired
    public EquipmentDAOImpl(JdbcTemplate jdbcTemplate, EquipmentRowMapper equipmentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.equipmentRowMapper = equipmentRowMapper;
    }

    @Override
    public Equipment getByName(String name) {
        log.debug("Call method getByName() with name = " + name);
        try {
            Equipment equipment = jdbcTemplate.queryForObject(
                    SQL_SELECT_EQUIPMENT_BY_NAME,
                    equipmentRowMapper,
                    name
            );
            if (log.isDebugEnabled()) {
                log.debug("Equipment is " + equipment);
            }
            return equipment;
        } catch (EmptyResultDataAccessException ex) {
            log.warn(EMPTY_RESULT_MESSAGE + name, ex);
            throw new DataNotFoundException(EMPTY_RESULT_MESSAGE + name, ex);
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
        }
    }

    @Override
    public List<Equipment> findAllByName(String name) {
        log.debug("Call method findAllByName()");
        try {
            List<Equipment> equipments = jdbcTemplate.query(
                    SQL_FIND_ALL_BY_NAME,
                    equipmentRowMapper,
                    name
            );
            if (log.isDebugEnabled()) {
                log.debug("Equipments are " + equipments);
            }
            return equipments;
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_FIND_ALL_METHOD_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_FIND_ALL_METHOD_METHOD, ex);
        }
    }

    @Override
    public void save(Equipment equipment) {
        log.debug("Call method save() for well with name = " + equipment.getName());
        if (!doesExist(equipment.getName())) {
            saveNewEquipment(equipment);
        }
    }

    private void saveNewEquipment(Equipment equipment) {
        log.debug("Call method saveNewEquipment() for equipment with name = " + equipment.getName());
        log.debug("Call method saveNewEquipment() for equipment with wellId = " + equipment.getWellId());

        String name = equipment.getName();
        int foreignKey = equipment.getWellId();
        try {
            jdbcTemplate.update(
                    SQL_SAVE_EQUIPMENT,
                    name, foreignKey
            );
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_SAVE_EQUIPMENT_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_SAVE_EQUIPMENT_METHOD, ex);
        }
    }
}

