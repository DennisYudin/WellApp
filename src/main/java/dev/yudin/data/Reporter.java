package dev.yudin.data;

import dev.yudin.entities.WellDTO;

import java.util.List;

public interface Reporter {

    void create(List<WellDTO> input, String fileName);
}
