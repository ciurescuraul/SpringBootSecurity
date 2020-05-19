package raulciurescu.SpringBootSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raulciurescu.SpringBootSecurity.db.UserRepository;
import raulciurescu.SpringBootSecurity.model.User;

import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {

    private UserRepository userRepository;

    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("api1")
    public String api1() { return "API Test 1"; }

    @GetMapping("api2")
    public String api2() { return "API Test 2"; }

    @GetMapping("users")
    public List<User> users(){
        return this.userRepository.findAll();
    }
}
