package dev.yudin.mappers;

import dev.yudin.entities.Equipment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EquipmentRowMapper implements RowMapper<Equipment> {

    @Override
    public Equipment mapRow(ResultSet rs, int rowNum) throws SQLException {

        Equipment equipment = new Equipment();

        equipment.setId(rs.getInt("id"));
        equipment.setName(rs.getString("name"));
        equipment.setWellId(rs.getInt("well_id"));

        return equipment;
    }
}
