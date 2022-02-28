package dev.yudin.dao.impl;

import dev.yudin.dao.WellDAO;
import dev.yudin.entities.Well;
import dev.yudin.entities.WellEquipmentDTO;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.DataNotFoundException;
import dev.yudin.mappers.WellEquipmentRowMapper;
import dev.yudin.mappers.WellRowMapper;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j
@Repository
public class WellDAOImpl implements WellDAO {
    private static final String SQL_SELECT_WELL_BY_NAME = "SELECT * FROM wells WHERE name = ?";
    private static final String SQL_SELECT_ALL_WELLS = "SELECT * FROM wells";
    private static final String SQL_SAVE_WELL = "INSERT INTO wells (name) VALUES (?)";
    private static final String SQL_SELECT_WELL_ID = "SELECT id FROM wells WHERE name = ?";
    private static final String SQL_SELECT_WELL_AND_AMOUNT_EQUIPMENT = "" +
            "SELECT wells.name, COUNT(*) \n" +
            "FROM wells \n" +
            "JOIN equipments \n" +
            "ON wells.id = equipments.well_id \n" +
            "WHERE wells.name = ?";

    private static final String ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD = "Error during call the method getByName()";
    private static final String EMPTY_RESULT_MESSAGE = "There is no such well with such name = ";
    private static final String ERROR_MESSAGE_FOR_SAVE_WELL_METHOD = "Error during call the method saveNewWell()";
    private static final String ERROR_MESSAGE_FOR_GET_WELL_WITH_DETAILS_METHOD = "Error during call the method getWellWithDetails()";
    private static final String ERROR_MESSAGE_FOR_FIND_ALL_METHOD = "Error during call the method findAll()";

    private JdbcTemplate jdbcTemplate;
    private WellRowMapper wellRowMapper;
    private WellEquipmentRowMapper wellEquipmentRowMapper;

    @Autowired
    public WellDAOImpl(JdbcTemplate jdbcTemplate,
                       WellRowMapper wellRowMapper,
                       WellEquipmentRowMapper wellEquipmentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.wellRowMapper = wellRowMapper;
        this.wellEquipmentRowMapper = wellEquipmentRowMapper;
    }

    @Override
    public Well getByName(String name) {
        log.debug("Call method getByName() with name = " + name);
        try {
            Well well = jdbcTemplate.queryForObject(
                    SQL_SELECT_WELL_BY_NAME,
                    wellRowMapper,
                    name
            );
            if (log.isDebugEnabled()) {
                log.debug("Well is " + well);
            }
            return well;
        } catch (EmptyResultDataAccessException ex) {
            log.warn(EMPTY_RESULT_MESSAGE + name, ex);
            throw new DataNotFoundException(EMPTY_RESULT_MESSAGE + name, ex);
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
        }
    }

    @Override
    public int getId(String name) {
        log.debug("Call method getId() with name = " + name);
        try {
            Integer id = jdbcTemplate.queryForObject(
                    SQL_SELECT_WELL_ID,
                    Integer.class,
                    name
            );
            if (log.isDebugEnabled()) {
                log.debug("Well'ID is " + id);
            }
            return id;
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
        }
    }

    @Override
    public List<Well> findAll() {
        log.debug("Call method findAll()");
        try {
            List<Well> wells = jdbcTemplate.query(
                    SQL_SELECT_ALL_WELLS,
                    wellRowMapper
            );
            if (log.isDebugEnabled()) {
                log.debug("Wells are " + wells);
            }
            return wells;
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
        }
    }

    @Override
    public void save(Well well) {
        log.debug("Call method save() for well with name = " + well.getName());
        if (!doesExist(well.getName())) {
            saveNewWell(well);
        }
    }

    private void saveNewWell(Well well) {
        log.debug("Call method saveNewWell() for well with name = " + well.getName());
        String name = well.getName();
        try {
            jdbcTemplate.update(
                    SQL_SAVE_WELL,
                    name
            );
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_SAVE_WELL_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_SAVE_WELL_METHOD, ex);
        }
    }

    @Override
    public WellEquipmentDTO getWellWithDetails(String name) {
        log.debug("Call method getWellWithDetails() for well with name = " + name);
        try {
            WellEquipmentDTO wellEquipmentDTO = jdbcTemplate.queryForObject(
                    SQL_SELECT_WELL_AND_AMOUNT_EQUIPMENT,
                    wellEquipmentRowMapper,
                    name
            );
            if (log.isDebugEnabled()) {
                log.debug("All info about wells " + wellEquipmentDTO);
            }
            return wellEquipmentDTO;
        } catch (DataAccessException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_WELL_WITH_DETAILS_METHOD, ex);
            throw new DAOException(ERROR_MESSAGE_FOR_GET_WELL_WITH_DETAILS_METHOD, ex);
        }
    }
}
