package episen.sirius.ing2.proto_back.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.repository.EmployeRepo;

@Service
public class LoginService {

    @Autowired
    private EmployeRepo empRepo;

    public String authenticateUser(String username, String password) throws AuthenticationException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Password cannot be empty");
        }

        Employe employe = empRepo.findByNomUtilisateur(username);


        if (!employe.getMotDePasse().equals(password)) {
            throw new AuthenticationException("Mot de passe incorrect");
        }


        return "dummy-token-" + employe.getIdE();
    }
}