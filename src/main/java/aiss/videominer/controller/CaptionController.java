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

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    // GET http://localhost:8080/api/captions
    // Devuelve todos los subtítulos

    @GetMapping
    public List<Caption> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/captions/{id}
    // Devuelve un subtítulo por id

    @GetMapping("/{id}")
    public Caption findOne(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Caption not found"));
    }

    // POST http://localhost:8080/api/captions
    // Crea un subtítulo nuevo

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Caption create(@Valid @RequestBody Caption caption) {

        return repository.save(
                new Caption(
                        caption.getId(),
                        caption.getName(),
                        caption.getLanguage()
                )
        );
    }
}