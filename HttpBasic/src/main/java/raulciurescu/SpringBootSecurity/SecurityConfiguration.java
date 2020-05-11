package raulciurescu.SpringBootSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("raul").password(passwordEncoder().encode("raul123")).roles("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder().encode("user123")).roles("USER")
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
/**
 * Granular access
 * */
//                .antMatchers("/index.html").permitAll()
//                .antMatchers("/profile/index").authenticated()
//                .antMatchers("/admin/index").hasRole("ADMIN")
//                .antMatchers("/management/index").hasAnyRole("ADMIN","MANAGER")


/**
 * Access all pages in that folder
 * The order of antMatchers is VERY IMPORTANT because they are executed in this order
 * */
                .antMatchers("/index").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
//                .antMatchers("/api/public/api1").authenticated() // Granular protection
                .antMatchers("/api/public/**").authenticated() // Protected with wildcards
//                .antMatchers("/api/public/**").hasRole("ADMIN") // Protected with wildcards and Roles
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
