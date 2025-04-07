package episen.sirius.ing2.proto_back;

import episen.sirius.ing2.proto_back.dto.HistoriqueParMedicamentDTO;
import episen.sirius.ing2.proto_back.repository.HistoriqueRepo;
import episen.sirius.ing2.proto_back.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoriqueServiceTest {

    @Mock
    private HistoriqueRepo historiqueRepo;

    @InjectMocks
    private StockService stockService;

    @Test
    void getHistoriqueGroupedByMedicament_shouldReturnCorrectDTOList() {

        Object[] mockResult1 = new Object[]{
                1L,
                "Paracetamol",
                5,
                50,
                Date.valueOf(LocalDate.of(2023, 5, 10))
        };

        Object[] mockResult2 = new Object[]{
                2L,
                "Ibuprofene",
                3,
                30,
                Date.valueOf(LocalDate.of(2023, 5, 15))
        };

        List<Object[]> mockResults = Arrays.asList(mockResult1, mockResult2);

        when(historiqueRepo.findHistoriqueGroupedByMedicament()).thenReturn(mockResults);


        List<HistoriqueParMedicamentDTO> result = stockService.getHistoriqueGroupedByMedicament();


        assertEquals(2, result.size());

        HistoriqueParMedicamentDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getIdMedicament());
        assertEquals("Paracetamol", dto1.getNomMedicament());
        assertEquals(5, dto1.getNombreSorties());
        assertEquals(50, dto1.getQuantiteTotaleSortie());
        assertEquals(LocalDate.of(2023, 5, 10), dto1.getDerniereSortie());


        HistoriqueParMedicamentDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getIdMedicament());
        assertEquals("Ibuprofene", dto2.getNomMedicament());
        assertEquals(3, dto2.getNombreSorties());
        assertEquals(30, dto2.getQuantiteTotaleSortie());
        assertEquals(LocalDate.of(2023, 5, 15), dto2.getDerniereSortie());
    }

    @Test
    void getHistoriqueGroupedByMedicament_shouldReturnEmptyListWhenNoData() {

        when(historiqueRepo.findHistoriqueGroupedByMedicament()).thenReturn(List.of());


        List<HistoriqueParMedicamentDTO> result = stockService.getHistoriqueGroupedByMedicament();


        assertTrue(result.isEmpty());
    }
}