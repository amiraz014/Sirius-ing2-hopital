package episen.sirius.ing2.proto_back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;


@Service
public class  ViewService {
    @Autowired
    private EmployeRepo repo;
    @Autowired
    private GardeRepo gardeRepo;

    public List<Employe> getAllEmployes() {
        System.out.println("Employes : " + repo.findEmployeDetailsByProfessionId(40L));
        return repo.findEmployeDetailsByProfessionId(40L);
       
    }



}