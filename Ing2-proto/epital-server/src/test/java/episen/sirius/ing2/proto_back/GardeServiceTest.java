package episen.sirius.ing2.proto_back;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Garde;
import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;
import episen.sirius.ing2.proto_back.repository.LieuRepo;
import episen.sirius.ing2.proto_back.service.GardeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GardeServiceTest {

    @Mock
    private EmployeRepo employeRepo;
    @Mock
    private GardeRepo gardeRepo;
    @Mock
    private LieuRepo lieuRepo;

    @InjectMocks
    private GardeService gardeService;

    @Test
    void whenNoEmployees_thenThrowException() {
        when(employeRepo.findByProfessionId(40L)).thenReturn(Collections.emptyList());

        LocalDate debut = LocalDate.now();
        LocalDate fin = debut.plusDays(1);

        assertThrows(RuntimeException.class, () -> gardeService.planifierGardes(debut, fin));
    }



    @Test
    void getHeurePourType_returnsCorrectTimes() {
        assertEquals(LocalTime.of(8, 0), gardeService.getHeurePourType("MATIN"));
        assertEquals(LocalTime.of(20, 0), gardeService.getHeurePourType("SOIR"));

    }
}