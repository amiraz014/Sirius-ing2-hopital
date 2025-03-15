package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.repository.AbsenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepo absenceRepo;
}
