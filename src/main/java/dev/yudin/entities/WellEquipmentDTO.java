package dev.yudin.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellEquipmentDTO {
    private String name;
    private int amount;
}
