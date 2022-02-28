package dev.yudin.mappers;

import dev.yudin.entities.Well;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WellRowMapper implements RowMapper<Well> {

    @Override
    public Well mapRow(ResultSet rs, int rowNum) throws SQLException {

        Well well = new Well();

        well.setId(rs.getInt("id"));
        well.setName(rs.getString("name"));

        return well;
    }
}
