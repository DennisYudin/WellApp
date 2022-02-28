package dev.yudin.services;

import dev.yudin.entities.Equipment;

import java.util.List;

public interface EquipmentService extends GenericService<Equipment> {

    List<Equipment> findAllByName(String name);
}
