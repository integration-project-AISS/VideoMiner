package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.User;
import aiss.videominer.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET http://localhost:8080/api/users
    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // GET http://localhost:8080/api/users/{userId}
    @GetMapping("/{userId}")
    public User findOne(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));
    }

    // POST http://localhost:8080/api/users
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    // PUT http://localhost:8080/api/users/{userId}
    @PutMapping("/{userId}")
    public User update(@PathVariable Long userId,
                       @Valid @RequestBody User userData) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        user.setName(userData.getName());
        user.setUser_link(userData.getUser_link());
        user.setPicture_link(userData.getPicture_link());

        return userRepository.save(user);
    }

    // DELETE http://localhost:8080/api/users/{userId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(userId);
    }
}