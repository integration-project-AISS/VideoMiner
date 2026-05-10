package aiss.videominer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    UserRepository repository;

    // GET http://localhost:8080/api/users
    // Devuelve todos los usuarios

    @GetMapping
    public Page<User> findAll(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String order

    ) {

        Pageable paging = PageRequest.of(
                page,
                size,
                Sort.by(order)
        );

        return repository.findAll(paging);
    }

    // GET http://localhost:8080/api/users/{id}
    // Devuelve un usuario por id

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"));
    }

    // POST http://localhost:8080/api/users
    // Crea un usuario nuevo

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {

        return repository.save(
                new User(
                        user.getName(),
                        user.getUser_link(),
                        user.getPicture_link()
                )
        );
    }
}