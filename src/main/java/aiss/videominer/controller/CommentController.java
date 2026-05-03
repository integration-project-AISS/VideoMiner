package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;

    // GET http://localhost:8080/api/comments
    // Devuelve todos los comentarios

    @GetMapping
    public List<Comment> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/comments/{id}
    // Devuelve un comentario por id

    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Comment not found"));
    }

    // POST http://localhost:8080/api/comments
    // Crea un comentario nuevo

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment create(@Valid @RequestBody Comment comment) {

        return repository.save(
                new Comment(
                        comment.getId(),
                        comment.getText(),
                        comment.getCreatedOn()
                )
        );
    }
}