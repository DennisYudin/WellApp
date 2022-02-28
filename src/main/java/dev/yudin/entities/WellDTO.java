package dev.yudin.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellDTO {
    private String wellName;
    private int wellId;
    private List<Equipment> equipments;
}
