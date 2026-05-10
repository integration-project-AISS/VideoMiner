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
    public Page<Comment> findAll(

            @RequestParam(required = false) String text,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String order

    ) {

        Pageable paging = PageRequest.of(
                page,
                size,
                Sort.by(order)
        );

        if (text != null) {
            return repository.findByText(text, paging);
        }

        return repository.findAll(paging);
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