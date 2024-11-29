package episen.sirius.ing2.proto_back.controller;

import episen.sirius.ing2.proto_back.model.User;
import episen.sirius.ing2.proto_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epital-api")
@CrossOrigin(origins = "http://172.31.253.209:3000")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getUsers();
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
           return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUsers(@RequestBody User user){
        try{
        User savedUser = service.savedUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (Exception e){
          return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
