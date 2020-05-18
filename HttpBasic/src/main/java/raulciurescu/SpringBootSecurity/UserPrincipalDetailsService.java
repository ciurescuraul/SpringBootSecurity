package raulciurescu.SpringBootSecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import raulciurescu.SpringBootSecurity.db.UserRepository;
import raulciurescu.SpringBootSecurity.model.User;

public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
