package dev.yudin.dao;

import dev.yudin.entities.Well;
import dev.yudin.entities.WellEquipmentDTO;

import java.util.List;

public interface WellDAO extends GenericDAO<Well> {

    int getId(String name);

    WellEquipmentDTO getWellWithDetails(String name);

    List<Well> findAll();
}
