package episen.sirius.ing2.proto_back.service;

import java.util.List;

import episen.sirius.ing2.proto_back.model.Lieu;
import episen.sirius.ing2.proto_back.model.Profession;
import episen.sirius.ing2.proto_back.repository.LieuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;
import episen.sirius.ing2.proto_back.repository.GardeRepo;


@Service
public class  ViewService {
    @Autowired
    private EmployeRepo repo;
    @Autowired
    private LieuRepo lrepo;




    public List<Employe> getAllEmployes() {

        List<Employe> employes = repo.findByProfessionId(47L);
        for (Employe employe : employes) {
            System.out.println("-------------------------------------------------------------------");
           System.out.println(employe.getNom());
            System.out.println(employe.getProfession().getNom());
            employe.getGardes().forEach(garde -> {
                System.out.println(garde.getDate());
                System.out.println(garde.getHeure());
                System.out.println(garde.getLieu().getSecteur());
            });

            System.out.println(employe.getGardes().stream().map( g -> g.getLieu().getSecteur()).toList());
            System.out.println("-------------------------------------------------------------------");



        }
        return employes;
    }


    public List<String> getSectors(){
        List<String> sectors = lrepo.findSectors();
        System.out.println(sectors);
        return sectors;
    }



}