package dev.yudin.dao;

import dev.yudin.entities.Equipment;

import java.util.List;

public interface EquipmentDAO extends GenericDAO<Equipment> {

    List<Equipment> findAllByName(String name);
}
