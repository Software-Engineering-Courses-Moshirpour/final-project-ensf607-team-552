package com.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.enums.Status;
import com.jwt.request.LoginRequest;
import com.jwt.request.SignupRequest;
import com.jwt.response.JwtResponse;
import com.jwt.response.MessageResponse;
import com.model.ERole;
import com.model.Role;
import com.model.User;
import com.repository.RoleDao;
import com.repository.UserDao;
import com.service.UserDetailsImpl;
import com.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userRepository;

    @Autowired
    RoleDao roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
                        Role userRole = roleRepository.findByRoleName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                } else {
                    strRoles.forEach(role -> {
                        switch (role) {
                            case "admin":
                                Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "amc":
                        Role amcRole = roleRepository.findByRoleName(ERole.ROLE_ANIMALCAREAT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(amcRole);

                        break;
                    case "amt":
                        Role amtRole = roleRepository.findByRoleName(ERole.ROLE_ANIMALHTTECH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(amtRole);

                        break;
                    case "tt":
                        Role ttRole = roleRepository.findByRoleName(ERole.ROLE_TEACHINGTECH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(ttRole);

                        break;
                    case "ss":
                        Role ssRole = roleRepository.findByRoleName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(ssRole);

                        break;
                }
            });
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setDob(LocalDate.parse(signUpRequest.getDob(),formatter));
        user.setStatus(Status.ACTIVE);
        user.setPhone("555555");
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

