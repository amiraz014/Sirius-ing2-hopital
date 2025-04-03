package episen.sirius.ing2.proto_back.controller;

import java.util.List;
import java.util.Map;

import episen.sirius.ing2.proto_back.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import episen.sirius.ing2.proto_back.model.Employe;
import episen.sirius.ing2.proto_back.service.ViewService;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/epital-api")
@CrossOrigin(origins = "http://172.31.250.115:3000")
public class EmployeController {

    @Autowired
    private ViewService viewService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/employes")
    public ResponseEntity<List<Employe>> getEmployes() {
        List<Employe> employes = viewService.getAllEmployes();
        if (employes != null) {
            return new ResponseEntity<>(employes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(employes, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> LogAuth(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        try {
            String token = loginService.authenticateUser(username, password);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(Map.of(
                            "token", token,
                            "message", "Authentification réussie"
                    ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur interne du serveur"));
        }
    }

}