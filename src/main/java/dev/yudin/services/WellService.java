package dev.yudin.services;

import dev.yudin.entities.Well;
import dev.yudin.entities.WellDTO;
import dev.yudin.entities.WellEquipmentDTO;

import java.util.List;

public interface WellService extends GenericService<Well>{

    Well getByName(String name);

    int getId(String name);

    List<Well> findAll();

    WellEquipmentDTO getWellWithDetails(String name);

    List<WellDTO> getAllData();
}
