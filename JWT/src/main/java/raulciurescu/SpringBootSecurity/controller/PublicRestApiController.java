package raulciurescu.SpringBootSecurity.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raulciurescu.SpringBootSecurity.db.UserRepository;
import raulciurescu.SpringBootSecurity.model.User;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin
public class PublicRestApiController {
    private UserRepository userRepository;

    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Available to all authenticated users
    @GetMapping("api")
    public String api() { return "API Test With User Logged in "; }

    // Available to managers
    @GetMapping("management/reports")
    public String reports() { return "Some management report data"; }

    // Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users(){
        return this.userRepository.findAll();
    }
}
