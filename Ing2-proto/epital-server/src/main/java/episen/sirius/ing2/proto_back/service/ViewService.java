package episen.sirius.ing2.proto_back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.model.EmployeDTO;

@Service
public class  ViewService {
    @Autowired
    private EmployeRepo repo;

    public List<EmployeDTO> getAllEmployes() {
        return repo.findEmployes();
    }
   


}