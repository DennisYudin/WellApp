package dev.yudin.mappers;

import dev.yudin.entities.Well;
import dev.yudin.entities.WellEquipmentDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WellEquipmentRowMapper implements RowMapper<WellEquipmentDTO> {

    @Override
    public WellEquipmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        WellEquipmentDTO wellEquipmentDTO = new WellEquipmentDTO();

        wellEquipmentDTO.setName(rs.getString("name"));
        wellEquipmentDTO.setAmount(rs.getInt("Count(*)"));

        return wellEquipmentDTO;
    }
}
