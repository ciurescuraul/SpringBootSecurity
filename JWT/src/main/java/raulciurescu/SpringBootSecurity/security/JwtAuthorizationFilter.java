package raulciurescu.SpringBootSecurity.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import raulciurescu.SpringBootSecurity.db.UserRepository;
import raulciurescu.SpringBootSecurity.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthenticationEntryPoint authenticationEntryPoint,
                                  UserRepository userRepository) {
        super(authenticationManager, authenticationEntryPoint);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the Jwt token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring implementation and exit
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request,response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();

            // Search in DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                User user = userRepository.findByUsername(userName);
                UserPrincipal userPrincipal = new UserPrincipal(user);

                return new UsernamePasswordAuthenticationToken(
                        userName,
                        null,
                        userPrincipal.getAuthorities()
                );

            }
            return null;
        }
        return null;
    }
}
