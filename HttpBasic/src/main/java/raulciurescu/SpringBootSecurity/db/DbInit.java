package raulciurescu.SpringBootSecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raulciurescu.SpringBootSecurity.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        // Delete all
        this.userRepository.deleteAll();

        // Create users
        User user = new User("raul",passwordEncoder.encode("raul123"), "USER", "");
        User admin = new User("admin",passwordEncoder.encode("admin123"), "ADMIN", "ACCESS_API1,ACCESS_API2");
        User manager = new User("manager",passwordEncoder.encode("manager123"), "MANAGER", "ACCESS_API1");

        List<User> users = Arrays.asList(user,admin,manager);

        // Save to db
        this.userRepository.saveAll(users);
    }
}
