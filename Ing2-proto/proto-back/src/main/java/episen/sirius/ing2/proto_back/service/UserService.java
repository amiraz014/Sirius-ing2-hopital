package episen.sirius.ing2.proto_back.service;

import episen.sirius.ing2.proto_back.model.User;
import episen.sirius.ing2.proto_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public List<User> getUsers() {
        return repo.findAll();
    }

    public User savedUser(User user) {
        return repo.save(user);
    }
}
