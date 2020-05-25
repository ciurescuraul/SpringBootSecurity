package raulciurescu.SpringBootSecurity.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raulciurescu.SpringBootSecurity.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Retrieve users from database by username
    User findByUsername(String username);
}
