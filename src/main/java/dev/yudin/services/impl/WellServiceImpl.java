package dev.yudin.services.impl;

import dev.yudin.dao.EquipmentDAO;
import dev.yudin.dao.WellDAO;
import dev.yudin.entities.Equipment;
import dev.yudin.entities.Well;
import dev.yudin.entities.WellDTO;
import dev.yudin.entities.WellEquipmentDTO;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.DataNotFoundException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.WellService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
public class WellServiceImpl implements WellService {
    private static final String ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD = "Error during call the method getByName()";
    private static final String ERROR_MESSAGE_FOR_GET_ID_METHOD = "Error during call the method getId()";
    private static final String EMPTY_RESULT_MESSAGE = "There is no such well with such name = ";
    private static final String ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD = "Error during call the method doesExistValue()";
    private static final String ERROR_MESSAGE_FOR_SAVE_METHOD = "Error during call the method save()";
    private static final String ERROR_MESSAGE_FOR_GET_WELLS_WITH_DETAILS_METHOD = "Error during call the method getWellWithDetails()";
    private static final String ERROR_MESSAGE_FOR_FIND_ALL_METHOD = "Error during call the method findAll()";

    private WellDAO wellDAO;
    private EquipmentDAO equipmentDAO;

    @Autowired
    public WellServiceImpl(WellDAO wellDAO, EquipmentDAO equipmentDAO) {
        this.wellDAO = wellDAO;
        this.equipmentDAO = equipmentDAO;
    }

    @Override
    public Well getByName(String name) {
        log.debug("Call method getByName() with name = " + name);
        try {
            Well well = wellDAO.getByName(name);
            if (log.isDebugEnabled()) {
                log.debug("Well is " + well);
            }
            return well;
        } catch (DataNotFoundException ex) {
            log.warn(EMPTY_RESULT_MESSAGE + name, ex);
            throw new ServiceException(EMPTY_RESULT_MESSAGE + name, ex);
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_GET_BY_NAME_METHOD, ex);
        }
    }

    @Override
    public boolean doesExistValue(String name) {
        log.debug("Call method doesExistValue() with name = " + name);

        try {
            boolean valueExist = wellDAO.doesExist(name);

            return valueExist;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD, ex);
        }
    }

    @Override
    public int getId(String name) {
        log.debug("Call method getId() with name = " + name);
        try {
            int id = wellDAO.getId(name);
            if (log.isDebugEnabled()) {
                log.debug("Well's ID " + id);
            }
            return id;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_ID_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_GET_ID_METHOD, ex);
        }
    }

    public void save(Well well) {
        log.debug("Call method save() with name = " + well.getName());
        try {
            wellDAO.save(well);
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
        }
    }

    @Override
    public List<Well> findAll() {
        log.debug("Call method findAll()");
        try {
            List<Well> wells = wellDAO.findAll();
            if (log.isDebugEnabled()) {
                log.debug("Wells are " + wells);
            }
            return wells;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
        }
    }

    @Override
    public WellEquipmentDTO getWellWithDetails(String name) {
        log.debug("Call method getWellWithDetails() with name = " + name);
        try {
            WellEquipmentDTO wellEquipmentDTO = wellDAO.getWellWithDetails(name);
            if (log.isDebugEnabled()) {
                log.debug("WellEquipmentDTO is " + wellEquipmentDTO);
            }
            return wellEquipmentDTO;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_GET_WELLS_WITH_DETAILS_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_GET_WELLS_WITH_DETAILS_METHOD, ex);
        }
    }

    @Override
    public List<WellDTO> getAllData() {
        log.debug("Call method getAllData()");

        List<WellDTO> result = new ArrayList<>();

        List<Well> wells = wellDAO.findAll();

        for (Well well : wells) {
            List<Equipment> equipments = equipmentDAO.findAllByName(well.getName());

            WellDTO wellDTO = new WellDTO();
            wellDTO.setWellId(well.getId());
            wellDTO.setWellName(well.getName());
            wellDTO.setEquipments(equipments);

            result.add(wellDTO);
        }
        if (log.isDebugEnabled()) {
            log.debug("WellDTO are " + result);
        }
        return result;
    }
}
