package raulciurescu.SpringBootSecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import raulciurescu.SpringBootSecurity.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserRepository userRepository;

    public DbInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args){

        // Create users
        User user = new User("raul","raul123", "USER", "");
        User admin = new User("admin","admin123", "ADMIN", "ACCESS_API1,ACCESS_API2");
        User manager = new User("manager","manager123", "MANAGER", "ACCESS_API1");

        List<User> users = Arrays.asList(user,admin,manager);

        // Save to db
        this.userRepository.saveAll(users);
    }
}
