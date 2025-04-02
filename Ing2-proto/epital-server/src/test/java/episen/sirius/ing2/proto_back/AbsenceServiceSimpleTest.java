package episen.sirius.ing2.proto_back;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import episen.sirius.ing2.proto_back.model.Absence;
import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.repository.AbsenceRepo;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.service.AbsenceService;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AbsenceServiceSimpleTest {

    @Autowired
    private AbsenceService absenceService;

    @MockBean
    private EmployeRepo employeRepo;

    @MockBean
    private AbsenceRepo absenceRepo;

    @Test
    void testSaveAbsence() {
    
        String username = "testUser";
        String motif = "Congé";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        Employe mockEmploye = new Employe();
        mockEmploye.setIdE(1L);
        mockEmploye.setNomUtilisateur(username);
        
        when(employeRepo.findByNomUtilisateur(username)).thenReturn(mockEmploye);
        when(absenceRepo.save(any(Absence.class))).thenAnswer(invocation -> invocation.getArgument(0));

    
        absenceService.saveAbsence(motif, date, time, username);

        
        verify(employeRepo, times(1)).findByNomUtilisateur(username);
        verify(absenceRepo, times(1)).save(any(Absence.class));
    }
}