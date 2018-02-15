package com.online.newspaper.repository;

import com.online.newspaper.model.entities.User;
import com.online.newspaper.model.web.RegistrationRequest;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private static Map<String, User> users = new HashMap<>();

    public User createUser(RegistrationRequest request) {
        User mapUser = users.get(request.getUsername());
        if (mapUser != null) {
            throw new RuntimeException(String.format("User with username %s already exists",
                    request.getUsername()));
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .roles(Arrays.asList("ROLE_ADMIN", "ROLE_WRITER", "ROLE_READER"))
                .username(request.getUsername())
                .id(users.size() + 1)
                .build();

        users.putIfAbsent(user.getUsername(), user);

        return user;
    }

    public User getByUsernameAndPassword(@NonNull String username,
                                         @NonNull String password) {
        User user = users.get(username);
        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public User getByUsername(@NonNull String username) {
        return users.get(username);
    }

    public User getByUserId(Integer userId) {
        return users
                .values()
                .stream()
                .filter(x -> x.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {

        return new ArrayList<>(users.values());
    }
}
