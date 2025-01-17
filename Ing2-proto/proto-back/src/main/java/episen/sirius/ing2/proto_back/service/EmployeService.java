package episen.sirius.ing2.proto_back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.model.Profession;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.ProfessionRepo;

@Service
public class EmployeService {
   
    @Autowired 
    private EmployeRepo erepo;
    @Autowired
    private Profession prepo;
    


    
}
