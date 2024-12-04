package com.project.Clinical_System.service;



import com.project.Clinical_System.dto.UserDto;
import com.project.Clinical_System.entity.Role;
import com.project.Clinical_System.entity.User;
import com.project.Clinical_System.repository.RoleRepository;
import com.project.Clinical_System.repository.UserRepository;
import com.project.Clinical_System.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Handles user signup.
     */
    public void signup(UserDto userDto) {
        if (userExists(userDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Convert the role string to Role entity
        Role role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Handles user login and returns a JWT token.
     */
    public String login(User user) {
        // Authenticate the user
        Authentication authentication = authenticateUser(user);

        // Extract roles from authentication object
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Generate JWT token with roles
        return jwtUtil.generateToken(authentication.getName(), roles);
    }
    private Authentication authenticateUser(User user) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
    }

}

