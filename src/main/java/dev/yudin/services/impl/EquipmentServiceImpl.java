package dev.yudin.services.impl;

import dev.yudin.dao.EquipmentDAO;
import dev.yudin.entities.Equipment;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.EquipmentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class EquipmentServiceImpl implements EquipmentService {
    private static final String ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD = "Error during call the method doesExistValue()";
    private static final String ERROR_MESSAGE_FOR_SAVE_METHOD = "Error during call the method save()";
    private static final String ERROR_MESSAGE_FOR_FIND_ALL_METHOD = "Error during call the method findAll()";

    private EquipmentDAO equipmentDAO;

    @Autowired
    public EquipmentServiceImpl(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    @Override
    public boolean doesExistValue(String name) {
        log.debug("Call method doesExistValue() with name = " + name);
        try {
            boolean valueExist = equipmentDAO.doesExist(name);

            return valueExist;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_DOES_EXIST_VALUE_METHOD, ex);
        }
    }

    @Override
    public void save(Equipment equipment) {
        log.debug("Call method save() with name = " + equipment.getName());
        try {
            equipmentDAO.save(equipment);
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_SAVE_METHOD, ex);
        }
    }

    @Override
    public List<Equipment> findAllByName(String name) {
        log.debug("Call method findAll()");
        try {
            List<Equipment> equipments = equipmentDAO.findAllByName(name);
            if (log.isDebugEnabled()) {
                log.debug("Equipments are " + equipments);
            }
            return equipments;
        } catch (DAOException ex) {
            log.error(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
            throw new ServiceException(ERROR_MESSAGE_FOR_FIND_ALL_METHOD, ex);
        }
    }
}
