package dev.yudin.services;

import dev.yudin.dao.WellDAO;
import dev.yudin.entities.Well;
import dev.yudin.services.impl.WellServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WellServiceImplTest {

    @InjectMocks
    private WellServiceImpl wellService;

    @Mock
    private WellDAO wellDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getByName_ShouldReturnCategory_WhenInputIsThreeFirstLettersOfCategory() {

        Well expectedWell = new Well(1, "Well-1");

        Mockito.when(wellDAO.getByName("Well-1")).thenReturn(expectedWell);

        Well actualWell = wellService.getByName("Well-1");

        assertEquals(expectedWell, actualWell);
    }
}
