package com.online.newspaper.controller;

import com.online.newspaper.model.entities.User;
import com.online.newspaper.model.entities.UserSession;
import com.online.newspaper.model.web.LoginRequest;
import com.online.newspaper.model.web.RegistrationRequest;
import com.online.newspaper.repository.UserRepository;
import com.online.newspaper.repository.UserSessionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public UserSession register(@RequestBody RegistrationRequest request) {
        User user = userRepository.createUser(request);
        UserSession userSession = userSessionRepository.create(
                UUID.randomUUID().toString(),
                user);

        System.out.println(userSession.getSessionId());

        return userSession;
    }

    @PostMapping("/login")
    @SneakyThrows
    public UserSession login(@RequestBody LoginRequest request) {
        User user = userRepository.getByUsernameAndPassword(
                request.getUsername(),
                request.getPassword());

        if (user == null) {
            throw new UsernameNotFoundException("Login is incorrect");
        }

        return userSessionRepository.create(
                UUID.randomUUID().toString(),
                user
        );
    }

    @PutMapping("/logout")
    public void logout(@RequestHeader("Authorization")
                               String sessionId) {
        userSessionRepository.invalidateSession(sessionId);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
